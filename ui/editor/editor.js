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
        $(this).children().each(function(component){
            $(this).remove();
        });

        if ($(this).hasClass("title")) {
            $("<h4>"+ $(this).attr("data-content") +"</h4>").appendTo($(this));
        } else if ($(this).hasClass("textarea")) {
            $("<p style=\"height: "+ $(this).attr("data-height") + "px\">"+ $(this).attr("data-content") +"</p>").appendTo($(this));
        }
    });
}

var edit = function () {
    draggableRow();
    $('.component').each(function() {
        $(this).children().each(function(component){
            $(this).remove();
        });

        let component = $(this);
        $("<span class=\"glyphicon glyphicon-move\"></span>").appendTo(component);

        if ($(this).hasClass('title')) {
            $("<input class=\"form-control\" type=\"text\" value=\"" + component.attr("data-content") + "\">")
                .change(function() {
                    component.attr("data-content", $(this).val());
                })
                .appendTo(component);
        } else if ($(this).hasClass('textarea')) {
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