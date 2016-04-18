/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.servlet;

import dao.DaoEstados;
import dao.DaoProductos;
import dao.impl.DaoEstadosImpl;
import dao.impl.DaoProductosImpl;
import dto.Productos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.ProductosValidator;

/**
 *
 * @author Jose
 */
@WebServlet(name = "ProductosServlet", urlPatterns = {"/view/admin/Productos"})
public class ProductosServlet extends HttpServlet {

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
            DaoProductos daoProductos = new DaoProductosImpl();
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
                    list = daoProductos.productosQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[1]).append("</td>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[3]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idproducto_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idproducto_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Productos";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoProductos.ctasPaginas(filsXpag);

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
                    Productos productos = new Productos();
                    ProductosValidator validator = new ProductosValidator();
                    List<String> list_msg = validator.valida(request, productos, false);

                    if (list_msg.isEmpty()) {
                        result = daoProductos.productosIns(productos);

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
                        result = daoProductos.productosDel(ids);
                    }
                    break;

                case "GET":
                    Integer idproducto = DeString.aInteger(request.getParameter("idproducto"));

                    if (idproducto != null) {
                        productos = daoProductos.productosGet(idproducto);

                        if (productos != null) {
                            message.append(productos.getDescripcion())
                                    .append("%%%")
                                    .append(productos.getPreciounitario())
                                    .append("%%%")
                                    .append(productos.getIdestado());

                        } else {
                            result = "Producto no Existe";
                        }

                    } else {
                        result = "ID Producto incorrecto";
                    }
                    break;

                case "UPD":
                    productos = new Productos();
                    validator = new ProductosValidator();
                    list_msg = validator.valida(request, productos, true);

                    if (list_msg.isEmpty()) {
                        result = daoProductos.productosUpd(productos);

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
