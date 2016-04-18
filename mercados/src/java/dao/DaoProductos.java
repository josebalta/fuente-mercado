/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Productos;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoProductos {
    
    public List<Object[]> productosQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String productosIns(Productos productos);

    public String productosDel(List<Integer> ids);

    public Productos productosGet(Integer idproducto);

    public String productosUpd(Productos productos);
    
    public List<Object[]> productosCbo();
    
    public String getMessage();
    
}
