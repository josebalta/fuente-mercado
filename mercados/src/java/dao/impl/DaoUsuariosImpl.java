package dao.impl;

import dao.DaoUsuarios;
import dto.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.aes.AdvEncSta;
import parainfo.sql.ConectaDb;

public class DaoUsuariosImpl implements DaoUsuarios {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoUsuariosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public Usuarios autentica(String nombre, String clave) {
        Usuarios usuarios = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idusuario,")
                .append("idrol,")
                .append("nombre,")
                .append("clave ")
                .append("FROM usuarios ")
                .append("WHERE (nombre = ?) ")
                .append("AND (clave = ?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps
                = cn.prepareStatement(sql.toString())) {

            AdvEncSta aes = new AdvEncSta();
            String pass = aes.encrypt(clave);

            ps.setString(1, nombre);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuarios = new Usuarios();

                    usuarios.setIdusuario(rs.getInt(1));
                    usuarios.setIdrol(rs.getInt(2));
                    usuarios.setNombre(rs.getString(3));
                    //usuarios.setClave(rs.getString(4));

                } else {
                    throw new SQLException("Usuario NO registrado");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return usuarios;
    }

    @Override
    public List<Object[]> usuariosQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("usuarios.idusuario,")
                .append("roles.descripcion,")
                .append("usuarios.nombre ")
                .append("FROM usuarios ")
                .append("INNER JOIN roles ")
                .append("ON usuarios.idrol = roles.idrol ")
                .append("ORDER BY usuarios.nombre ASC ")
                .append("LIMIT ? OFFSET ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, ctasfils);    // cantidad de filas
            ps.setInt(2, (numpag * ctasfils)); // desde (numpag == 0 para la primera

            try (ResultSet rs = ps.executeQuery()) {

                list = new ArrayList<>();

                while (rs.next()) { // filas de consulta
                    Object[] u = new Object[3];

                    u[0] = rs.getInt(1);
                    u[1] = rs.getString(2);
                    u[2] = rs.getString(3);

                    list.add(u);
                }
            } catch (SQLException e) {
                message = e.getMessage();
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return list;
    }

    @Override
    public Integer[] ctasPaginas(int filsXpag) {
        Integer[] result = null;
        sql.delete(0, sql.length())
                .append("SELECT COUNT(*) FROM usuarios");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                // total filas de consulta
                Integer totalfils = rs.getInt(1);

                // cantidad de paginas con filsXpag
                Integer ctasPags = (totalfils % filsXpag) == 0
                        ? (totalfils / filsXpag)
                        : (totalfils / filsXpag + 1);

                result = new Integer[2];
                result[0] = ctasPags;
                result[1] = totalfils;
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }
        return result;
    }

    @Override
    public String usuariosIns(Usuarios usuarios) {
        sql.delete(0, sql.length())
                .append("INSERT INTO usuarios(idrol, nombre, clave) VALUES(?,?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            AdvEncSta aes = new AdvEncSta();
            String pass = aes.encrypt(usuarios.getClave());

            ps.setInt(1, usuarios.getIdrol());
            ps.setString(2, usuarios.getNombre());
            ps.setString(3, pass);

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                throw new SQLException("0 filas afectadas");
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    @Override
    public String usuariosDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM usuarios WHERE idusuario = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            cn.setAutoCommit(false); // desactiva autoCommit
            boolean ok = true;

            for (Integer x : ids) {
                ps.setInt(1, x);

                int ctos = ps.executeUpdate();
                if (ctos == 0) {
                    ok = false;
                    message = "ID recibido no existe";
                    break;
                }
            }

            if (ok) {
                cn.commit();
            } else {
                cn.rollback();
            }

            cn.setAutoCommit(true); // activa autoCommit

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    @Override
    public Usuarios usuariosGet(Integer idusuario) {
        Usuarios usuarios = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idusuario,")
                .append("idrol,")
                .append("nombre ")
                .append("FROM usuarios ")
                .append("WHERE idusuario = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idusuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuarios = new Usuarios();
                    
                    usuarios.setIdusuario(rs.getInt(1));
                    usuarios.setIdrol(rs.getInt(2));
                    usuarios.setNombre(rs.getString(3));
                    
                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return usuarios;
    }

    @Override
    public String usuariosUpd(Usuarios usuarios) {
        sql.delete(0, sql.length())
                .append("UPDATE usuarios SET idrol=?, nombre=? WHERE idusuario=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, usuarios.getIdrol());
            ps.setString(2, usuarios.getNombre());
            ps.setInt(3, usuarios.getIdusuario());

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                throw new SQLException("0 filas afectadas");
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
