$(function () {
    vendedoresCbo();
});

function vendedoresCbo() {
    $.ajax({
        url: "Vendedores",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#vendedoresCbo").html(data);
                //
                vendedoresQry();
            }
        }
    });
}

function vendedoresQry() {
    $.ajax({
        url: "Vendedores",
        data: {
            accion: "QRY",
            numpag: $("#vendedoresCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#vendedoresQry").html(data);
            }
        }
    });
}

function vendedoresIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");
    $("#idestado_ins").val("");

    $.ajax({
        url: "Vendedores",
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
                                url: "Vendedores",
                                type: "post",
                                data: {
                                    accion: "INS",
                                    nombre: $("#nombre_ins").val(),
                                    idestado: $("#idestado_ins").val()
                                },
                                success: function (data) {
                                    var ok = data.substring(0, 4);

                                    if (ok === "!Ok.") {
                                        $("#error_ins").html(data.substring(4, data.length)).show();

                                    } else {
                                        $("#dins").dialog("close");
                                        vendedoresCbo();
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

function vendedoresDel() {
    var ids = [];

    $("input[name='idvendedor_del']:checked").each(function () {
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
                        url: "Vendedores",
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
                                vendedoresCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function vendedoresUpd() {
    var id = $("input[name='idvendedor_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Vendedores",
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
                        url: "Vendedores",
                        type: "post",
                        data: {
                            accion: "GET",
                            idvendedor: id
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                var dato = data.split("%%%");

                                $("#nombre_upd").val(dato[0]);
                                $("#idestado_upd").val(dato[1]);

                                $("#dupd").dialog({
                                    modal: true,
                                    width: 460,
                                    buttons: {
                                        "Cancelar": function () {
                                            $(this).dialog("close");
                                        },
                                        "Grabar": function () {
                                            $.ajax({
                                                url: "Vendedores",
                                                type: "post",
                                                data: {
                                                    accion: "UPD",
                                                    idvendedor: id,
                                                    nombre: $("#nombre_upd").val(),
                                                    idestado: $("#idestado_upd").val()
                                                },
                                                success: function (data) {
                                                    var ok = data.substring(0, 4);

                                                    if (ok === "!Ok.") {
                                                        $("#error_upd").html(data.substring(4, data.length)).show();

                                                    } else {
                                                        $("#dupd").dialog("close");
                                                        vendedoresCbo();
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