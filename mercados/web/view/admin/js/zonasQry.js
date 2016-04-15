$(function () {
    zonasCbo();
});

function zonasCbo() {
    $.ajax({
        url: "Zonas",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#zonasCbo").html(data);
                //
                zonasQry();
            }
        }
    });
}

function zonasQry() {
    $.ajax({
        url: "Zonas",
        data: {
            accion: "QRY",
            numpag: $("#zonasCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#zonasQry").html(data);
            }
        }
    });
}

function zonasIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");

    $("#dins").dialog({
        modal: true,
        width: 420,
        buttons: {
            "Cancelar": function () {
                $(this).dialog("close");
            },
            "Grabar": function () {
                $.ajax({
                    url: "Zonas",
                    type: "post",
                    data: {
                        accion: "INS",
                        nombre: $("#nombre_ins").val()
                    },
                    success: function (data) {
                        var ok = data.substring(0, 4);

                        if (ok === "!Ok.") {
                            $("#error_ins").html(data.substring(4, data.length)).show();

                        } else {
                            zonasCbo();
                            $("#dins").dialog("close");
                        }
                    }
                });
            }
        }
    });
}

function zonasDel() {
    var ids = [];

    $("input[name='idzona_del']:checked").each(function () {
        ids.push($(this).val());
    });

    if (ids.length === 0) {
        message("Advertencia", "Seleccione fila(s) a Retirar");

    } else {
        $("#p_message").html("¿Retirar registro(s)?");
        $("#dlg_message").dialog({
            modal: true,
            width: 440,
            title: "Advertencia",
            buttons: {
                "No": function () {
                    $(this).dialog("close");
                },
                "Si": function () {
                    $(this).dialog("close");

                    $.ajax({
                        url: "Zonas",
                        type: "post",
                        data: {
                            accion: "DEL",
                            ids: ids.toString()
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                zonasCbo();
                                $("#dlg_message").dialog("close");
                            }
                        }
                    });
                }
            }
        });
    }
}

function zonasUpd() {
    var id = $("input[name='idzona_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        //
        $.ajax({
            url: "Zonas",
            type: "post",
            data: {
                accion: "GET",
                idzona: id
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    var dato = data.split("%%%");

//                                $("#idrol_upd").val(dato[0]);
                    $("#nombre_upd").val(dato[0]);

                    $("#dupd").dialog({
                        modal: true,
                        width: 420,
                        buttons: {
                            "Cancelar": function () {
                                $(this).dialog("close");
                            },
                            "Grabar": function () {
                                $.ajax({
                                    url: "Zonas",
                                    type: "post",
                                    data: {
                                        accion: "UPD",
                                        idzona: id,
//                                                    idrol: $("#idrol_upd").val(),
                                        nombre: $("#nombre_upd").val()
                                    },
                                    success: function (data) {
                                        var ok = data.substring(0, 4);

                                        if (ok === "!Ok.") {
                                            $("#error_upd").html(data.substring(4, data.length)).show();

                                        } else {
                                            zonasCbo();
                                            $("#dupd").dialog("close");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}