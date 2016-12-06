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
        let component = $(this);
        component.removeContent();

        $(new Component(component).toViewHtml()).appendTo(component);
    });
}

var edit = function () {
    draggableRow();
    $('.component').each(function() {
        let component = $(this);
        component.removeContent();

        $("<span class=\"glyphicon glyphicon-move\"></span>").appendTo(component);

        if (component.hasClass('title')) {
            $("<input class=\"form-control\" type=\"text\" value=\"" + component.attr("data-content") + "\">")
                .change(function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(component);
        } else if (component.hasClass('textarea')) {
            $("<textarea class=\"form-control\" style=\"height: "+ component.attr("data-height") + "px\">" + component.attr("data-content") + "</textarea>")
                .mouseup(function() {
                    component.attr("data-height", $(this).outerHeight());
                })
                .change(function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(component);
        }

        $("<span class=\"glyphicon glyphicon-remove-circle\"></span>")
            .click(function() {
                component.closest('.row').remove();
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

function Component(component){
    var type = component.attr("class");
    var data = component.attr("data-content");
    var height = component.attr("data-height");
    var htmlElement;

    this.toViewHtml = function() {
        if (type.includes('title')) {
            htmlElement = "<h4>"+ data +"</h4>";
        } else if (type.includes('textarea')) {
            htmlElement = "<p style=\"height: "+ height + "px\">"+ data +"</p>";
        }
        return htmlElement;
    }
}

$.fn.removeContent = function() {
    $(this).children().each(function(component){
        $(this).remove();
    });
}