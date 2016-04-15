package dao;

import dto.Usuarios;
import java.util.List;

public interface DaoUsuarios {

    public Usuarios autentica(String usuario, String password);
    
    public List<Object[]> usuariosQry(int numpag, int filsXpag);

    public Integer[] ctasPaginas(int filsXpag);

    public String usuariosIns(Usuarios usuarios);

    public String usuariosDel(List<Integer> ids);

    public Usuarios usuariosGet(Integer idusuario);

    public String usuariosUpd(Usuarios usuarios);
    
    public String getMessage();
}

