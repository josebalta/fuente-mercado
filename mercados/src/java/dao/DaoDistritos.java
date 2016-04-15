/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Distritos;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoDistritos {
    
    public List<Object[]> distritosQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String distritosIns(Distritos distritos);

    public String distritosDel(List<Integer> ids);

    public Distritos distritosGet(Integer iddistrito);

    public String distritosUpd(Distritos distritos);
    
    public List<Object[]> distritosCbo();
    
    public String getMessage();
    
}
