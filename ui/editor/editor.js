$(document).ready(function(){
    wrapRow();
    view();
    $('#toggle-one').change(function() {
        editable = $(this).prop('checked');
        if (editable) {
            edit();
        } else {
            view();
        }
    })
});

var view = function () {
    clearContent();
    $('.title').each(function() {
        $(this).append("<h4>"+ $(this).attr("data-content") +"</h4>");
    });

    $('.textarea').each(function() {
        $(this).append("<p>"+ $(this).attr("data-content") +"</p>");
    });
}

var edit = function () {
    clearContent();
    $('.title').each(function() {
        let component = $(this);
        component.append("<input class=\"form-control\" type=\"text\" value=\"" + component.attr("data-content") + "\">");
        component.children(":first").change(function(){
            component.attr("data-content", $(this).val());
        });
    });

    $('.textarea').each(function() {
        let component = $(this);
        component.append("<textarea class=\"form-control\">" + component.attr("data-content") + "</textarea>");
        component.children(":first").change(function(){
            component.attr("data-content", $(this).val());
        });
    });
}

var wrapRow = function() {
    $('li.row').each(function() {
        console.log('wala');
        $(this).replaceWith('<div class="panel panel-default"><div class="panel-body">' + this.innerHTML + '</div></div>');
    });
}

var clearContent = function () {
    $('.components').each(function(components){
        let children = $(this).children();
        children.each(function(component){
            $(this).empty();
        });
    });
}