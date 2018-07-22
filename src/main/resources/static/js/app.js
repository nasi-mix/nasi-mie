/**
 *
 */
$(document).ready(function () {

    $('.addUser,.table .editBtn').on('click',function (e) {
        e.preventDefault();
        if($(this).attr('data-id')){
            var url = '/findUserById?id='+$(this).attr('data-id');
            $.get(url,function (data) {
                $('#id').val(data.id);
                $('#qrCode').val(data.qrCode);
                $('#containerId').val(data.containerId);
                $('#containerPort').val(data.containerPort);
                $('#containerStatus').val(data.containerStatus);
                $('#wechatName').val(data.wechatName);
                $('#nickname').val(data.nickname);
                $('#containerLocation').val(data.containerLocation);
                $('#icon').val(data.icon);
                $('#startTime').val(data.startTime);
                $('#endTime').val(data.endTime);
                if(data.isEnable){
                    $('#isEnableOui').prop('checked', true);
                }else{
                    $('#isEnableNon').prop('checked', true);
                }
            });
        }else{
            $('#id').val('');
            $('#wechatName').val('');
            $('#containerLocation').val('');
            $('#startTime').val('');
            $('#endTime').val('');
            $('#nickname').val('');
            $('#isEnableOui').prop('checked', false);
            $('#isEnableNon').prop('checked', false);

        }
        $('#myModal').modal();
    });
    loading();
});

function loading() {
    $('a.btn').on('click', function () {
        var $btn = $(this).button('loading');
    });
}