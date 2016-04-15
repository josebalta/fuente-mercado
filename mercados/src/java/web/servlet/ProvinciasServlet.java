package web.servlet;

import dao.DaoDepartamentos;
import dao.DaoProvincias;
import dao.impl.DaoDepartamentosImpl;
import dao.impl.DaoProvinciasImpl;
import dto.Provincias;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.ProvinciasValidator;

@WebServlet(name = "ProvinciasServlet", urlPatterns = {"/view/admin/Provincias"})
public class ProvinciasServlet extends HttpServlet {

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
            DaoProvincias daoProvincias = new DaoProvinciasImpl();
            DaoDepartamentos daoDepartamentos = new DaoDepartamentosImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "DEPARTAMENTOS_CBO":
                    List<Object[]> list = daoDepartamentos.departamentosCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Departamentos";
                    }
                    break;

                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    list = daoProvincias.provinciasQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[1]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idprovincia_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idprovincia_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Provincias";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoProvincias.ctasPaginas(filsXpag);

                    if (ctasPagsFils != null) {
                        for (int i = 0; i < ctasPagsFils[0]; i++) {
                            message.append("<option value=\"").append(i).append("\">");
                            message.append(1 + i);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Departamentos";
                    }
                    break;

                case "INS":
                    Provincias provincias = new Provincias();
                    ProvinciasValidator validator = new ProvinciasValidator();
                    List<String> list_msg = validator.valida(request, provincias, false);

                    if (list_msg.isEmpty()) {
                        result = daoProvincias.provinciasIns(provincias);

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
                        result = daoProvincias.provinciasDel(ids);
                    }
                    break;

                case "GET":
                    Integer idprovincia = DeString.aInteger(request.getParameter("idprovincia"));

                    if (idprovincia != null) {
                        provincias = daoProvincias.provinciasGet(idprovincia);

                        if (provincias != null) {
                            message.append(provincias.getIddepartamento())
                                    .append("%%%")
                                    .append(provincias.getNombre());

                        } else {
                            result = "Provincia no Existe";
                        }

                    } else {
                        result = "ID Provincia incorrecto";
                    }
                    break;

                case "UPD":
                    provincias = new Provincias();
                    validator = new ProvinciasValidator();
                    list_msg = validator.valida(request, provincias, true);

                    if (list_msg.isEmpty()) {
                        result = daoProvincias.provinciasUpd(provincias);

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
