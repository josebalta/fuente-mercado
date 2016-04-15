
package dao;

import dto.Zonas;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoZonas {

    public List<Object[]> zonasQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String zonasIns(Zonas zonas);

    public String zonasDel(List<Integer> ids);

    public Zonas zonasGet(Integer idzona);

    public String zonasUpd(Zonas zonas);

    public List<Object[]> zonasCbo();

    public String getMessage();

}
