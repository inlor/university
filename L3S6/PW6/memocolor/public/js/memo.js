const navs = ["#content", "#color", "#share"]

/* Show and hide elements */
function show_hide(show, hide) {
    hide.forEach(element => {
        $(element + "-div").hide();
    });
    $(show + "-div").show();
}

/* Add active class of remove */
function add_remove_active(add, remove) {
    remove.forEach(element => {
        $(element).removeClass("active");
    });
    $(add).addClass("active");
}

/* Default case */
show_hide("#content", navs);

/* General roules for Content Color & Share */
navs.forEach(element => {
    $(element).click(() => {
        show_hide(element, navs);
        add_remove_active(element, navs);
    });
});

/* Selection of a color */
const colors = ['red', 'orange', 'yellow', 'green', 'blue', 'violet', 'black', 'grey', 'white'];
colors.forEach(color => {
    $("#" + color).click(function () {
        $(this).addClass("circle-selected")
        document.getElementById("color-input").value = color;
        $("#content-div").removeClass(function (index, className) {
            return (className.match(/(^|\s)color-\S+/g) || []).join(' ');
        });
        colors.forEach((remove_select) => {
            if (color != remove_select) {
                $("#" + remove_select).removeClass("circle-selected")
                $("#content-div").addClass("color-" + color + "-bg")
            }
        });
    });
});

/* Share */
$(document).ready(function () {
    $.getJSON("http://" + window.location.hostname + ":" + window.location.port + "/api/users", function (data) {
        $("#share-input").keyup(function () {
            $("#result").html('');
            let search_field = $(this).val();
            let expression = new RegExp(search_field, "i");
            let i = 0;
            let per_page = 0;
            $.each(data, function (key, value) {
                if (value.username.search(expression) != -1 && per_page < 5) {

                    let id_user = unique();
                    per_page++;
                    $("#result").append('<li class="list-group-item" id="user' + id_user + '">' + value.username + '</li>');

                    $("#user" + id_user).click(function () {
                        let id_tag = unique();
                        $("#tags").append('<span class="badge badge-primary cursor" id="tag' + id_tag + '">' + value.username+":"+$("#rights").val()+ '</span>');
                        $("#tag" + id_tag).click(function () {
                            $(this).remove();
                        });
                        $(this).remove();
                    });
                }
                i++;
            });
        });
    });
});

function unique() {
    return Math.random().toString(16).slice(8);
}

/* When submit */
$("#submit").click(function () {
    const array = [];
    $('span[id^="tag"]').each(function () {
        array.push($(this).text())
    });
    $('#share-array').val(JSON.stringify(array));
});