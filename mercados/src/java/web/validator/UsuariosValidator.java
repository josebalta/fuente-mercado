package web.validator;

import dto.Usuarios;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 * @author parainformaticos.com
 */
public class UsuariosValidator {

    public UsuariosValidator() {
    }

    /**
     * @param request
     * @param usuarios para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Usuarios usuarios, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idusuario = DeString.aInteger(request.getParameter("idusuario"));
        Integer idrol = DeString.aInteger(request.getParameter("idrol"));
        String nombre = request.getParameter("nombre");
        String clave = request.getParameter("clave");

        if (upd && (idusuario == null)) {
            list.add("ID Usuario Incorrecto");
        }

        if (idrol == null) {
            list.add("Seleccione Rol");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if (!upd && ((clave == null) || (clave.trim().length() == 0))) {
            list.add("Ingrese clave");
        }

        usuarios.setIdusuario(idusuario);
        usuarios.setIdrol(idrol);
        usuarios.setNombre(nombre);
        usuarios.setClave(clave);

        return list;
    }
}
