/**
 *
 */
$(document).ready(function () {

    $('.addUser,.table .editBtn').on('click', function (e) {
        e.preventDefault();
        if ($(this).attr('data-id')) {
            var url = '/findUserById?id=' + $(this).attr('data-id');
            $.get(url, function (data) {
                $('#id').val(data.id);
                $('#qrCode').val(data.qrCode);
                $('#containerId').val(data.containerId);
                $('#containerPort').val(data.containerPort);
                $('#containerStatus').val(data.containerStatus);
                $('#networkRx').val(data.networkRx);
                $('#networkTx').val(data.networkTx);
                $('#wechatName').val(data.wechatName);
                $('#nickname').val(data.nickname);
                $('#icon').val(data.icon);
                $('#startTime').val(data.startTime);
                $('#endTime').val(data.endTime);
                if (data.isEnable) {
                    $('#isEnableOui').prop('checked', true);
                } else {
                    $('#isEnableNon').prop('checked', true);
                }
                if(data.enableSelfControl){
                    $('#enableSelfControlOui').prop('checked', true);
                }else{
                    $('#enableSelfControlNon').prop('checked', true);
                }
            });
        } else {
            $('#id').val('');
            $('#wechatName').val('');
            $('#startTime').val('');
            $('#endTime').val('');
            $('#nickname').val('');
            $('#isEnableOui').prop('checked', false);
            $('#isEnableNon').prop('checked', false);
            $('#enableSelfControlOui').prop('checked', false);
            $('#enableSelfControlNon').prop('checked', true);
            $('#networkRx').val(0.0);
            $('#networkTx').val(0.0);
        }
        $('#myModal').modal();
    });
    loading();
    generateCode();
    login();
});

function loading() {
    $('a.btn').on('click', function () {
        $(this).button('loading');
    });
}

function generateCode() {
    $("#btnGetCode").on('click', function () {
        $.post("/generateLoginAndPassword", function (data, status) {
            if (status == 'success') {
                $("#tokenQr").attr("src", "/api/qrCode?str=otpauth://totp/qfdk@nasi-ssr?secret=" + data + "&issuer=qfdk");
                $("#tokenValue").text(data);
                $("#modalRegister").modal('show');
            }
        });
    });
}

function login() {
    $("#btnLogin").click(function () {
        $('#msgLoginFailed').hide();
        $.post("/authenticate", {
            login: $("#username").val(),
            password: $("#password").val()
        }, function (data, status) {
            if (data == 'AUTHENTICATED') {
                window.location.replace("/admin");
            } else if (data == "REQUIRE_TOKEN_CHECK") {
                $("#modalLoginCheckToken").modal('show');
            } else {
                $('#msgLoginFailed').show();
            }
        }).fail(function () {
            $('#msgLoginFailed').show();
        });
    });

    // $("#btnTokenVerify").click(function () {
    //     $('#msgTokenCheckFailed').hide();
    //     $.post("/authenticate/token", {
    //         login: $("#username").val(),
    //         password: $("#password").val(),
    //         token: $("#loginToken").val()
    //
    //     }, function (data, status) {
    //         if (data == 'AUTHENTICATED') {
    //             window.location.replace("/admin");
    //         } else {
    //             $('#msgTokenCheckFailed').show();
    //         }
    //     }).fail(function () {
    //         $('#msgTokenCheckFailed').show();
    //     });
    // });

    $("#btnTokenLogin").click(function () {
        $.post("/authenticate/token", {
            token: $("#token").val()
        }, function (data, status) {
            if (data == 'AUTHENTICATED') {
                window.location.replace("/admin");
            }
        });
    });
}