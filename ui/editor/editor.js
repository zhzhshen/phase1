$(document).ready(function(){
    edit();
});

var view = function () {
    $('.title').each(function(title) {
        $(this).replaceWith("<li><h1>"+ $(this).attr("data") +"</h1></li>");
    });

    $('.textarea').each(function(title) {
        $(this).replaceWith("<li><p>"+ $(this).attr("data") +"</p></li>");
    });
}

var edit = function () {
    $('.title').each(function(title) {
        $(this).replaceWith("<li><h1 contenteditable=\"true\">"+ $(this).attr("data") +"</h1></li>");
//        $(this).replaceWith("<li><input type=\"text\" value=\""+ $(this).attr("data") +"\"></input></li>");
    });

    $('.textarea').each(function(title) {
        $(this).replaceWith("<li><p contenteditable=\"true\">"+ $(this).attr("data") +"</p></li>");
    });
}