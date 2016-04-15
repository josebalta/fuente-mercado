package web.validator;

import dto.Provincias;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 * @author parainformaticos.com
 */
public class ProvinciasValidator {

    public ProvinciasValidator() {
    }

    /**
     * @param request
     * @param provincias para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Provincias provincias, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idprovincia = DeString.aInteger(request.getParameter("idprovincia"));
        Integer iddepartamento = DeString.aInteger(request.getParameter("iddepartamento"));
        String nombre = request.getParameter("nombre");

        if (upd && (idprovincia == null)) {
            list.add("ID Provincia Incorrecto");
        }

        if (iddepartamento == null) {
            list.add("Seleccione Departamento");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        provincias.setIdprovincia(idprovincia);
        provincias.setIddepartamento(iddepartamento);
        provincias.setNombre(nombre);
        return list;
    }
}
