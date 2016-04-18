/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.validator;

import dto.Vendedores;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class VendedoresValidator {

    public VendedoresValidator() {
    }

    /**
     * @param request
     * @param vendedores para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Vendedores vendedores, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idvendedor = DeString.aInteger(request.getParameter("idvendedor"));
        String nombre = request.getParameter("nombre");
        Short idestado = DeString.aShort(request.getParameter("idestado"));

        if (upd && (idvendedor == null)) {
            list.add("ID Vendedor Incorrecto");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if (idestado == null) {
            list.add("Seleccione estado");
        }

        vendedores.setIdvendedor(idvendedor);
        vendedores.setNombre(nombre);
        vendedores.setIdestado(idestado);
        return list;
    }

}
