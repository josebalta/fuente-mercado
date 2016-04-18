package web.validator;

import dto.Productos;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class ProductosValidator {

    public ProductosValidator() {
    }

    /**
     * @param request
     * @param productos para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Productos productos, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idproducto = DeString.aInteger(request.getParameter("idproducto"));
        String descripcion = request.getParameter("descripcion");
        Double preciounitario = DeString.aDouble(request.getParameter("preciounitario"));
        Short idestado = DeString.aShort(request.getParameter("idestado"));

        if (upd && (idproducto == null)) {
            list.add("ID Producto Incorrecto");
        }

        if ((descripcion == null) || (descripcion.trim().length() == 0)) {
            list.add("Ingrese descripción");
        }
        
        if ((preciounitario == 0) || (preciounitario == null)) {
            list.add("Ingrese precio unitario");
        }
        
        if (idestado == null) {
            list.add("Seleccione estado");
        }

        productos.setIdproducto(idproducto);
        productos.setDescripcion(descripcion);
        productos.setPreciounitario(preciounitario);
        productos.setIdestado(idestado);
        return list;
    }
    
}
