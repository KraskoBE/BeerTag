$(document).ready(function () {
    if (typeof $.cookie('access_token') != 'undefined') {
        $('#addBeerButton').show();
    }

    loadTopBeers();
    loadPageCount();
    loadCountries();
    loadStyles();
    loadTypes();
});

function loadTopBeers() {
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/api/beers?page=0&orderBy=rating",
        "method": "GET",
        "headers": {
            "Cache-Control": "no-cache",
            "cache-control": "no-cache",
        },
        success: function (data) {
            $('#topBeersContainer').html('');
            $.each(data.content, function (i) {

                $('#topBeersContainer').append(`
                <div class="card">
            <h5 class="card-header">
                <img src="https://www.countryflags.io/${data.content[i].originCountry.code}/shiny/32.png" desc>
                ${data.content[i].name}
            </h5>
            <img class="card-img-top" src="http://localhost:8080/api/images/${data.content[i].image.id}" alt="Card image cap">
            <div class="top-left"><span class="badge badge-secondary">${data.content[i].abv + "%"}</span></div>
            <div class="card-body">
                <p class="card-subtitle text-truncate">${data.content[i].brewery}</p>
                <p class="card-subtitle text-muted text-truncate">${data.content[i].type}, ${data.content[i].style}</p>
                <p class="card-subtitle text-muted text-truncate">Added by <a href="http://localhost/user.php?u=${data.content[i].creator.id}">${data.content[i].creator.name}</a></p>
                <div class="star-ratings-sprite" style="display: inline-block;align-content:center"><span
                        style="width:${(data.content[i].averageRating / 5) * 100}%;" class="star-ratings-sprite-rating"></span></div>
                <p class="card-subtitle text-muted text-truncate">${data.content[i].totalVotes} votes
                    <a href="http://localhost/beer.php?b=${data.content[i].id}" style="float:right;">
                        See more
                    </a>
                </p>
            </div>
        </div>
                `
                );
            })
        },
        error: function (request, error) {
            console.log(request);
            console.log(error);
        },
    });
}

function loadPageCount() {
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/api/beers?page=0&orderBy=rating",
        "method": "GET",
        "headers": {
            "Cache-Control": "no-cache",
            "cache-control": "no-cache",
        },
        success: function (data) {
            $('#pagination').twbsPagination({
                totalPages: data.totalPages,
                visiblePages: 5,
                onPageClick: function (event, page) {
                    loadBeerPage(page - 1);
                }
            })
        },
        error: function (request, error) {
            console.log(request);
            console.log(error);
        },
    });

}

function loadBeerPage(index) {
    var orderBy = $('#orderSelect').val();

    if (index < 0)
        return;

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/beers?page=${index}&orderBy=${orderBy}`,
        "method": "GET",
        "headers": {
            "Cache-Control": "no-cache",
            "cache-control": "no-cache",
        },
        success: function (data) {
            $('#allBeersContainer').html('');
            $.each(data.content, function (i) {

                $('#allBeersContainer').append(`
                <div class="card">
            <h5 class="card-header">
                <img src="https://www.countryflags.io/${data.content[i].originCountry.code}/shiny/32.png" desc>
                ${data.content[i].name}
            </h5>
            <img class="card-img-top" src="http://localhost:8080/api/images/${data.content[i].image.id}" alt="Card image cap">
            <div class="top-left"><span class="badge badge-secondary">${data.content[i].abv + "%"}</span></div>
            <div class="card-body">
                <p class="card-subtitle text-truncate">${data.content[i].brewery}</p>
                <p class="card-subtitle text-muted text-truncate">${data.content[i].type}, ${data.content[i].style}</p>
                <p class="card-subtitle text-muted text-truncate">Added by <a href="http://localhost/user.php?u=${data.content[i].creator.id}">${data.content[i].creator.name}</a></p>
                <div class="star-ratings-sprite" style="display: inline-block;align-content:center"><span
                        style="width:${(data.content[i].averageRating / 5) * 100}%;" class="star-ratings-sprite-rating"></span></div>
                <p class="card-subtitle text-muted text-truncate">${data.content[i].totalVotes} votes
                    <a href="http://localhost/beer.php?b=${data.content[i].id}" style="float:right;">
                        See more
                    </a>
                </p>
            </div>
        </div>
                `
                );
            })
        },
        error: function (request, error) {
            console.log(request);
            console.log(error);
        },
    });
}

function loadCountries() {
    $.getJSON('./json/countries.json', function (data) {
        $.each(data, function (index) {
            $('#countrySelect').append(`<option value="${data[index].country_id}">${data[index].name}</option>`);
        })
    });
}

function loadStyles() {
    $.getJSON('./json/styles.json', function (data) {
        $.each(data, function (index) {
            $('#styleSelect').append(`<option value="${data[index].value}">${data[index].value}</option>`);
        })
    });
}

function loadTypes() {
    $.getJSON('./json/types.json', function (data) {
        $.each(data, function (index) {
            $('#typeSelect').append(`<option value="${data[index].value}">${data[index].value}</option>`);
        })
    });
}

$(function () {
    $('#newBeerForm').submit(function (e) {
        e.preventDefault();
        createBeer();
    });
});

$('#inputBeerImage').on("change", function () {
    var input = $('#inputBeerImage');
    var FR = new FileReader();

    FR.addEventListener("load", function (e) {
        $('#base64Converted').html(e.target.result);
        $('#base64Converted').val($('#base64Converted').val().replace(/^data:image\/[a-z]+;base64,/, ""));
    });

    FR.readAsDataURL((input[0]).files[0]);
});



function createBeer() {
    var serialized = $("#newBeerForm").serializeArray();

    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var final = JSON.stringify(data);
    var position = final.length - 1;

    var countryId = $('#countrySelect').val();
    var toInsert = `,"originCountry": {"id": ${countryId}}`;
    final = [final.slice(0, position), toInsert, final.slice(position)].join('');

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/api/beers",
        "method": "POST",
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Accept": "*/*",
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        "xhrFields": {
            "withCredentials": true
        },
        "data": final,
        success: function (response) {
            $('#addBeerInfoText').text("Beer added!");
            $('#addBeerInfoText').attr('class', 'text-success ml-1 font-weight-bold');
            $('#addBeerInfoText').attr('style', 'display:inline');
            window.setTimeout(function () {
                window.location.href = `/beer.php?b=${response.id}`;
            }, 2000);
        },
        error: function (response, error) {
            console.log(response.status);
            $('#addBeerInfoText').attr('class', 'text-danger ml-1 font-weight-bold');
            $('#addBeerInfoText').attr('style', 'display:inline');
            if (response.status == 400)
                $('#addBeerInfoText').text("Beer  in use!");
            else {
                $('#addBeerInfoText').text("Your session is invalid!");
                logout();
                setTimeout(location.reload.bind(location), 2000);
            }
        },
    });
}

$("input[type='number']").inputSpinner()

$("#orderSelect").change(function () {
    var currentPage = $('#pagination').twbsPagination('getCurrentPage');
    loadBeerPage(currentPage - 1);
});