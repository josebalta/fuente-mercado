/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.servlet;

import dao.DaoDistritos;
import dao.DaoProvincias;
import dao.DaoZonas;
import dao.impl.DaoDistritosImpl;
import dao.impl.DaoProvinciasImpl;
import dao.impl.DaoZonasImpl;
import dto.Distritos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.DistritosValidator;

@WebServlet(name = "DistritoServlet", urlPatterns = {"/view/admin/Distritos"})
public class DistritosServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
            DaoDistritos daoDistritos = new DaoDistritosImpl();
            DaoProvincias daoProvincias = new DaoProvinciasImpl();
            DaoZonas daoZonas = new DaoZonasImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "PROVINCIAS_CBO":
                    List<Object[]> list = daoProvincias.provinciasCbo();

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

                case "ZONAS_CBO":
                    list = daoZonas.zonasCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Zonas";
                    }
                    break;

                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    list = daoDistritos.distritosQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[3]).append("</td>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[1]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"iddistrito_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"iddistrito_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Distritos";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoDistritos.ctasPaginas(filsXpag);

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
                    Distritos distritos = new Distritos();
                    DistritosValidator validator = new DistritosValidator();
                    List<String> list_msg = validator.valida(request, distritos, false);

                    if (list_msg.isEmpty()) {
                        result = daoDistritos.distritosIns(distritos);

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
                        result = daoDistritos.distritosDel(ids);
                    }
                    break;

                case "GET":
                    Integer iddistrito = DeString.aInteger(request.getParameter("iddistrito"));

                    if (iddistrito != null) {
                        distritos = daoDistritos.distritosGet(iddistrito);

                        if (distritos != null) {
                            message.append(distritos.getIdprovincia())
                                    .append("%%%")
                                    .append(distritos.getIdzona())
                                    .append("%%%")
                                    .append(distritos.getNombre());

                        } else {
                            result = "Distrito no Existe";
                        }

                    } else {
                        result = "ID Distrito incorrecto";
                    }
                    break;

                case "UPD":
                    distritos = new Distritos();
                    validator = new DistritosValidator();
                    list_msg = validator.valida(request, distritos, true);

                    if (list_msg.isEmpty()) {
                        result = daoDistritos.distritosUpd(distritos);

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
