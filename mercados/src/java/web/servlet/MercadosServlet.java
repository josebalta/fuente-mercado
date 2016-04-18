/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.servlet;

import dao.DaoDepartamentos;
import dao.DaoDistritos;
import dao.DaoEstados;
import dao.DaoMercados;
import dao.impl.DaoDepartamentosImpl;
import dao.impl.DaoDistritosImpl;
import dao.impl.DaoEstadosImpl;
import dao.impl.DaoMercadosImpl;
import dto.Mercados;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.MercadosValidator;

/**
 *
 * @author Jose
 */
@WebServlet(name = "MercadosServlet", urlPatterns = {"/view/admin/Mercados"})
public class MercadosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            DaoMercados daoMercados = new DaoMercadosImpl();
            DaoDistritos daoDistritos = new DaoDistritosImpl();
            DaoEstados daoEstados = new DaoEstadosImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "DISTRITOS_CBO":
                    List<Object[]> list = daoDistritos.distritosCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta a Distritos";
                    }
                    break;

                case "ESTADOS_CBO":
                    list = daoEstados.estadosCbo();

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
                    list = daoMercados.mercadosQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[1]).append("</td>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td>").append(u[3]).append("</td>");
                            message.append("<td>").append(u[4]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[5]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idmercado_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idmercado_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Mercados";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoMercados.ctasPaginas(filsXpag);

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
                    Mercados mercados = new Mercados();
                    MercadosValidator validator = new MercadosValidator();
                    List<String> list_msg = validator.valida(request, mercados, false);

                    if (list_msg.isEmpty()) {
                        result = daoMercados.mercadosIns(mercados);

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
                        result = daoMercados.mercadosDel(ids);
                    }
                    break;

                case "GET":
                    Integer idmercado = DeString.aInteger(request.getParameter("idmercado"));

                    if (idmercado != null) {
                        mercados = daoMercados.mercadosGet(idmercado);

                        if (mercados != null) {
                            message.append(mercados.getAbreviado())
                                    .append("%%%")
                                    .append(mercados.getNombre())
                                    .append("%%%")
                                    .append(mercados.getIddistrito())
                                    .append("%%%")
                                    .append(mercados.getDireccion())
                                    .append("%%%")
                                    .append(mercados.getIdestado());

                        } else {
                            result = "Mercado no Existe";
                        }

                    } else {
                        result = "ID Mercado incorrecto";
                    }
                    break;

                case "UPD":
                    mercados = new Mercados();
                    validator = new MercadosValidator();
                    list_msg = validator.valida(request, mercados, true);

                    if (list_msg.isEmpty()) {
                        result = daoMercados.mercadosUpd(mercados);

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
