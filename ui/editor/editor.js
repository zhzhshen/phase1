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

        $(new Component(component).toEditHtml()).appendTo(component);
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

    this.toEditHtml = function() {
        htmlElement = $("<div></div>");
        $("<span class=\"glyphicon glyphicon-move\"></span>").appendTo(htmlElement);

        if (type.includes('title')) {
            $("<input class=\"form-control\" type=\"text\" value=\"" + data + "\">")
                .change(function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(htmlElement);
        } else if (type.includes('textarea')) {
            $("<textarea class=\"form-control\" style=\"height: "+ height + "px\">" + data + "</textarea>")
                .mouseup(function() {
                    component.attr("data-height", $(this).outerHeight());
                })
                .change(function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(htmlElement);
        }

        $("<span class=\"glyphicon glyphicon-remove-circle\"></span>")
            .click(function() {
                component.closest('.row').remove();
            })
            .appendTo(htmlElement);
        return htmlElement;
    }
}

$.fn.removeContent = function() {
    $(this).children().each(function(component){
        $(this).remove();
    });
}