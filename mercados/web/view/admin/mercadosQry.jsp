<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@include file="../../WEB-INF/jspf/aaadmin.jspf" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="../../images/alpa.ico" type="image/gif">
        <title>Mercados</title>
        <link href="../../jq/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
        <link href="../../jq/menu/menu.css" rel="stylesheet" type="text/css"/>
        <link href="../../css/main.css" rel="stylesheet" type="text/css"/>
        <link href="../../parainfo/table.css" rel="stylesheet" type="text/css"/>
        <link href="../../parainfo/form.css" rel="stylesheet" type="text/css"/>
        <link href="../../parainfo/message.css" rel="stylesheet" type="text/css"/>
        <link href="../../parainfo/a.css" rel="stylesheet" type="text/css"/>

        <script src="../../jq/jquery-2.2.0.min.js" type="text/javascript"></script>
        <script src="../../jq/jquery-ui.min.js" type="text/javascript"></script>
        <script src="../../jq/layout/jquery.layout.min.js" type="text/javascript"></script>
        <script src="../../jq/menu/menu.js" type="text/javascript"></script>
        <script src="../../parainfo/table.js" type="text/javascript"></script>
        <script src="../../parainfo/form.js" type="text/javascript"></script>
        <script src="../../parainfo/message.js" type="text/javascript"></script>
        <script src="../../js/main.js" type="text/javascript"></script>

        <script src="js/mercadosQry.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="ui-layout-north">
            <%@include file="../../WEB-INF/jspf/top.jspf" %>
        </div>

        <div class="ui-layout-west">
            <%@include file="../../WEB-INF/jspf/menuadmin.jspf" %>
        </div>

        <div class="ui-layout-center" style="padding-top: 30px">
            <table class="parainfo" style="margin: auto;width: 500px">
                <caption>
                    <div style="float: left">Lista de Mercados</div>
                    <div style="float: right">
                        <select id="mercadosCbo" class="parainfo" onchange="mercadosQry();"></select>
                    </div>
                    <div style="clear: both"></div>
                </caption>

                <thead>
                    <tr>
                        <td>Abreviatura</td>
                        <td>Nombre</td>
                        <td>Distrito</td>
                        <td>Direcci&oacute;n</td>
                        <td>Estado</td>
                        <th class="crud">
                            <a class="ins" href="#" onclick="mercadosIns();"
                               title="Nuevo Registro">
                                <span></span></a>
                        </th>
                        <th class="crud">
                            <a class="del" href="#" onclick="mercadosDel();"
                               title="Retirar Registros">
                                <span></span></a>
                        </th>
                        <th class="crud">
                            <a class="upd" href="#" onclick="mercadosUpd();"
                               title="Actualizar Registro">
                                <span></span></a>
                        </th>
                    </tr>
                </thead>
                <tbody id="mercadosQry"></tbody>
            </table>
        </div>

        <%-- INS --%>
        <div style="display: none">
            <div id="dins" title="Nuevo registro">
                <form class="parainfo">
                    <table>
                        <tr>
                            <td>Abreviatura</td>
                            <td>
                                <input type="text" id="abreviatura_ins" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Nombre</td>
                            <td>
                                <input type="text" id="nombre_ins" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Distrito</td>
                            <td>
                                <select id="iddistrito_ins" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Direcci&oacute;n</td>
                            <td>
                                <input type="text" id="direccion_ins" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Estado</td>
                            <td>
                                <select id="idestado_ins" style="width: 310px"></select>
                            </td>
                        </tr>
                    </table>
                </form>

                <div id="error_ins" class="msg_error ui-state-highlight ui-corner-all"></div>
            </div>
        </div>

        <%-- UPD --%>
        <div style="display: none">
            <div id="dupd" title="Actualizar registro">
                <form class="parainfo">
                    <table>
                        <tr>
                            <td>Abreviatura</td>
                            <td>
                                <input type="text" id="abreviatura_upd" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Nombre</td>
                            <td>
                                <input type="text" id="nombre_upd" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Distrito</td>
                            <td>
                                <select id="iddistrito_upd" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Direcci&oacute;n</td>
                            <td>
                                <input type="text" id="direccion_udp" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Estado</td>
                            <td>
                                <select id="idestado_upd" style="width: 310px"></select>
                            </td>
                        </tr>
                    </table>
                </form>

                <div id="error_upd" class="msg_error ui-state-highlight ui-corner-all"></div>
            </div>
        </div>

        <%-- mensajes de error enviados por el server --%>
        <div id="msg_server" class="msg_error ui-state-highlight ui-corner-all"></div>

        <%-- para diálogo de mensajería en el cliente --%>
        <div style="display: none">
            <div id="dlg_message"><p id="p_message"></p></div>
        </div>
    </body>
</html>
