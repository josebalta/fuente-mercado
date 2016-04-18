package dao.impl;

import dto.Vendedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.sql.ConectaDb;

/**
 *
 * @author Jose
 */
public class DaoVendedoresImpl implements dao.DaoVendedores {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoVendedoresImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> vendedoresQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("vendedores.idvendedor, ")
                .append("vendedores.nombre, ")
                .append("estados.descripcion ")
                .append("FROM vendedores ")
                .append("INNER JOIN estados ")
                .append("ON vendedores.idestado = estados.idestado ")
                .append("ORDER BY vendedores.nombre ASC ")
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
                .append("SELECT COUNT(*) FROM vendedores");

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
    public String vendedoresIns(Vendedores vendedores) {
        sql.delete(0, sql.length())
                .append("INSERT INTO vendedores (nombre, idestado) VALUES(?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, vendedores.getNombre());
            ps.setShort(2, vendedores.getIdestado());

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
    public String vendedoresDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM vendedores WHERE idvendedor = ?");

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
    public Vendedores vendedoresGet(Integer idvendedor) {
        Vendedores vendedores = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idvendedor, ")
                .append("nombre, ")
                .append("idestado ")
                .append("FROM vendedores ")
                .append("WHERE idvendedor= ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idvendedor);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vendedores = new Vendedores();

                    vendedores.setIdvendedor(rs.getInt(1));
                    vendedores.setNombre(rs.getString(2));
                    vendedores.setIdestado(rs.getShort(3));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return vendedores;
    }

    @Override
    public String vendedoresUpd(Vendedores vendedores) {
        sql.delete(0, sql.length())
                .append("UPDATE vendedores SET nombre=?, idestado=? WHERE idvendedor=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, vendedores.getNombre());
            ps.setShort(2, vendedores.getIdestado());
            ps.setInt(3, vendedores.getIdvendedor());

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

    @Override
    public List<Object[]> vendedoresCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idvendedor,")
                .append("nombre ")
                .append("FROM vendedores");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {
            list = new ArrayList<>();

            while (rs.next()) { // filas de consulta
                Object[] u = new Object[2];

                u[0] = rs.getInt(1);
                u[1] = rs.getString(2);

                list.add(u);
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return list;
    }
}
