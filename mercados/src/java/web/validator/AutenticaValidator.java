package web.validator;

import dto.Usuarios;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author parainformaticos.com
 */
public class AutenticaValidator {

    public AutenticaValidator() {
    }

    /**
     * @param request
     * @param usuarios para encapsular datos
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Usuarios usuarios) {
        List<String> list = new LinkedList<>();

        String nombre = request.getParameter("nombre");
        String clave = request.getParameter("clave");

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if ((clave == null) || (clave.trim().length() == 0)) {
            list.add("Ingrese clave");
        }

        usuarios.setNombre(nombre);
        usuarios.setClave(clave);

        return list;
    }
}
