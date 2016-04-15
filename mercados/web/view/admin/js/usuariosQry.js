$(function () {
    usuariosCbo();
});

function usuariosCbo() {
    $.ajax({
        url: "Usuarios",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#usuariosCbo").html(data);
                //
                usuariosQry();
            }
        }
    });
}

function usuariosQry() {
    $.ajax({
        url: "Usuarios",
        data: {
            accion: "QRY",
            numpag: $("#usuariosCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#usuariosQry").html(data);
            }
        }
    });
}

function usuariosIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");
    $("#clave_ins").val("");

    $.ajax({
        url: "Usuarios",
        type: "post",
        data: {
            accion: "ROLES_CBO"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#idrol_ins").html(data);
                $("#idrol_ins").val("2");
                //

                $("#dins").dialog({
                    modal: true,
                    width: 420,
                    buttons: {
                        "Cancelar": function () {
                            $(this).dialog("close");
                        },
                        "Grabar": function () {
                            $.ajax({
                                url: "Usuarios",
                                type: "post",
                                data: {
                                    accion: "INS",
                                    idrol: $("#idrol_ins").val(),
                                    nombre: $("#nombre_ins").val(),
                                    clave: $("#clave_ins").val()
                                },
                                success: function (data) {
                                    var ok = data.substring(0, 4);

                                    if (ok === "!Ok.") {
                                        $("#error_ins").html(data.substring(4, data.length)).show();

                                    } else {
                                        usuariosCbo();
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

function usuariosDel() {
    var ids = [];

    $("input[name='idusuario_del']:checked").each(function () {
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
                        url: "Usuarios",
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
                                usuariosCbo();
                                $("#dlg_message").dialog("close");
                            }
                        }
                    });
                }
            }
        });
    }
}

function usuariosUpd() {
    var id = $("input[name='idusuario_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Usuarios",
            type: "post",
            data: {
                accion: "ROLES_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#idrol_upd").html(data);

                    //
                    $.ajax({
                        url: "Usuarios",
                        type: "post",
                        data: {
                            accion: "GET",
                            idusuario: id
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                var dato = data.split("%%%");

                                $("#idrol_upd").val(dato[0]);
                                $("#nombre_upd").val(dato[1]);

                                $("#dupd").dialog({
                                    modal: true,
                                    width: 420,
                                    buttons: {
                                        "Cancelar": function () {
                                            $(this).dialog("close");
                                        },
                                        "Grabar": function () {
                                            $.ajax({
                                                url: "Usuarios",
                                                type: "post",
                                                data: {
                                                    accion: "UPD",
                                                    idusuario: id,
                                                    idrol: $("#idrol_upd").val(),
                                                    nombre: $("#nombre_upd").val()
                                                },
                                                success: function (data) {
                                                    var ok = data.substring(0, 4);

                                                    if (ok === "!Ok.") {
                                                        $("#error_upd").html(data.substring(4, data.length)).show();

                                                    } else {
                                                        usuariosCbo();
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