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
    undraggableRow();

    $('.component').each(function(){
        let widget = $(this).find('.form-control');
        let height = widget.outerHeight();
        $(this).attr("data-height", height);

        let children = $(this).children();
        children.each(function(component){
            $(this).remove();
        });

        if ($(this).hasClass("title")) {
            $("<h4>"+ $(this).attr("data-content") +"</h4>").appendTo($(this));
        } else if ($(this).hasClass("textarea")) {
            $("<p style=\"height: "+ height + "px\">"+ $(this).attr("data-content") +"</p>").appendTo($(this));
        }
    });
}

var edit = function () {
    clearContent();
    draggableRow();
    $('.component').each(function() {
        let component = $(this);
        $("<span class=\"glyphicon glyphicon-move\"></span>").appendTo(component);
        if ($(this).hasClass('title')) {
            $("<input class=\"form-control\" type=\"text\" value=\"" + component.attr("data-content") + "\">")
                .on('change', function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(component);
        } else if ($(this).hasClass('textarea')) {
            $("<textarea class=\"form-control\" style=\"height: "+ component.attr("data-height") + "px\">" + component.attr("data-content") + "</textarea>")
                .on('change', function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(component);
        }
        $("<span class=\"glyphicon glyphicon-remove-circle\"></span>")
            .on('click', function() {
                $(this).closest('.row').remove();
            })
            .appendTo(component);
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