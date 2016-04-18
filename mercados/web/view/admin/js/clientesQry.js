$(function () {
    clientesCbo();
});

function clientesCbo() {
    $.ajax({
        url: "Clientes",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#clientesCbo").html(data);
                //
                clientesQry();
            }
        }
    });
}

function clientesQry() {
    $.ajax({
        url: "Clientes",
        data: {
            accion: "QRY",
            numpag: $("#clientesCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#clientesQry").html(data);
            }
        }
    });
}

function clientesIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");
    $("#direccion_ins").val("");
    $("#telefono_ins").val("");
    $("#fechaingreso_ins").val("");
    $("#fechacese_ins").val("");

    $.ajax({
        url: "Clientes",
        type: "post",
        data: {
            accion: "VENDEDORES_CBO"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else { // exito
                $("#idvendedor_ins").html(data);

                $.ajax({
                    url: "Clientes",
                    type: "post",
                    data: {
                        accion: "MERCADOS_CBO" //,
                                //idvendedor: $("#idvendedor_ins").val()
                    },
                    success: function (data) {
                        var ok = data.substring(0, 4);

                        if (ok === "!Ok.") {
                            $("#msg_server").html(data.substring(4, data.length)).show();

                        } else {
                            $("#idmercado_ins").html(data);
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
                                            url: "Clientes",
                                            type: "post",
                                            data: {
                                                accion: "INS",
                                                idvendedor: $("#idvendedor_ins").val(),
                                                nombre: $("#nombre_ins").val(),
                                                idmercado: $("#idmercado_ins").val(),
                                                direccion: $("#direccion_ins").val(),
                                                telefono: $("#telefono_ins").val(),
                                                fechaingreso: $("#fechaingreso_ins").val(),
                                                fechacese: $("#fechacese_ins").val(),
                                                idestado: $("#idestado_ins").val()
                                            },
                                            success: function (data) {
                                                var ok = data.substring(0, 4);

                                                if (ok === "!Ok.") {
                                                    $("#error_ins").html(data.substring(4, data.length)).show();

                                                } else {
                                                    $("#dins").dialog("close");
                                                    clientesCbo();
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

function clientesDel() {
    var ids = [];

    $("input[name='idcliente_del']:checked").each(function () {
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
                        url: "Clientes",
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
                                clientesCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function clientesUpd() {
    var id = $("input[name='idcliente_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Clientes",
            type: "post",
            data: {
                accion: "VENDEDORES_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#idvendedor_upd").html(data);

                    $.ajax({
                        url: "Clientes",
                        type: "post",
                        data: {
                            accion: "MERCADOS_CBO" //,
                                    //idvendedor: $("#idvendedor_ins").val()
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                $("#idmercado_upd").html(data);
                                //
                                $.ajax({
                                    url: "Clientes",
                                    type: "post",
                                    data: {
                                        accion: "GET",
                                        idcliente: id
                                    },
                                    success: function (data) {
                                        var ok = data.substring(0, 4);


                                        if (ok === "!Ok.") {
                                            $("#msg_server").html(data.substring(4, data.length)).show();

                                        } else {
                                            var dato = data.split("%%%");

                                            $("#idvendedor_upd").val(dato[0]);
                                            $("#nombre_upd").val(dato[1]);
                                            $("#idmercado_upd").val(dato[2]);
                                            $("#direccion_upd").val(dato[3]);
                                            $("#telefono_upd").val(dato[4]);
                                            $("#fechaingreso_upd").val(dato[5]);
                                            $("#fechacese_upd").val(dato[6]);
                                            $("#idestado_upd").val(dato[7]);

                                            $("#dupd").dialog({
                                                modal: true,
                                                width: 460,
                                                buttons: {
                                                    "Cancelar": function () {
                                                        $(this).dialog("close");
                                                    },
                                                    "Grabar": function () {
                                                        $.ajax({
                                                            url: "Clientes",
                                                            type: "post",
                                                            data: {
                                                                accion: "UPD",
                                                                idcliente: id,
                                                                idvendedor: $("#idvendedor_upd").val(),
                                                                nombre: $("#nombre_upd").val(),
                                                                idmercado: $("#idmercado_upd").val(),
                                                                direccion: $("#direccion_upd").val(),
                                                                telefono: $("#telefono_upd").val(),
                                                                fechaingreso: $("#fechaingreso_upd").val(),
                                                                fechacese: $("#fechacese_upd").val(),
                                                                idestado: $("#idestado_upd").val()
                                                            },
                                                            success: function (data) {
                                                                var ok = data.substring(0, 4);

                                                                if (ok === "!Ok.") {
                                                                    $("#error_upd").html(data.substring(4, data.length)).show();

                                                                } else {
                                                                    clientesCbo();
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
        });
    }
}