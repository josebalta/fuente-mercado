$(function () {
    $("form.parainfo fieldset").addClass("ui-widget-content");
    $("form.parainfo legend").addClass("ui-widget-header ui-widget-content ui-corner-all");

    $("form.parainfo input[type=text]").addClass("ui-widget ui-widget-content ui-corner-all");
    $("form.parainfo input[type=password]").addClass("ui-widget ui-widget-content ui-corner-all");
    $("form.parainfo textarea").addClass("ui-widget ui-widget-content ui-corner-all");
    $("form.parainfo select").addClass("ui-widget ui-widget-content ui-corner-all");
    $("select.parainfo").addClass("ui-widget ui-widget-content ui-corner-all");

    $("form.parainfo input[type=submit]").button();
    $("form.parainfo input[type=button]").button();
    $("form.parainfo button").button();
    
    $("button.submit").button({icons: {primary: "ui-icon-locked"}});
});
