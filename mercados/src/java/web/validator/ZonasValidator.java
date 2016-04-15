package web.validator;

import dto.Zonas;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class ZonasValidator {

    public ZonasValidator() {
    }

    /**
     * @param request
     * @param zonas para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Zonas zonas, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idzona = DeString.aInteger(request.getParameter("idzona"));
        String nombre = request.getParameter("nombre");

        if (upd && (idzona == null)) {
            list.add("ID Zona Incorrecta");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        zonas.setIdzona(idzona);
        zonas.setNombre(nombre);
        return list;
    }

}
