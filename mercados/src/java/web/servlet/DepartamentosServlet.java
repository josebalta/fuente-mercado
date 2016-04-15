package web.servlet;

import dao.DaoDepartamentos;
import dao.impl.DaoDepartamentosImpl;
import dto.Departamentos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.DepartamentosValidator;

@WebServlet(name = "DepartamentosServlet", urlPatterns = {"/view/admin/Departamentos"})
public class DepartamentosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer idrol = (Integer) request.getSession().getAttribute("autorizacion");
        String ID = (String) request.getSession().getAttribute("ID");

        if ((ID == null) || (idrol == null)
                || (idrol != 1)
                || (!ID.equals(request.getSession().getId()))) {

            response.sendRedirect("../");

        } else {
            request.setCharacterEncoding("UTF-8");

            String accion = request.getParameter("accion");
            accion = (accion == null) ? "" : accion;
            String result = null;
            String target = null;
            StringBuilder message = new StringBuilder();
            //
            DaoDepartamentos daoDepartamentos = new DaoDepartamentosImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    List<Object[]> list = daoDepartamentos.departamentosQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td colspan=\"2\">").append(u[1]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"iddepartamento_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"iddepartamento_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Departamentos";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoDepartamentos.ctasPaginas(filsXpag);

                    if (ctasPagsFils != null) {
                        for (int i = 0; i < ctasPagsFils[0]; i++) {
                            message.append("<option value=\"").append(i).append("\">");
                            message.append(1 + i);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Roles";
                    }
                    break;

                case "INS":
                    Departamentos departamentos = new Departamentos();
                    DepartamentosValidator validator = new DepartamentosValidator();
                    List<String> list_msg = validator.valida(request, departamentos, false);

                    if (list_msg.isEmpty()) {
                        result = daoDepartamentos.departamentosIns(departamentos);

                    } else {
                        message.append("!Ok.<ol>");
                        for (String msg : list_msg) {
                            message.append("<li>").append(msg).append("</li>");
                        }
                        message.append("</ol>");
                    }
                    break;

                case "DEL":
                    List<Integer> ids = DeString.ids(request.getParameter("ids"));

                    if (ids == null) {
                        result = "Lista de ID(s) incorrecta";
                    } else {
                        result = daoDepartamentos.departamentosDel(ids);
                    }
                    break;

                case "GET":
                    Integer iddepartamento = DeString.aInteger(request.getParameter("iddepartamento"));

                    if (iddepartamento != null) {
                        departamentos = daoDepartamentos.departamentosGet(iddepartamento);

                        if (departamentos != null) {
                            message.append(departamentos.getNombre());

                        } else {
                            result = "Departamento no Existe";
                        }

                    } else {
                        result = "ID Departamento incorrecto";
                    }
                    break;

                case "UPD":
                    departamentos = new Departamentos();
                    validator = new DepartamentosValidator();
                    list_msg = validator.valida(request, departamentos, true);

                    if (list_msg.isEmpty()) {
                        result = daoDepartamentos.departamentosUpd(departamentos);

                    } else {
                        message.append("!Ok.<ol>");
                        for (String msg : list_msg) {
                            message.append("<li>").append(msg).append("</li>");
                        }
                        message.append("</ol>");
                    }
                    break;

                case "":
                    result = "Solicitud requerida";
                    break;

                default:
                    result = "Solicitud no reconocida";
            }

            if (result != null) {
                message.append("!Ok.").append(result);
            }

            if (target == null) {
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.print(message);
                }

            } else {
                response.sendRedirect(target);
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="doGet y doPost">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
