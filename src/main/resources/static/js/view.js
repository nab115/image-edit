var cropBounds = new Map();
const imageBounds = new Map();

$(document).ready(function() {

    getImage();

    $(".process-type-btn").click(function (){
        console.log($(this).attr("name"));
        var data = {"transform": $(this).attr("name")}
        if (data["transform"] === "crop") {
            data["cropParams"] = getCropString();
        }
        console.log(data);

        $.ajax({
            url: "/"
            , type: "POST"
            , data: data
            , success : (response) => {
                displayImage(response);
            }
        })
    })

    /*
    Nesting dragLine and the mousup listener inside mousdown listener
    seems like a messy way of accomplishing this. Should come back and improve this logic.

    The reason for this logic is we need to send dragLine the element being dragged. Usually, with an
    event listener, you can reference *this*, however in this case, *this* is window, since we want to
    add the mousemove listener to window not the line being dragged. So, we need some alternative way
    of giving dragLine reference to the specific line being moved. The only solution I have to that would be to
    use an anonymous function as the listener that passes the event AND the line element to dragLine, but then we
    would not be able to use removeEventListener on the anonymous function. So by nesting everything inside
    the mousedown listener, the reference to the line element being dragged is in scope (*$(this)*)

    */
    $(".crop-line").mousedown(function() {

        var line = $(this);

        function dragLine(e) {
            if ((line.attr("id") === ("top")) || (line.attr("id") === ("bottom"))) {
                if (inBounds(line.attr("id"), e.clientY)) {
                    offset = imageBounds.get("top");
                    line.css("top", (e.clientY - offset) + 'px');
                }
            }
            else {
                if (inBounds(line.attr("id"), e.clientX)) {
                    offset = imageBounds.get("left");
                    line.css("left", (e.clientX - offset) + 'px');
                }
            }
        }

        line.css("cursor", "grabbing");
        window.addEventListener('mousemove', dragLine, false);

        // the log for this function indicates the mousdown functions for all clicked lines
        // are not exiting
        line.mouseup(function(e) {
                line.css("cursor", "grab");

                if ((line.attr("id") === ("top")) || (line.attr("id") === ("bottom"))) {
                    cropBounds.set(line.attr("id"), e.clientY)
                }
                else {
                    cropBounds.set(line.attr("id"), e.clientX)
                }

                // logging here prints out 2^n - need to figure out why this function is
                // calling itself recursively
//                console.log(imageBounds.entries());
//                console.log(cropBounds.entries());
                window.removeEventListener('mousemove', dragLine, false);
        })
    })
})

function inBounds(line_id, value) {
    switch(line_id) {
        case "top":
            b1 = imageBounds.get("top");
            b2 = cropBounds.get("bottom");
            break;
        case "bottom":
            b1 = cropBounds.get("top");
            b2 = imageBounds.get("bottom");
            break;
        case "left":
            b1 = imageBounds.get("left");
            b2 = cropBounds.get("right");
            break;
        case "right":
            b1 = cropBounds.get("left");
            b2 = imageBounds.get("right");
            break;
    }

    if (value >= b1 && value <= b2) return true;

    return false;
}

function getCropString() {

    return (cropBounds.get("left") - imageBounds.get("left"))
    + ","
    + (cropBounds.get("top") - imageBounds.get("top"))
    + ","
    + (cropBounds.get("right") - imageBounds.get("left"))
    + ","
    + (cropBounds.get("bottom") - imageBounds.get("top"))
    + ","
    + (imageBounds.get("right") - imageBounds.get("left"))
    + ","
    + (imageBounds.get("bottom") - imageBounds.get("top"))
}

function setBounds() {

    const rect = document.getElementById('container').getBoundingClientRect();
    cropBounds.set("top", rect.top);
    cropBounds.set("bottom", rect.bottom);
    cropBounds.set("left", rect.left);
    cropBounds.set("right", rect.right);

    imageBounds.set("top", rect.top);
    imageBounds.set("bottom", rect.bottom);
    imageBounds.set("left", rect.left);
    imageBounds.set("right", rect.right);
}

function getImage() {
    console.log("retrieving image . . .");
    $.ajax({
            url: "/image"
            , type: "GET"
            , success : (response) => {
                displayImage(response);
            }
        })
}

function displayImage(encoded) {

    if (encoded) {
        imageHTML = `
            <span class="crop-line" id="left"></span>
            <span class="crop-line" id="top"></span>
            <span class="crop-line" id="bottom"></span>
            <span class="crop-line" id="right"></span>
            <img id="image" src="data:image/jpeg;base64, ${encoded}" alt=""/>
        `;

        console.log(imageHTML);

        document.getElementById("container").innerHTML = imageHTML;
        setBounds();
    }
}