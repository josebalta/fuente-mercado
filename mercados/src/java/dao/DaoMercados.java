/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Mercados;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoMercados {

    public List<Object[]> mercadosQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String mercadosIns(Mercados mercados);

    public String mercadosDel(List<Integer> ids);

    public Mercados mercadosGet(Integer idmercado);

    public String mercadosUpd(Mercados mercados);

    public List<Object[]> mercadosCbo();

    public String getMessage();

}
