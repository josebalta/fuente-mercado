package web.servlet;

import dao.DaoUsuarios;
import dao.impl.DaoDepartamentosImpl;
import dao.impl.DaoUsuariosImpl;
import dto.Usuarios;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.validator.AutenticaValidator;

@WebServlet(name = "AutenticaServlet", urlPatterns = {"/Autentica"})
public class AutenticaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        accion = (accion == null) ? "" : accion;
        String result = null;
        String target = null;
        List<String> message = new LinkedList<>();
        //
        DaoUsuarios daoUsuarios = new DaoUsuariosImpl();

        switch (accion) {
            case "LOGIN":
                Usuarios usuarios = new Usuarios();
                AutenticaValidator validator = new AutenticaValidator();
                message = validator.valida(request, usuarios);

                if (message.isEmpty()) {
                    usuarios = daoUsuarios.autentica(usuarios.getNombre(), usuarios.getClave());

                    if (usuarios == null) {
                        result = daoUsuarios.getMessage();
                    }
                }

                if (message.isEmpty() && (usuarios != null)) {
                    final String ID = request.getSession().getId();
                    request.getSession().setAttribute("ID", ID);
                    request.getSession().setAttribute("usuarios", usuarios);
                    request.getSession().setAttribute("autorizacion", usuarios.getIdrol());

                    if (usuarios.getIdrol() == 1) {
                        target = "view/admin/";
                        
                    } else if (usuarios.getIdrol() == 2) {
                        target = "view/client/";
                        
                    }

                } else {
                    request.setAttribute("usuarios", usuarios);
                }
                break;

            case "LOGOUT":
                request.getSession().invalidate();
                target = "view/";
                break;

            case "":
                result = "Solicitud requerida";
                break;

            default:
                result = "Solicitud no reconocida";
        }

        if (result != null) {
            message.add(result);
        }

        if (!message.isEmpty()) {
            request.setAttribute("msg", message);
        }

        if (target == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);

        } else {
            response.sendRedirect(target);
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

