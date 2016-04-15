$(function () {
    departamentosCbo();
});

function departamentosCbo() {
    $.ajax({
        url: "Departamentos",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#departamentosCbo").html(data);
                
                departamentosQry();
            }
        }
    });
}

function departamentosQry() {
    $.ajax({
        url: "Departamentos",
        data: {
            accion: "QRY",
            numpag: $("#departamentosCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#departamentosQry").html(data);
            }
        }
    });
}

function departamentosIns() {
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
                    url: "Departamentos",
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
                            departamentosCbo();
                            $("#dins").dialog("close");
                        }
                    }
                });
            }
        }
    });
}

function departamentosDel() {
    var ids = [];

    $("input[name='iddepartamento_del']:checked").each(function () {
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
                        url: "Departamentos",
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
                                departamentosCbo();
                                $("#dlg_message").dialog("close");
                            }
                        }
                    });
                }
            }
        });
    }
}

function departamentosUpd() {
    var id = $("input[name='iddepartamento_upd']:checked").val();

    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        //
        $.ajax({
            url: "Departamentos",
            type: "post",
            data: {
                accion: "GET",
                iddepartamento: id
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
                                    url: "Departamentos",
                                    type: "post",
                                    data: {
                                        accion: "UPD",
                                        iddepartamento: id,
//                                                    idrol: $("#idrol_upd").val(),
                                        nombre: $("#nombre_upd").val()
                                    },
                                    success: function (data) {
                                        var ok = data.substring(0, 4);

                                        if (ok === "!Ok.") {
                                            $("#error_upd").html(data.substring(4, data.length)).show();

                                        } else {
                                            departamentosCbo();
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