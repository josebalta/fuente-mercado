package web.servlet;

import dao.DaoRoles;
import dao.DaoUsuarios;
import dao.impl.DaoRolesImpl;
import dao.impl.DaoUsuariosImpl;
import dto.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import parainfo.convert.DeString;
import web.validator.UsuariosValidator;

@WebServlet(name = "UsuariosServlet", urlPatterns = {"/view/admin/Usuarios"})
public class UsuariosServlet extends HttpServlet {

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
            DaoUsuarios daoUsuarios = new DaoUsuariosImpl();
            DaoRoles daoRoles = new DaoRolesImpl();
            //
            final Integer filsXpag = 10;

            switch (accion) {
                case "ROLES_CBO":
                    List<Object[]> list = daoRoles.rolesCbo();

                    if (list != null) {
                        for (Object[] reg : list) {
                            message.append("<option value=\"").append(reg[0]).append("\">");
                            message.append(reg[1]);
                            message.append("</option>");
                        }
                    } else {
                        result = "!Ok.No se pudo realizar Consulta Roles";
                    }
                    break;

                case "QRY":
                    Integer numpag = DeString.aInteger(request.getParameter("numpag"));
                    list = daoUsuarios.usuariosQry(numpag, filsXpag);

                    if (list != null) {
                        for (Object[] u : list) {
                            message.append("<tr>");
                            message.append("<td>").append(u[2]).append("</td>");
                            message.append("<td colspan=\"2\">").append(u[1]).append("</td>");
                            message.append("<td>").append("<input type=\"checkbox\" name=\"idusuario_del\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("<td>").append("<input type=\"radio\" name=\"idusuario_upd\" value=\"").append(u[0]).append("\"/>").append("</td>");
                            message.append("</tr>");
                        }
                    } else {
                        result = "!Ok.No se pudo realizar Consulta Usuarios";
                    }
                    break;

                case "PAGS":
                    Integer[] ctasPagsFils = daoUsuarios.ctasPaginas(filsXpag);

                    if (ctasPagsFils != null) {
                        for (int i = 0; i < ctasPagsFils[0]; i++) {
                            message.append("<option value=\"").append(i).append("\">");
                            message.append(1 + i);
                            message.append("</option>");
                        }
                    } else {
                        result = "!Ok.No se pudo realizar Consulta Roles";
                    }
                    break;

                case "INS":
                    Usuarios usuarios = new Usuarios();
                    UsuariosValidator validator = new UsuariosValidator();
                    List<String> list_msg = validator.valida(request, usuarios, false);

                    if (list_msg.isEmpty()) {
                        result = daoUsuarios.usuariosIns(usuarios);

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
                        result = daoUsuarios.usuariosDel(ids);
                    }
                    break;

                case "GET":
                    Integer idusuario = DeString.aInteger(request.getParameter("idusuario"));

                    if (idusuario != null) {
                        usuarios = daoUsuarios.usuariosGet(idusuario);

                        if (usuarios != null) {
                            result = usuarios.getIdrol() + "%%%" + usuarios.getNombre();

                        } else {
                            result = "!Ok.Usuario no Existe";
                        }

                    } else {
                        result = "!Ok.ID Usuario incorrecto";
                    }
                    break;

                case "UPD":
                    usuarios = new Usuarios();
                    validator = new UsuariosValidator();
                    list_msg = validator.valida(request, usuarios, true);

                    if (list_msg.isEmpty()) {
                        result = daoUsuarios.usuariosUpd(usuarios);

                    } else {
                        message.append("!Ok.<ol>");
                        for (String msg : list_msg) {
                            message.append("<li>").append(msg).append("</li>");
                        }
                        message.append("</ol>");
                    }
                    break;

                case "":
                    result = "!Ok.Solicitud requerida";
                    break;

                default:
                    result = "!Ok.Solicitud no reconocida";
            }

            if (result != null) {
                message.append(result);
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
