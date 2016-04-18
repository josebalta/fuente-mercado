package web.validator;

import dto.Clientes;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class ClientesValidator {

    public ClientesValidator() {
    }

    /**
     * @param request
     * @param clientes para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Clientes clientes, boolean upd) throws ParseException {
        List<String> list = new LinkedList<>();
//
        Integer idcliente = DeString.aInteger(request.getParameter("idcliente"));
        Integer idvendedor = DeString.aInteger(request.getParameter("idvendedor"));
        String nombre = request.getParameter("nombre");
        Integer idmercado = DeString.aInteger(request.getParameter("idmercado"));
        String direccion = request.getParameter("direccion");
        Integer telefono = DeString.aInteger(request.getParameter("telefono"));
        Date fechaingreso = DeString.aDate(request.getParameter("fechaingreso"));
        Date fechacese = DeString.aDate(request.getParameter("fechacese"));
        Short idestado = DeString.aShort(request.getParameter("idestado"));

        if (upd && (idcliente == null)) {
            list.add("ID Cliente Incorrecto");
        }

        if (idvendedor == null) {
            list.add("Seleccione Provincia");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if (idvendedor == null) {
            list.add("Seleccione Provincia");
        }

        if (idestado == null) {
            list.add("Seleccione Zona");
        }

        clientes.setIdcliente(idcliente);
        clientes.setIdvendedor(idvendedor);
        clientes.setNombre(nombre);
        clientes.setIdmercado(idmercado);
        clientes.setDireccion(direccion);
        clientes.setTelefono(telefono);
        clientes.setFechaingreso(fechaingreso);
        clientes.setFechacese(fechacese);
        clientes.setIdestado(idestado);

        return list;
    }
}
