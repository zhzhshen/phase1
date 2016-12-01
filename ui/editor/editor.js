$(document).ready(function(){
    view();
});

var view = function () {
    clearContent();
    $('.title').each(function() {
        $(this).append("<h1>"+ $(this).attr("data-content") +"</h1>");
    });

    $('.textarea').each(function() {
        $(this).append("<p>"+ $(this).attr("data-content") +"</p>");
    });
}

var edit = function () {
    clearContent();
    $('.title').each(function() {
        $(this).append("<h1 contenteditable=\"true\">"+ $(this).attr("data-content") +"</h1>");
    });

    $('.textarea').each(function() {
        $(this).append("<p contenteditable=\"true\">"+ $(this).attr("data-content") +"</p>");
    });
}

var clearContent = function () {
    $('.components').each(function(components){
        children = $(this).children();
        children.each(function(component){
            $(this).empty();
        });
    });
}