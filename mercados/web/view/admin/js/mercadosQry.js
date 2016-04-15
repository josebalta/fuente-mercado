$(function () {
    mercadosCbo();
});

function mercadosCbo() {
    $.ajax({
        url: "Mercados",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#mercadosCbo").html(data);
                //
                mercadosQry();
            }
        }
    });
}

function mercadosQry() {
    $.ajax({
        url: "Mercados",
        data: {
            accion: "QRY",
            numpag: $("#mercadosCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#mercadosQry").html(data);
            }
        }
    });
}

function mercadosIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#abreviatura_ins").val("");
    $("#nombre_ins").val("");
    $("#direccion_ins").val("");

    $.ajax({
        url: "Mercados",
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
                $("#abreviatura_ins").val("");
                $("#nombre_ins").val("");
                $("#iddistrito_ins").val("");
                $("#direccion_ins").val("");
                $("#idestado_ins").val("");
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
                                url: "Mercados",
                                type: "post",
                                data: {
                                    accion: "INS",
                                    abreviatura: $("#abreviatura_ins").val(),
                                    nombre: $("#nombre_ins").val(),
                                    iddistrito: $("#iddistrito_ins").val(),
                                    direccion: $("#direccion_ins").val(),
                                    idestado: $("#idestado_ins").val()
                                },
                                success: function (data) {
                                    var ok = data.substring(0, 4);

                                    if (ok === "!Ok.") {
                                        $("#error_ins").html(data.substring(4, data.length)).show();

                                    } else {
                                        mercadosCbo();
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

function mercadosDel() {
    var ids = [];

    $("input[name='idmercado_del']:checked").each(function () {
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
                        url: "Mercados",
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
                                mercadosCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function mercadosUpd() {
    var id = $("input[name='idmercado_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Mercados",
            type: "post",
            data: {
                accion: "DISTRITOS_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#iddepartamento_upd").html(data);

                    //
                    $.ajax({
                        url: "Mercados",
                        type: "post",
                        data: {
                            accion: "GET",
                            idmercado: id
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                var dato = data.split("%%%");

                                $("#abreviatura_upd").val(dato[0]);
                                $("#nombre_upd").val(dato[1]);
                                $("#iddistrito_upd").val(dato[2]);
                                $("#direccion_upd").val(dato[3]);
                                $("#idestado_upd").val(dato[4]);

                                $("#dupd").dialog({
                                    modal: true,
                                    width: 460,
                                    buttons: {
                                        "Cancelar": function () {
                                            $(this).dialog("close");
                                        },
                                        "Grabar": function () {
                                            $.ajax({
                                                url: "Mercados",
                                                type: "post",
                                                data: {
                                                    accion: "UPD",
                                                    idmercado: id,
                                                    abreviatura: $("#abreviatura_upd").val(),
                                                    nombre: $("#nombre_upd").val(),
                                                    iddistrito: $("#iddistrito_upd").val(),
                                                    direccion: $("#direccion_upd").val(),
                                                    idestado: $("#idestado_upd").val()
                                                },
                                                success: function (data) {
                                                    var ok = data.substring(0, 4);

                                                    if (ok === "!Ok.") {
                                                        $("#error_upd").html(data.substring(4, data.length)).show();

                                                    } else {
                                                        mercadosCbo();
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