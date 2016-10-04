$(document).ready(function () {
    $("#imgInput").click(function () {
        $("#inputBtn").click();
    });
    $("#inputBtn").change(function(){
        readURL(this);
    });
});

function readURL(input) {
    if(/(\.bmp|\.gif|\.jpg|\.jpeg|\.png)$/i.test(input.value)) {
        if (input.files[0].size < 1000000) {
            $("#change-avatar").submit();
        } else {
            $.get("user/fileTooLarge", function (data, status) {
                $(".alert-error").html(data);
                visible();
            });
        }
    } else {
        $.get("user/invalidFileFormat", function (data, status) {
            $(".alert-error").html(data);
            visible();
        });
    }
}