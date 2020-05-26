$(document).ready(function () {
    $('#submit').click(function () {
        var information = {
            'translationEngine' : document.getElementById("engine").value,
            'source' : document.getElementById("stext").value,
            'target' : document.getElementById("ttext").value,
            'mark' : document.getElementById("mark").value
        };

        var JsonData = JSON.stringify(information);

        $.ajax({
            type: 'POST',
            url: '/submit',
            contentType: "application/json",
            data: JsonData,
            success: function (result) {
                console.log(result);
            },
            error: function (err) {
                console.log(err);
            }

        });

    });
});