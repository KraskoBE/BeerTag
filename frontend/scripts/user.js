var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
};

$(document).ready(function () {
    if (typeof $.cookie('access_token') === 'undefined') {
        window.location.replace("http://localhost:80");
    }

    var userId = getUrlParameter('u');
    if (!isInt(userId))
        window.location.replace("http://localhost:80");

    var tokenInfo = parseJwt($.cookie('access_token'));

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}/wishedBeers`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#wishBody').html('');

            $.each(data, function (index) {
                $('#wishBody').append(`
                    <tr>
                        <th scope="row">${index + 1}</th>
                        <td><a href="http://localhost/beer.php?b=${data[index].id}" style="color:white">${data[index].name}</a></td>
                        <td>${data[index].type}</td>
                        <td>${data[index].style}</td>
                        <td>${Math.round(data[index].averageRating * 100) / 100}</td>
                    </tr>
                `);
            })


        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}/createdBeers`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#addedBody').html('');

            $.each(data, function (index) {
                $('#addedBody').append(`
                    <tr>
                        <th scope="row">${index + 1}</th>
                        <td><a href="http://localhost/beer.php?b=${data[index].id}" style="color:white">${data[index].name}</a></td>
                        <td>${Math.round(data[index].averageRating * 100) / 100}</td>
                    </tr>
                `);
            })


        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}/topDrankBeers`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {

            $('#topBeersContainer').html('');

            $.each(data, function (index) {
                $('#topBeersContainer').append(`
                <div class="card">
                <h5 class="card-header">
                    <img src="https://www.countryflags.io/${data[index].originCountry.code}/shiny/32.png" desc>
                    ${data[index].name}
                </h5>
                <img class="card-img-top" src="http://localhost:8080/api/images/${data[index].image.id}" alt="Card image cap">
                <div class="top-left"><span class="badge badge-secondary">${data[index].abv}%</span></div>
                <div class="card-body">
                    <p class="card-subtitle text-truncate">${data[index].brewery}</p>
                    <p class="card-subtitle text-muted text-truncate">${data[index].type}, ${data[index].style}
                    </p>
                    <p class="card-subtitle text-muted text-truncate">Added by <a href="#">${data[index].creator.name}</a></p>
                    <div class="star-ratings-sprite" style="display: inline-block;align-content:center"><span
                            style="width:${(data[index].averageRating / 5) * 100}%" class="star-ratings-sprite-rating"></span></div>
                    <p class="card-subtitle text-muted text-truncate">${data[index].totalVotes} votes
                        <a href="http://localhost/beer.php?b=${data[index].id}" style="float:right;">
                            See more
                        </a>
                    </p>
                </div>
            </div>
                `);
            })


        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#userInfoContainer').html(`
            <h1 class="display-4" style="margin-left:0">${data.name}</h1>
            <div id="contr" class="cntr" style="
                background-image: url('http://localhost:8080/api/images/${data.image == null ? 1 : data.image.id}');">
            </div>
            <p id="labelAge" class="lead text-center">
                Age: ${data.age}
            </p>
            <input id="inputAge" type="number" data-suffix="" value="${data.age}" data-decimals="0" min="0" max="100" step="1"
    name="age" hidden required />
        
    <input type="file" class="form-control-file mb-2" id="inputProfileImage" style="display:none" accept=".jpg,.jpeg,.png">

    <textarea hidden id="base64Converted" name="imageBase64"></textarea>

            <button type="button" id="editButton" onclick="editUser()" class="btn btn-light" style="display:none">Edit Profile</button>
            
            <button type="button" id="cancelButton" onclick="cancelEdit()" class="btn btn-secondary" style="display:none">Cancel</button>
            
            <button type="button" id="saveButton" onclick="saveUser()" class="btn btn-success" style="display:none">Save</button>

            <button type="button" id="deleteButton" onclick="deleteUser()" class="btn btn-danger" style="display:none">Delete User</button>
            `);

            if (tokenInfo.user_id == data.id)
                $('#editButton').show();
            if (tokenInfo.roles == 'Admin') {
                $('#editButton').show();
                $('#deleteButton').show();
            }

        },
        error: function (data, status, request) {
            window.location.replace("http://localhost:80");
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
})

function isInt(value) {
    return !isNaN(value) && (function (x) { return (x | 0) === x; })(parseFloat(value))
}

function editUser() {
    $('#editButton').hide();
    $('#cancelButton').show();
    $('#saveButton').show();

    $('#labelAge').hide();
    $("input[type='number']").inputSpinner();

    $('#inputProfileImage').show();
}

function cancelEdit() {
    $('#editButton').show();
    $('#cancelButton').hide();
    $('#saveButton').hide();

    $('#labelAge').show();
    $('.input-group').hide();

    $('#inputProfileImage').hide();
}

function saveUser() {
    var userId = getUrlParameter('u');

    var jsonData = `{
        "age":${$('#inputAge').val()},
        "image":"${$('#base64Converted').val()}"
    }`;

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}`,
        "method": "PUT",
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        "processData": false,
        "data": jsonData,
        success: function (data) {
            $('#labelAge').text("Age: " + data.age);
            $('#inputAge').val(data.age);
            $('#contr').attr('style', `background-image: url('http://localhost:8080/api/images/${data.image == null ? 1 : data.image.id}');`)
            cancelEdit();
        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

$(document).on('change', '#inputProfileImage', function () {
    var input = $('#inputProfileImage');
    var FR = new FileReader();

    FR.addEventListener("load", function (e) {
        $('#base64Converted').html(e.target.result);
        $('#base64Converted').val($('#base64Converted').val().replace(/^data:image\/[a-z]+;base64,/, ""));
    });

    FR.readAsDataURL((input[0]).files[0]);
});

function deleteUser() {
    var userId = getUrlParameter('u');

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/${userId}`,
        "method": "DELETE",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "cache-control": "no-cache"
        },
        success: function (data) {
            window.location.replace("http://localhost:80");
        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}
