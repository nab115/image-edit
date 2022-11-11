$(document).ready(function() {

})

$(".process-type-btn").click(function() {
    console.log("js working")
    $.ajax({
        url: "/"
        , type: "POST"
        , data: $(this).attr("name")
        , contentType: "text/plain"
        , success : (result) => {
            console.log("ajax");
            $("#image").attr("src", result);
        }
    })
})