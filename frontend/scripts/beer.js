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

function loadABV(initial) {
    $('#blockABV').append(`<input id="inputABV" type="number" data-suffix="%" value="${initial}" data-decimals="2" min="0" max="100" step="0.1"
    name="abv" hidden required />`);
}

$(document).ready(function () {
    if (typeof $.cookie('access_token') === 'undefined') {
        window.location.replace("http://localhost:80");
    }

    var beerId = getUrlParameter('b');
    if (!isInt(beerId))
        window.location.replace("http://localhost:80");


    var tokenInfo = parseJwt($.cookie('access_token'));

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/beers/${beerId}`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache",
        },
        success: function (data) {
            $('#beer-container').html(`
            <div class="row">
        <div class="col-md-4 justify-content-center">
        <div class="cntr" style="
        background-image: url('http://localhost:8080/api/images/${data.image.id}');">
                </div>
        </div>
        <div class="col-md-4 text-center">
            <h1 class="font-weight-bold">${data.name}</h1>
            <select id="countrySelect" class="form-control" style="display:none">
            </select>
            
            <p id="labelCountry" class="font-italic">${data.originCountry.name}</p>

            <blockquote class="blockquote text-center" >
                <p class="mb-0">Brewery</p>
                <input id="textBrewery" class="form-control form-control-md" type="text" placeholder="Brewery" value="${data.brewery}" style="display:none">
                <footer id="labelBrewery" class="blockquote-footer text-light ">${data.brewery}</footer>
            </blockquote>

            <blockquote class="blockquote text-center">
                <p class="mb-0">Style</p>
                <select id="styleSelect" class="form-control" name="style" required style="display:none">
                </select>
                <footer id="labelStyle" class="blockquote-footer text-light ">${data.style}</footer>
            </blockquote>

            <blockquote class="blockquote text-center">
                <p class="mb-0">Type</p>
                <select id="typeSelect" class="form-control" name="type" required style="display:none">
                </select>
                <footer id="labelType" class="blockquote-footer text-light ">${data.type}</footer>
            </blockquote>

            <blockquote class="blockquote text-center" id="blockABV">
                <p class="mb-0">ABV</p>
                <footer id="labelABV" class="blockquote-footer text-light ">${data.abv}%</footer>
            </blockquote>

            <blockquote class="blockquote text-center">
                <p class="mb-0">Rating</p>
                <footer id="labelRating" class="blockquote-footer text-light ">${Math.round(data.averageRating * 100) / 100}</footer>
            </blockquote>

        </div>
        <div class="col-md-4">
            <h4 class="font-weight-normal mt-2">Rate</h4>
            <div class="rate" style="display: block">
                <input type="radio" onclick="rateBeer(5)" name="rate" id="star5" value="5" />
                <label for="star5">5 stars</label>
                <input type="radio" onclick="rateBeer(4)" name="rate" id="star4" value="4" />
                <label for="star4">4 stars</label>
                <input type="radio" onclick="rateBeer(3)" name="rate" id="star3" value="3" />
                <label for="star3">3 stars</label>
                <input type="radio" onclick="rateBeer(2)" name="rate" id="star2" value="2" />
                <label for="star2">2 stars</label>
                <input type="radio" onclick="rateBeer(1)" name="rate" id="star1"value="1" />
                <label for="star1">1 star</label>
            </div>
            <br>
            <br>
            <br>
            <button type="button" id="wishButton" onclick="wishBeer(${beerId})" class="btn btn-light mb-2" disabled>Add to Wishlist</button>
            <br>
            <button type="button" id="drinkButton" onclick="drinkBeer(${beerId})" class="btn btn-light mb-2" disabled>Drink</button>
            <br>
            <button type="button" id="editButton" onclick="editBeer()" class="btn btn-light mb-2">Edit Beer</button>
            <button type="button" id="cancelButton" onclick="cancel()" class="btn btn-secondary mb-2" style="display:none">Cancel</button>
            <button type="button" id="saveButton" onclick="updateBeer()" class="btn btn-success mb-2" style="display:none">Save</button>
            <br>
            <button type="button" id="deleteButton" onclick="deleteBeer()" class="btn btn-danger mb-5" disabled>Delete Beer</button>
            <br>
            <h3 class="font-weight-bold">Tags</h3>
            <div class="row mx-3" id="tagsGroup">
            </div>
            <input type="text" class="form-control mt-5 " id="input1" placeholder="Enter a tag">
            <button type="button" id="addTagButton" onclick="addTag()" class="btn btn-info mt-2" >Add tag</button>
        </div>
    </div>

    <div class="col-md-8">
        <h3>Description</h3>
        <textarea id="inputDescription" class="form-control" rows="3" style="display:none"></textarea>
        <p id="labelDescription" class="text-justify" style="background:rgba(255,255,255,0.7);border-radius: 5px;padding:1%; color:black">${data.description}</p>
    </div>
            `);
            $('#tagsGroup').html('');
            console.log(data);
            $.each(data.beerTags, function (index) {
                $('#tagsGroup').append(`<h4><span class="badge badge-info mr-1">#${data.beerTags[index].name}</span></h4>`);

            })

            loadCountries();
            loadStyles();
            loadTypes();
            loadABV(data.abv);

            if (tokenInfo.user_id === data.creator.id || tokenInfo.roles == 'Admin')
                $('#deleteButton').attr('disabled', false);
        },
        error: function (data, status, request) {
            $('#beer-container').html(`<h1 class="display-1">BEER NOT FOUND!</h1>`);

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/beerDetails?beer_id=${beerId}`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            if (data.drunk) {
                $('#drinkButton').attr('disabled', true);
                $('#wishButton').attr('disabled', true);
            } else
                if (data.wished) {
                    $('#drinkButton').attr('disabled', false);
                    $('#wishButton').attr('disabled', true);
                }
                else {
                    $('#drinkButton').attr('disabled', false);
                    $('#wishButton').attr('disabled', false);
                }

            if (data.rating > 0)
                $(`#star${data.rating}`).attr('checked', true);

        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });


});

$("#input1").ready(function () {
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/tags`,
        "method": "GET",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            var tagArray = new Array();

            $.each(data, function (index) {
                tagArray.push(data[index].name);
            })
            $(function () {
                $("#input1").autocomplete({
                    source: [tagArray]
                });
            });

            console.log(tagArray);
        },
        error: function (data, status, request) {

            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
})

function isInt(value) {
    return !isNaN(value) && (function (x) { return (x | 0) === x; })(parseFloat(value))
}

function rateBeer(index) {
    var beerId = getUrlParameter('b');
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/rateBeer?beer_id=${beerId}&rating=${index}`,
        "method": "PUT",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#labelRating').text(Math.round(data.averageRating * 100) / 100);
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

function wishBeer(index) {
    var beerId = getUrlParameter('b');
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/wish?beer_id=${beerId}&rating=${index}`,
        "method": "PUT",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#wishButton').attr('disabled', true);
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

function drinkBeer(index) {
    var beerId = getUrlParameter('b');
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/drink?beer_id=${beerId}&rating=${index}`,
        "method": "PUT",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#wishButton').attr('disabled', true);
            $('#drinkButton').attr('disabled', true);
            cheers();
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

function editBeer() {
    $('#editButton').hide();
    $('#cancelButton').show();
    $('#saveButton').show();

    $('#textBrewery').show();
    $('#labelBrewery').hide();

    var countryText = $('#labelCountry').text();
    $('#countrySelect').show();
    $('#labelCountry').hide();
    $("#countrySelect option").filter(function () {
        return this.text == countryText;
    }).attr('selected', true);


    var countryText = $('#labelStyle').text();
    $('#styleSelect').show();
    $('#labelStyle').hide();
    $("#styleSelect option").filter(function () {
        return this.text == countryText;
    }).attr('selected', true);

    var countryText = $('#labelType').text();
    $('#typeSelect').show();
    $('#labelType').hide();
    $("#typeSelect option").filter(function () {
        return this.text == countryText;
    }).attr('selected', true);

    $("input[type='number']").inputSpinner();
    $('#labelABV').hide();

    $('#labelDescription').hide();
    $('#inputDescription').val($('#labelDescription').text());
    $('#inputDescription').show();
}

function cancel() {
    $('#editButton').show();
    $('#cancelButton').hide();
    $('#saveButton').hide();

    $('#textBrewery').hide();
    $('#labelBrewery').show();

    $('#countrySelect').hide();
    $('#labelCountry').show();

    $('#styleSelect').hide();
    $('#labelStyle').show();

    $('#typeSelect').hide();
    $('#labelType').show();

    $('.input-group').hide();
    $("#input").attr('style', "display:none");
    $('#labelABV').show();

    $('#labelDescription').show();
    $('#inputDescription').hide();
}

function updateBeer() {

    var beerId = getUrlParameter('b');

    var jsonStr = `{
        "brewery": "${$('#textBrewery').val()}",
        "originCountry": {
            "id" : ${$('#countrySelect').val()}
        },
        "description": "${$('#inputDescription').val()}",
        "type": "${$('#typeSelect').val()}",
        "style": "${$('#styleSelect').val()}",
        "abv": ${$('#inputABV').val()}
    }`;

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/beers/${beerId}`,
        "method": "PUT",
        "headers": {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + $.cookie("access_token"),
            "Cache-Control": "no-cache",
            "cache-control": "no-cache"
        },
        "data": jsonStr,
        success: function (data) {

            $('#labelBrewery').text($('#textBrewery').val());
            $('#labelCountry').text($('#countrySelect option:selected').text());
            $('#labelStyle').text($('#styleSelect option:selected').text())
            $('#labelType').text($('#typeSelect option:selected').text())
            $('#labelABV').text($('#inputABV').val());
            $('#labelDescription').text($('#inputDescription').val());
            cancel();
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

function deleteBeer() {
    var beerId = getUrlParameter('b');
    var r = confirm("Are you sure?");
    if (r == true) {
        $.ajax({
            "async": true,
            "crossDomain": true,
            "url": `http://localhost:8080/api/beers/${beerId}`,
            "method": "DELETE",
            "headers": {
                "Authorization": "Bearer " + $.cookie("access_token"),
                "cache-control": "no-cache"
            },
            success: function (data) {
                window.location.replace("http://localhost/beers.php");
            },
            error: function (data, status, request) {
                console.log(data);
                console.log(status);
                console.log(request);
            },
        });
    }
}

function addTag() {
    var beerId = getUrlParameter('b');

    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": `http://localhost:8080/api/users/tagBeer?beer_id=${beerId}&tag=${$('#input1').val()}`,
        "method": "PUT",
        "headers": {
            "Authorization": "Bearer " + $.cookie("access_token"),
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#tagsGroup').html('');
            console.log(data);
            $.each(data.beerTags, function (index) {
                $('#tagsGroup').append(`<h4><span class="badge badge-info mr-1">#${data.beerTags[index].name}</span></h4>`);

            })
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}

function cheers()
{
    $('#cheersAlert').slideDown();
}