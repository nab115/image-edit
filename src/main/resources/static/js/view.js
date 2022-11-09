$(document).ready(function() {

})

$(".process-type-btn").click(function() {
    document.getElementById("process-type-title").textContent = $(this).text();
})

$("#upload-button").click(function(e) {
    $.post("/", "testing");
})