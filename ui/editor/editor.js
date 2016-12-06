$(document).ready(function(){
    wrapColumn();
    view();
    $('#modeSwitch').change(function() {
        switchMode();
    })
});

var switchMode = function () {
    if (mode === 'view') {
        edit();
    } else {
        view();
    }
}

var view = function () {
    mode = 'view';
    $('.component').each(function(){
        new Component($(this)).view();
    });
}

var edit = function () {
    mode = 'edit';
    $('.component').each(function() {
        new Component($(this)).edit();
    });
}

var wrapColumn = function() {
    $('li.column').each(function() {
        $(this).replaceWith('<div class="panel panel-default col-md-6"><div class="panel-body">' + this.innerHTML + '</div></div>');
    });
}

function Component(component){
    let type = component.attr("class");
    let data = component.attr("data-content");
    let height = component.attr("data-height");
    let htmlElement;

    this.view = function() {
        undraggable();
        removeContent();
        if (type.includes('title')) {
            htmlElement = "<h4>"+ data +"</h4>";
        } else if (type.includes('textarea')) {
            htmlElement = "<p style=\"height: "+ height + "px\">"+ data +"</p>";
        }
        $(htmlElement).appendTo(component);
    }

    this.edit = function() {
        draggable();
        removeContent();
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
        $(htmlElement).appendTo(component);
    }

    function removeContent() {
        component.children().each(function(){
            $(this).remove();
        });
    }

    function undraggable() {
        component.closest('.row').draggable({disabled: true, axis: "y"});
    }

    function draggable() {
        component.closest('.row').draggable({disabled: false});
    }
}