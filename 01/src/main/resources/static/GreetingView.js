$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/greeting?name=philipp"
    }).then(function(data) {
        $('.greeting-id').append(data.id);
        $('.greeting-name').append(data.name);
    });
});