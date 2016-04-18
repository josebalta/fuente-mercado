package dao;

import dto.Clientes;
import java.util.List;

/**
 *
 * @author Jose
 */
public interface DaoClientes {

    public List<Object[]> clientesQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String clientesIns(Clientes clientes);

    public String clientesDel(List<Integer> ids);

    public Clientes clientesGet(Integer idcliente);

    public String clientesUpd(Clientes clientes);

    public List<Object[]> clientesCbo();

    public String getMessage();

}
