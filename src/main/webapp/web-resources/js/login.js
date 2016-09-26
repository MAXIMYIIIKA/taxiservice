/**
 * Created by maxim on 07.09.2016.
 */
function checkForm(form) {
    var flag = true;
    if (document.getElementById("username").value == ""){
        flag = false;
        $.get("register/emptyUsername", function (data, status) {
            $(".alert-error").html(data);
            visible();
        });
    }
    return flag;
};