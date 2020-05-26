$(document).ready(function () {
    $('#download').click(function () {
        $.ajax({
            type: 'GET',
            url: '/download',
        });
    });
});