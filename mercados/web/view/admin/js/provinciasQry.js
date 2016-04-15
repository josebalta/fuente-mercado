$(function () {
    provinciasCbo();
});

function provinciasCbo() {
    $.ajax({
        url: "Provincias",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#provinciasCbo").html(data);
                //
                provinciasQry();
            }
        }
    });
}

function provinciasQry() {
    $.ajax({
        url: "Provincias",
        data: {
            accion: "QRY",
            numpag: $("#provinciasCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#provinciasQry").html(data);
            }
        }
    });
}

function provinciasIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");

    $.ajax({
        url: "Provincias",
        type: "post",
        data: {
            accion: "DEPARTAMENTOS_CBO"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#iddepartamento_ins").html(data);
                $("#nombre_ins").val("");
                //

                $("#dins").dialog({
                    modal: true,
                    width: 460,
                    buttons: {
                        "Cancelar": function () {
                            $(this).dialog("close");
                        },
                        "Grabar": function () {
                            $.ajax({
                                url: "Provincias",
                                type: "post",
                                data: {
                                    accion: "INS",
                                    iddepartamento: $("#iddepartamento_ins").val(),
                                    nombre: $("#nombre_ins").val()
                                },
                                success: function (data) {
                                    var ok = data.substring(0, 4);

                                    if (ok === "!Ok.") {
                                        $("#error_ins").html(data.substring(4, data.length)).show();

                                    } else {
                                        provinciasCbo();
                                        $("#dins").dialog("close");
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

function provinciasDel() {
    var ids = [];

    $("input[name='idprovincia_del']:checked").each(function () {
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
                        url: "Provincias",
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
                                $("#dlg_message").dialog("close");
                                provinciasCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function provinciasUpd() {
    var id = $("input[name='idprovincia_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Provincias",
            type: "post",
            data: {
                accion: "DEPARTAMENTOS_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#iddepartamento_upd").html(data);

                    //
                    $.ajax({
                        url: "Provincias",
                        type: "post",
                        data: {
                            accion: "GET",
                            idprovincia: id
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                var dato = data.split("%%%");

                                $("#iddepartamento_upd").val(dato[0]);
                                $("#nombre_upd").val(dato[1]);

                                $("#dupd").dialog({
                                    modal: true,
                                    width: 460,
                                    buttons: {
                                        "Cancelar": function () {
                                            $(this).dialog("close");
                                        },
                                        "Grabar": function () {
                                            $.ajax({
                                                url: "Provincias",
                                                type: "post",
                                                data: {
                                                    accion: "UPD",
                                                    idprovincia: id,
                                                    iddepartamento: $("#iddepartamento_upd").val(),
                                                    nombre: $("#nombre_upd").val()
                                                },
                                                success: function (data) {
                                                    var ok = data.substring(0, 4);

                                                    if (ok === "!Ok.") {
                                                        $("#error_upd").html(data.substring(4, data.length)).show();

                                                    } else {
                                                        provinciasCbo();
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
        });
    }
}