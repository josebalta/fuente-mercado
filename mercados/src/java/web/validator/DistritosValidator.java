package web.validator;

import dto.Distritos;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class DistritosValidator {

    public DistritosValidator() {
    }

    /**
     * @param request
     * @param distritos para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Distritos distritos, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer iddistrito = DeString.aInteger(request.getParameter("iddistrito"));
        Integer idprovincia = DeString.aInteger(request.getParameter("idprovincia"));
        Integer idzona = DeString.aInteger(request.getParameter("idzona"));
        String nombre = request.getParameter("nombre");

        if (upd && (iddistrito == null)) {
            list.add("ID Distrito Incorrecto");
        }

        if (idprovincia == null) {
            list.add("Seleccione Provincia");
        }
        
        if (idzona == null) {
            list.add("Seleccione Zona");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        distritos.setIddistrito(iddistrito);
        distritos.setIdprovincia(idprovincia);
        distritos.setIdzona(idzona);
        distritos.setNombre(nombre);
        
        return list;
    }

}
