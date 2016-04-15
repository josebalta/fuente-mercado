package web.validator;

import dto.Departamentos;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 * @author parainformaticos.com
 */
public class DepartamentosValidator {

    public DepartamentosValidator() {
    }

    /**
     * @param request
     * @param departamentos para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Departamentos departamentos, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer iddepartamento = DeString.aInteger(request.getParameter("iddepartamento"));
        String nombre = request.getParameter("nombre");

        if (upd && (iddepartamento == null)) {
            list.add("ID Departamento Incorrecto");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        departamentos.setIddepartamento(iddepartamento);
        departamentos.setNombre(nombre);
        return list;
    }
}
