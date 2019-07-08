$(document).ready(function () {
  if (typeof $.cookie('access_token') === 'undefined') {
    $('#nav-loginButton').attr("style", "display:block");
    $('#nav-signUpButton').attr("style", "display:block");
    $('#nav-item-myProfile').remove();
  } else {
    $('#nav-loginButton').remove();
    $('#nav-signUpButton').remove();
    $('#nav-item-users').attr('style', "margin-right: 15px");
    $('#nav-item-myProfile').attr("style", "display:block");

    var tokenInfo = parseJwt($.cookie('access_token'));
    $('#navbarDropdown').append(tokenInfo.name);
    $('#profileImage').attr("src", `http://localhost:8080/api/images/${tokenInfo.imageId}`);
    $('#myProfileLabel').attr('href', `http://localhost:80/user.php?u=${tokenInfo.user_id}`);
  }
  selectActivePage();
});


function parseJwt(token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
};

function selectActivePage() {
  var url = window.location.pathname;
  var index = url.lastIndexOf("/") + 1;
  var filenameWithExtension = url.substr(index);
  var filename = filenameWithExtension.split(".")[0];
  if (filename === "")
    filename = "index";

  $('#nav-item-' + filename).attr('class', 'nav-item active');
}

$('#loginModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget)
  var recipient = button.data('whatever')
  $('.nav-tabs a[href="#' + recipient + '"]').tab('show');
})

$('#inputPasswordReg, #inputPassword2Reg').on('keyup', function () {
  var pass = $('#inputPasswordReg');
  var pass_confirm = $('#inputPassword2Reg');
  var noMatch = $('#labelNoMatch');

  if (pass.val() == pass_confirm.val()) {
    noMatch.prop('style', 'display:none');
    $('#submitRegister').prop('disabled', false);
  } else {
    noMatch.prop('style', 'display:block');
    $('#submitRegister').prop('disabled', true);
  }
});

$(function () {
  $('#registrationForm').submit(function (e) {
    e.preventDefault();
    createProfile();
  });
});

function createProfile() {
  var serialized = $("#registrationForm").serializeArray();

  var s = '';
  var data = {};
  for (s in serialized) {
    data[serialized[s]['name']] = serialized[s]['value']
  }
  var final = JSON.stringify(data);

  console.log(final);

  $.ajax({
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:8080/api/auth/register",
    "method": "POST",
    "headers": {
      "Content-Type": "application/json",
      "Accept": "*/*",
    },
    "xhrFields": {
      "withCredentials": true
    },
    "processData": false,
    "data": final,
    success: function () {
      $('#registerInfoText').text("Registration successful!");
      $('#registerInfoText').attr('class', 'text-success ml-1 font-weight-bold');
      $('#registerInfoText').attr('style', 'display:inline');
      setTimeout(location.reload.bind(location), 2000);
    },
    error: function (request, error) {
      console.log(error);
      $('#registerInfoText').text("Email already in use!");
      $('#registerInfoText').attr('class', 'text-danger ml-1 font-weight-bold');
      $('#registerInfoText').attr('style', 'display:inline');
    },
  });
}

$(function () {
  $('#loginForm').submit(function (e) {
    e.preventDefault();
    signIn();
  });
});


function signIn() {
  var serialized = $("#loginForm").serializeArray();

  var s = '';
  var data = {};
  for (s in serialized) {
    data[serialized[s]['name']] = serialized[s]['value']
  }
  var final = JSON.stringify(data);

  $.ajax({
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:8080/api/auth/login",
    "method": "POST",
    "headers": {
      'Access-Control-Allow-Origin': 'http://localhost',
      "Content-Type": "application/json",
      "Accept": "*/*",
      "Cache-Control": "no-cache",
    },
    "xhrFields": {
      "withCredentials": true
    },
    "processData": false,
    "data": final,
    success: function () {
      $('#loginInfoText').text("Login successful!");
      $('#loginInfoText').attr('class', 'text-success ml-1 font-weight-bold');
      $('#loginInfoText').attr('style', 'display:inline');
      setTimeout(location.reload.bind(location), 2000);
    },
    error: function () {
      $('#loginInfoText').text("Incorrect user/pass!");
      $('#loginInfoText').attr('class', 'text-danger ml-1 font-weight-bold');
      $('#loginInfoText').attr('style', 'display:inline');
    }
  });
}

function logout() {
  $.removeCookie('access_token', { path: '/' });
  location.reload();
}