<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="WEB-INF/jspf/browser.jspf" %>
<%    session.invalidate();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="../../images/alpa.ico" type="image/gif">
        <title>indalpamayo.com</title>
        <link href="jq/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
        <link href="css/main.css" type="text/css" rel="stylesheet"/>
        <link href="parainfo/form.css" type="text/css" rel="stylesheet"/>

        <script src="jq/jquery-2.2.0.min.js" type="text/javascript"></script>
        <script src="jq/jquery-ui.min.js" type="text/javascript"></script>
        <script src="parainfo/form.js" type="text/javascript"></script>
    </head>
    <body>
        <form class="parainfo" action="Autentica" method="post"
              style="margin: auto;display: table;margin-top: 50px">
            <input type="hidden" name="accion" value="LOGIN"/>

            <fieldset class="ui-corner-all">
                <legend>Formulario de Autenticación</legend>

                <table class="tabla">
                    <tr>
                        <th rowspan="4"><img src="images/lock.png"/></th>
                        <td><label for="usuario">Nombre</label></td>
                        <td>
                            <input type="text" name="nombre" maxlength="50" value="José Balta" id="nombre" style="width: 200px"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="clave">Clave</label></td>
                        <td>
                            <!--<input type="password" name="clave" maxlength="50" id="clave" value="${usuarios.clave}" style="width: 200px"/>-->
                            <input type="password" name="clave" maxlength="50" id="clave" value="12345" style="width: 200px"/> <!--LUEGO BORRAR-->
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: right">
                            <button type="submit" class="submit">Autenticarse</button>
                            <br/>
                            <span style="color: #777">
                                <!--<img src="images/i-parainformaticos.gif" style="vertical-align: bottom"/>indalpamayo.com<br/>-->
                                <br/>indalpamayo.com<br/>
                            </span>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>

        <%-- para errores --%>
        <c:if test="${msg.size() > 0}">
            <div class="msg_error ui-state-error ui-corner-all">
                <ul>
                    <c:forEach var="m" items="${msg}"><li>${m}</li></c:forEach>
                    </ul>
                </div>
        </c:if>
    </body>
</html>

