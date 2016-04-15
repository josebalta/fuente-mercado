/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.validator;

import dto.Mercados;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import parainfo.convert.DeString;

/**
 *
 * @author Jose
 */
public class MercadosValidator {

    public MercadosValidator() {
    }

    /**
     * @param request
     * @param mercados para encapsular datos
     * @param upd si viene true es un update
     * @return lista de ingresos incorrectos
     */
    public List<String> valida(
            HttpServletRequest request, Mercados mercados, boolean upd) {
        List<String> list = new LinkedList<>();

        Integer idmercado = DeString.aInteger(request.getParameter("idmercado"));
        Integer iddistrito = DeString.aInteger(request.getParameter("iddistrito"));
        String abreviatura = request.getParameter("abreviatura");
        String nombre = request.getParameter("nombre");
        Short idestado = DeString.aShort(request.getParameter("idestado"));

        if (upd && (idmercado == null)) {
            list.add("ID Mercado Incorrecto");
        }

        if (iddistrito == null) {
            list.add("Seleccione Distrito");
        }

        if ((abreviatura == null) || (abreviatura.trim().length() == 0)) {
            list.add("Ingrese abreviatura");
        }

        if ((nombre == null) || (nombre.trim().length() == 0)) {
            list.add("Ingrese nombre");
        }

        if (idestado == null) {
            list.add("Seleccione Estado");
        }
        mercados.setIdmercado(idmercado);
        mercados.setIddistrito(iddistrito);
        mercados.setAbreviado(abreviatura);
        mercados.setNombre(nombre);
        mercados.setIdestado(idestado);
        return list;
    }
}
