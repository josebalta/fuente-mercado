package dao;

import dto.Departamentos;
import java.util.List;

public interface DaoDepartamentos {
    
    public List<Object[]> departamentosQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String departamentosIns(Departamentos departamentos);

    public String departamentosDel(List<Integer> ids);

    public Departamentos departamentosGet(Integer iddepartamento);

    public String departamentosUpd(Departamentos departamentos);
    
    public List<Object[]> departamentosCbo();
    
    public String getMessage();
}

