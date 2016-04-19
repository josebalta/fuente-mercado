<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@include file="../../WEB-INF/jspf/aaadmin.jspf" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="../../images/alpa.ico" type="image/gif">
        <title>Ventas</title>
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

        <script src="js/ventasQry.js" type="text/javascript"></script>
    </head>

    <body>
        <div class="ui-layout-north">
            <%@include file="../../WEB-INF/jspf/top.jspf" %>
        </div>

        <div class="ui-layout-west">
            <%@include file="../../WEB-INF/jspf/menuadmin.jspf" %>
        </div>     

        <div class="ui-layout-center" style="padding-top: 30px">





            <%-- FORM --%>
            <div id="dins" title="Nuevo registro">
                <form class="parainfo">
                    <table>
                        <tr>
                            <td>Vendedor</td>
                            <td style="padding-right: 25px">
                                <select id="idvendedor_ins" style="width: 210px"></select>
                            </td>
                            <td>Fecha</td>
                            <td>
                                <input type="date" id="fecha_ins" maxlength="9" style="width: 200px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Cliente</td>
                            <td style="padding-right: 25px">
                                <select id="idcliente_ins" style="width: 210px"></select>
                            </td>
                            <td>Importe</td>
                            <td>
                                <input type="text" id="importe_ins" maxlength="9" style="width: 200px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Mercado</td>
                            <td style="padding-right: 25px">
                                <select id="idmercado_ins" style="width: 210px"></select>
                            </td>
                            <td>Saldo</td>
                            <td>
                                <input type="text" id="saldo_ins" maxlength="9" style="width: 200px"/>
                            </td>
                        </tr>   
                    </table>
                </form>

                <!--<div id="error_ins" class="msg_error ui-state-highlight ui-corner-all"></div>-->
            </div>




            <table class="parainfo" style="margin: auto;width: 600px">
                <caption>
                    <div style="float: left">Venta</div>
                    <div style="float: right">
                        <span  style="font-weight: normal;margin-left: 16px">
                            B&uacute;squeda</span> 
                        <input type="text" id="tutoFindTxt" class="parainfo"
                               style="width: 120px;text-transform: uppercase" 
                               maxlength="30"/> 
                        <a class="parainfo" style="vertical-align: sub" href="#" onclick="busquedaQry();" title="Buscar Registro">
                            <img src="../../images/icons/search.gif" alt="Buscar" /></a>
                    </div>
                    <div style="clear: both"></div>
                </caption>

                <thead>
                    <tr>
                        <td>Producto</td>
                        <td>Cantidad</td>
                        <td>Precio venta</td>
                        <td>Suma de precios</td>
                        <th class="crud">
                            <a class="ins" href="#" onclick="ventasIns();"
                               title="Nuevo Registro">
                                <span></span></a>
                        </th>
                        <th class="crud">
                            <a class="del" href="#" onclick="ventasDel();"
                               title="Retirar Registros">
                                <span></span></a>
                        </th>
                        <th class="crud">
                            <a class="upd" href="#" onclick="ventasUpd();"
                               title="Actualizar Registro">
                                <span></span></a>
                        </th>
                    </tr>
                </thead>
                <tbody id="ventasQry"></tbody>

                <tfoot>
                    <tr>
                        <td colspan="3" style="" align="right">Total S/.</td>
                        <td></td>
                        <td style="text-align: right" class="font_foot" colspan="3">
                            <span>Página</span>
                            <select id="ventasCbo" class="parainfo" onchange="ventasQry();"></select>
                        </td>

                        </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <%-- INS --%>
        <div style="display: none">
            <div id="dins" title="Nuevo registro">
                <form class="parainfo">
                    <table>
                        <tr>
                            <td>Nombre</td>
                            <td>
                                <input type="text" id="nombre_ins" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Vendedor</td>
                            <td>
                                <select id="idvendedor_ins" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Mercado</td>
                            <td>
                                <select id="idmercado_ins" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Direcci&oacute;n</td>
                            <td>
                                <input type="text" id="direccion_ins" maxlength="255" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Celular</td>
                            <td>
                                <input type="text" id="telefono_ins" maxlength="9" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fecha ingreso</td>
                            <td>
                                <input type="date" id="fechaingreso_ins" maxlength="9" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fecha cese</td>
                            <td>
                                <input type="date" id="fechacese_ins" maxlength="9" style="width: 300px"/>
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
            <div id="dupd" title="Actualiza registro">
                <form class="parainfo">
                    <table>
                        <tr>
                            <td>Nombre</td>
                            <td>
                                <input type="text" id="nombre_upd" maxlength="150" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Vendedor</td>
                            <td>
                                <select id="idvendedor_upd" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Mercado</td>
                            <td>
                                <select id="idmercado_upd" style="width: 310px"></select>
                            </td>
                        </tr>
                        <tr>
                            <td>Direcci&oacute;n</td>
                            <td>
                                <input type="text" id="direccion_upd" maxlength="255" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Celular</td>
                            <td>
                                <input type="text" id="telefono_upd" maxlength="9" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fecha ingreso</td>
                            <td>
                                <input type="date" id="fechaingreso_upd" maxlength="9" style="width: 300px"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Fecha cese</td>
                            <td>
                                <input type="date" id="fechacese_upd" maxlength="9" style="width: 300px"/>
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

        <%-- para diálogo de mensajería en el venta --%>
        <div style="display: none">
            <div id="dlg_message"><p id="p_message"></p></div>
        </div>
    </body>
</html>
