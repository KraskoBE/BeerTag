$(document).ready(function () {
    loadUsers();
});

function loadUsers() {
    $.ajax({
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/api/users",
        "method": "GET",
        "headers": {
            "cache-control": "no-cache"
        },
        success: function (data) {
            $('#usersTableBody').html('');

            $.each(data, function(index){
                $('#usersTableBody').append(`
                <tr>
                <th scope="row">${index+1}</th>
                <td><img src="http://localhost:8080/api/images/${data[index].image==null?1:data[index].image.id}" alt="Profile picture" id="profileImage"
                        class="circleImage">
                </td>
                <td><a href="http://localhost/user.php?u=${data[index].id}" style="color:white">${data[index].name}</a></td>
                <td>${data[index].userRole}</td>
                <td>${data[index].age}</td>
                </tr>
                `)
            })
        },
        error: function (data, status, request) {
            console.log(data);
            console.log(status);
            console.log(request);
        },
    });
}