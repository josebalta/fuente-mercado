package dao;

import dto.Provincias;
import java.util.List;

public interface DaoProvincias {
    
    public List<Object[]> provinciasQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String provinciasIns(Provincias provincias);

    public String provinciasDel(List<Integer> ids);

    public Provincias provinciasGet(Integer idprovincia);

    public String provinciasUpd(Provincias provincias);
    
    public List<Object[]> provinciasCbo();
    
    public String getMessage();
}

