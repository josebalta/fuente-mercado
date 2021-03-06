package web.validator;

import dto.Clientes;
import java.text.ParseException;
import java.sql.Date;
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
     * @throws java.text.ParseException
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
        Integer celular = DeString.aInteger(request.getParameter("telefono"));
        Date fechaingreso = DeString.aDate(request.getParameter("fechaingreso"));
        Date fechacese = DeString.aDate(request.getParameter("fechacese"));
        Short idestado = DeString.aShort(request.getParameter("idestado"));

        if (upd && (idcliente == null)) {
            list.add("ID Cliente Incorrecto");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if (idvendedor == null) {
            list.add("Seleccione Vendedor");
        }

        if (idmercado == null) {
            list.add("Seleccione Mercado");
        }

        if (celular == null || celular.equals("[0-9]*") || request.getParameter("telefono").length() != 9) {
            list.add("Ingrese n&uacute;mero de celular v&aacute;lido");
        }

        if (idestado == null) {
            list.add("Seleccione Estado");
        }

        clientes.setIdcliente(idcliente);
        clientes.setIdvendedor(idvendedor);
        clientes.setNombre(nombre);
        clientes.setIdmercado(idmercado);
        clientes.setDireccion(direccion);
        clientes.setTelefono(celular);
        clientes.setFechaingreso(fechaingreso);
        clientes.setFechacese(fechacese);
        clientes.setIdestado(idestado);

        return list;
    }
}