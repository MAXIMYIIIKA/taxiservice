/**
 * Created by maxim on 06.09.2016.
 */
$(document).ready(function() {
    $('#confPassword').keyup(function() {
        var pass = $("#password").val();
        var pass_rep = $("#confPassword").val();

        if (pass != pass_rep) {
            $("#confPassword").css('border', 'red 1px solid');
            $.get("register/passwordsDontMatchMessage", function (data, status) {
               $(".alert-error").html(data);
                visible();
            });
        }

        if (pass === pass_rep) {
            $("#confPassword").css('border', '1px solid');
            hide();
            // $('.alert-error').html('');
        }
    });
});

function checkForm(form) {
    var flag = true;
    if (document.getElementById("username").value == ""){
        flag = false;
        $.get("register/emptyUsername", function (data, status) {
            $(".alert-error").html(data);
            visible();
        });
    } else if (document.getElementById("password").value == "" || document.getElementById("password").value != document.getElementById("confPassword").value) {
        flag = false;
        $.get("register/passwordsDontMatchMessage", function (data, status) {
            $(".alert-error").html(data);
            visible();
        });
    }
    return flag;
};

