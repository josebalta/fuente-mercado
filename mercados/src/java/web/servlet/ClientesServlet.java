/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.servlet;

import dao.DaoClientes;
import dao.DaoEstados;
import dao.DaoVendedores;
import dao.DaoMercados;
import dao.impl.DaoClientesImpl;
import dao.impl.DaoEstadosImpl;
import dao.impl.DaoVendedoresImpl;
import dao.impl.DaoMercadosImpl;
import dto.Clientes;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeDate;
import parainfo.convert.DeString;
import web.validator.ClientesValidator;

/**
 *
 * @author Jose
 */
@WebServlet(name = "ClientesServlet", urlPatterns = {"/view/admin/Clientes"})
public class ClientesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

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
            DaoClientes daoClientes = new DaoClientesImpl();
            DaoVendedores daoVendedores = new DaoVendedoresImpl();
            DaoMercados daoMercados = new DaoMercadosImpl();
            DaoEstados daoEstados = new DaoEstadosImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "VENDEDORES_CBO":
                    List<Object[]> list = daoVendedores.vendedoresCbo();

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

                case "MERCADOS_CBO":
                    list = daoMercados.mercadosCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Mercados";
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
                        result = "No se pudo realizar Consulta Mercados";
                    }
                    break;

                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    list = daoClientes.clientesQry(numpag, filsXpag);

                    if (list != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td>").append(u[1]).append("</td>");
                            message.append("<td>").append(u[3]).append("</td>");
                            message.append("<td>").append(u[4]).append("</td>");
                            message.append("<td>").append(u[5]).append("</td>");
                            message.append("<td>").append(DeDate.formato(u[6])).append("</td>");
                            message.append("<td>").append(DeDate.formato(u[7])).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[8]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idcliente_del\" value=\" ").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idcliente_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "No se pudo realizar Consulta Clientes";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoClientes.ctasPaginas(filsXpag);

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
                    Clientes clientes = new Clientes();
                    ClientesValidator validator = new ClientesValidator();
                    List<String> list_msg = validator.valida(request, clientes, false);

                    if (list_msg.isEmpty()) {
                        result = daoClientes.clientesIns(clientes);

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
                        result = daoClientes.clientesDel(ids);
                    }
                    break;

                case "GET":
                    Integer idcliente = DeString.aInteger(request.getParameter("idcliente"));

                    if (idcliente != null) {
                        clientes = daoClientes.clientesGet(idcliente);

                        if (clientes != null) {
                            message.append(clientes.getIdvendedor())
                                    .append("%%%")
                                    .append(clientes.getNombre())
                                    .append("%%%")
                                    .append(clientes.getIdmercado())
                                    .append("%%%")
                                    .append(clientes.getDireccion())
                                    .append("%%%")
                                    .append(clientes.getTelefono())
                                    .append("%%%")
                                    .append(clientes.getFechaingreso())
                                    .append("%%%")
                                    .append(clientes.getFechacese())
                                    .append("%%%")
                                    .append(clientes.getIdestado());

                        } else {
                            result = "Cliente no Existe";
                        }

                    } else {
                        result = "ID Cliente incorrecto";
                    }
                    break;

                case "UPD":
                    clientes = new Clientes();
                    validator = new ClientesValidator();
                    list_msg = validator.valida(request, clientes, true);

                    if (list_msg.isEmpty()) {
                        result = daoClientes.clientesUpd(clientes);

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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
