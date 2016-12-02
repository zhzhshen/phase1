$(document).ready(function(){
    wrapColumn();
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
    undraggableRow();
    $('.title').each(function() {
        $(this).append("<h4>"+ $(this).attr("data-content") +"</h4>");
    });

    $('.textarea').each(function() {
        $(this).append("<p>"+ $(this).attr("data-content") +"</p>");
    });
}

var edit = function () {
    clearContent();
    draggableRow();
    $('.title').each(function() {
        let component = $(this);
        component.append("<span class=\"glyphicon glyphicon-move\"></span><input class=\"form-control\" type=\"text\" value=\"" + component.attr("data-content") + "\">");
        component.children(":first").change(function(){
            component.attr("data-content", $(this).val());
        });
    });

    $('.textarea').each(function() {
        let component = $(this);
        component.append("<span class=\"glyphicon glyphicon-move\"></span><textarea class=\"form-control\">" + component.attr("data-content") + "</textarea>");
        component.children(":first").change(function(){
            component.attr("data-content", $(this).val());
        });
    });
}

var draggableRow = function() {
    $('ul.row').each(function() {
        $(this).draggable({disabled: false});
    });
}

var undraggableRow = function() {
    $('ul.row').each(function() {
        $(this).draggable({disabled: true, axis: "y"});
    });
}

var wrapColumn = function() {
    $('li.column').each(function() {
        $(this).replaceWith('<div class="panel panel-default col-md-6"><div class="panel-body">' + this.innerHTML + '</div></div>');
    });
}

var clearContent = function () {
    $('ul.row').each(function(components){
        let children = $(this).children();
        children.each(function(component){
            $(this).empty();
        });
    });
}