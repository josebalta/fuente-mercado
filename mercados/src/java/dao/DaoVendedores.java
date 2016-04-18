/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Vendedores;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoVendedores {

    public List<Object[]> vendedoresQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String vendedoresIns(Vendedores vendedores);

    public String vendedoresDel(List<Integer> ids);

    public Vendedores vendedoresGet(Integer idvendedor);

    public String vendedoresUpd(Vendedores vendedores);

    public List<Object[]> vendedoresCbo();

    public String getMessage();

}
