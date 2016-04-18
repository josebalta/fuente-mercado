$(function () {
    productosCbo();
});

function productosCbo() {
    $.ajax({
        url: "Productos",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#productosCbo").html(data);
                //
                productosQry();
            }
        }
    });
}

function productosQry() {
    $.ajax({
        url: "Productos",
        data: {
            accion: "QRY",
            numpag: $("#productosCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#productosQry").html(data);
            }
        }
    });
}

function productosIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#descripcion_ins").val("");
    $("#preciounitario_ins").val("");
    $("#idestado_ins").val("");

    $.ajax({
        url: "Productos",
        type: "post",
        data: {
            accion: "ESTADOS_CBO"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#idestado_ins").html(data);
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
                                url: "Productos",
                                type: "post",
                                data: {
                                    accion: "INS",
                                    descripcion: $("#descripcion_ins").val(),
                                    preciounitario: $("#preciounitario_ins").val(),
                                    idestado: $("#idestado_ins").val()
                                },
                                success: function (data) {
                                    var ok = data.substring(0, 4);

                                    if (ok === "!Ok.") {
                                        $("#error_ins").html(data.substring(4, data.length)).show();

                                    } else {
                                        $("#dins").dialog("close");
                                        productosCbo();
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

function productosDel() {
    var ids = [];

    $("input[name='idproducto_del']:checked").each(function () {
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
                        url: "Productos",
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
                                productosCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function productosUpd() {
    var id = $("input[name='idproducto_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Productos",
            type: "post",
            data: {
                accion: "ESTADOS_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#idestado_upd").html(data);

                    //
                    $.ajax({
                        url: "Productos",
                        type: "post",
                        data: {
                            accion: "GET",
                            idproducto: id
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                var dato = data.split("%%%");

                                $("#descripcion_upd").val(dato[0]);
                                $("#preciounitario_upd").val(dato[1]);
                                $("#idestado_upd").val(dato[2]);

                                $("#dupd").dialog({
                                    modal: true,
                                    width: 460,
                                    buttons: {
                                        "Cancelar": function () {
                                            $(this).dialog("close");
                                        },
                                        "Grabar": function () {
                                            $.ajax({
                                                url: "Productos",
                                                type: "post",
                                                data: {
                                                    accion: "UPD",
                                                    idproducto: id,
                                                    descripcion: $("#descripcion_upd").val(),
                                                    preciounitario: $("#preciounitario_upd").val(),
                                                    idestado: $("#idestado_upd").val()
                                                },
                                                success: function (data) {
                                                    var ok = data.substring(0, 4);

                                                    if (ok === "!Ok.") {
                                                        $("#error_upd").html(data.substring(4, data.length)).show();

                                                    } else {
                                                        $("#dupd").dialog("close");
                                                        productosCbo();
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