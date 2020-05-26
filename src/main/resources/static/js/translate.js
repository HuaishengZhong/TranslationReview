var isWaitingForData = false;


function updateContent(data, textid) {
    document.getElementById(textid).value = data;
    document.getElementById(textid).scrollTop = document.getElementById(textid).scrollHeight;

}

function clearTextArea(classid) {
    document.getElementById(classid).value = ''
}


var contentTimeOutFunction = 0;

$('#stext').ready(function () {

    $('#stext').bind('input propertychange', function () {
        clearTimeout(contentTimeOutFunction);
        var stext = document.getElementById('stext').value.replace(/^\s+|\s+$/g, "");


        if (stext == '') {
            clearTextArea('ttext');
        }
        else {
            var information = {};
            information['engine'] = document.getElementById("engine").value;
            information['sl'] = document.getElementById("sl").value;
            information['tl'] = document.getElementById("tl").value;
            information['stext'] = stext;
            contentTimeOutFunction = setTimeout(function () {
                $.ajax({
                    type: 'GET',
                    url: '/trans',
                    contentType: "text/plain;charset=utf-8",
                    data: information,
                    success: function (data) {
                        updateContent(data, 'ttext');

                    },
                    error: function (e) {
                        clearTextArea('stext');
                        $('#stext').focus()
                    }
                });
            }, 700);

        }

    });
})
;