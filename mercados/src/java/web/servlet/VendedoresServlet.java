/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.servlet;

import dao.DaoEstados;
import dao.DaoVendedores;
import dao.impl.DaoEstadosImpl;
import dao.impl.DaoVendedoresImpl;
import dto.Vendedores;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.VendedoresValidator;

/**
 *
 * @author Jose
 */
@WebServlet(name = "VendedoresServlet", urlPatterns = {"/view/admin/Vendedores"})
public class VendedoresServlet extends HttpServlet {

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
            DaoVendedores daoVendedores = new DaoVendedoresImpl();
            DaoEstados daoEstados = new DaoEstadosImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "ESTADOS_CBO":
                    List<Object[]> list = daoEstados.estadosCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Estados";
                    }
                    break;

                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    list = daoVendedores.vendedoresQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[1]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[2]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idvendedor_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idvendedor_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Vendedores";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoVendedores.ctasPaginas(filsXpag);

                    if (ctasPagsFils != null) {
                        for (int i = 0; i < ctasPagsFils[0]; i++) {
                            message.append("<option value=\"").append(i).append("\">");
                            message.append(1 + i);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Estados";
                    }
                    break;

                case "INS":
                    Vendedores vendedores = new Vendedores();
                    VendedoresValidator validator = new VendedoresValidator();
                    List<String> list_msg = validator.valida(request, vendedores, false);

                    if (list_msg.isEmpty()) {
                        result = daoVendedores.vendedoresIns(vendedores);

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
                        result = daoVendedores.vendedoresDel(ids);
                    }
                    break;

                case "GET":
                    Integer idvendedor = DeString.aInteger(request.getParameter("idvendedor"));

                    if (idvendedor != null) {
                        vendedores = daoVendedores.vendedoresGet(idvendedor);

                        if (vendedores != null) {
                            message.append(vendedores.getNombre())
                                    .append("%%%")
                                    .append(vendedores.getIdestado());

                        } else {
                            result = "Vendedor no Existe";
                        }

                    } else {
                        result = "ID Vendedor incorrecto";
                    }
                    break;

                case "UPD":
                    vendedores = new Vendedores();
                    validator = new VendedoresValidator();
                    list_msg = validator.valida(request, vendedores, true);

                    if (list_msg.isEmpty()) {
                        result = daoVendedores.vendedoresUpd(vendedores);

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
