$(function () {
    distritosCbo();
});

function distritosCbo() {
    $.ajax({
        url: "Distritos",
        data: {
            accion: "PAGS"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#distritosCbo").html(data);
                //
                distritosQry();
            }
        }
    });
}

function distritosQry() {
    $.ajax({
        url: "Distritos",
        data: {
            accion: "QRY",
            numpag: $("#distritosCbo").val()
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else {
                $("#distritosQry").html(data);
            }
        }
    });
}

function distritosIns() {
    // limpiando
    $("#error_ins").html("").hide();
    $("#nombre_ins").val("");

    $.ajax({
        url: "Distritos",
        type: "post",
        data: {
            accion: "PROVINCIAS_CBO"
        },
        success: function (data) {
            var ok = data.substring(0, 4);

            if (ok === "!Ok.") {
                $("#msg_server").html(data.substring(4, data.length)).show();

            } else { // exito
                $("#idprovincia_ins").html(data);

                $.ajax({
                    url: "Distritos",
                    type: "post",
                    data: {
                        accion: "ZONAS_CBO" //,
                                //idprovincia: $("#idprovincia_ins").val()
                    },
                    success: function (data) {
                        var ok = data.substring(0, 4);

                        if (ok === "!Ok.") {
                            $("#msg_server").html(data.substring(4, data.length)).show();

                        } else {
                            $("#idzona_ins").html(data);

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
                                            url: "Distritos",
                                            type: "post",
                                            data: {
                                                accion: "INS",
                                                idprovincia: $("#idprovincia_ins").val(),
                                                idzona: $("#idzona_ins").val(),
                                                nombre: $("#nombre_ins").val()
                                            },
                                            success: function (data) {
                                                var ok = data.substring(0, 4);

                                                if (ok === "!Ok.") {
                                                    $("#error_ins").html(data.substring(4, data.length)).show();

                                                } else {
                                                    $("#dins").dialog("close");
                                                    distritosCbo();
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

function distritosDel() {
    var ids = [];

    $("input[name='iddistrito_del']:checked").each(function () {
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
                        url: "Distritos",
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
                                distritosCbo();
                            }
                        }
                    });
                }
            }
        });
    }
}

function distritosUpd() {
    var id = $("input[name='iddistrito_upd']:checked").val();
    
    if (isNaN(id)) {
        message("Advertencia", "Seleccione Fila para Actualizar Datos");

    } else {
        $.ajax({
            url: "Distritos",
            type: "post",
            data: {
                accion: "PROVINCIAS_CBO"
            },
            success: function (data) {
                var ok = data.substring(0, 4);

                if (ok === "!Ok.") {
                    $("#msg_server").html(data.substring(4, data.length)).show();

                } else {
                    $("#idprovincia_upd").html(data);

                    $.ajax({
                        url: "Distritos",
                        type: "post",
                        data: {
                            accion: "ZONAS_CBO" //,
                                    //idprovincia: $("#idprovincia_ins").val()
                        },
                        success: function (data) {
                            var ok = data.substring(0, 4);

                            if (ok === "!Ok.") {
                                $("#msg_server").html(data.substring(4, data.length)).show();

                            } else {
                                $("#idzona_upd").html(data);
                                //
                                $.ajax({
                                    url: "Distritos",
                                    type: "post",
                                    data: {
                                        accion: "GET",
                                        iddistrito: id
                                    },
                                    success: function (data) { 
                                        var ok = data.substring(0, 4);
                                                                

                                        if (ok === "!Ok.") {
                                            $("#msg_server").html(data.substring(4, data.length)).show();

                                        } else {
                                            var dato = data.split("%%%");

                                            $("#idprovincia_upd").val(dato[0]);
                                            $("#idzona_upd").val(dato[1]);
                                            $("#nombre_upd").val(dato[2]);

                                            $("#dupd").dialog({
                                                modal: true,
                                                width: 460,
                                                buttons: {
                                                    "Cancelar": function () {
                                                        $(this).dialog("close");
                                                    },
                                                    "Grabar": function () {
                                                        $.ajax({
                                                            url: "Distritos",
                                                            type: "post",
                                                            data: { 
                                                                accion: "UPD",
                                                                iddistrito: id,
                                                                idprovincia: $("#idprovincia_upd").val(),
                                                                idzona: $("#idzona_upd").val(),
                                                                nombre: $("#nombre_upd").val()
                                                            },
                                                            success: function (data) {
                                                                var ok = data.substring(0, 4);
   
                                                                if (ok === "!Ok.") {
                                                                    $("#error_upd").html(data.substring(4, data.length)).show();

                                                                } else {
                                                                    distritosCbo();
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