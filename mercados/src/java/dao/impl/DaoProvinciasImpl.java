package dao.impl;

import dao.DaoProvincias;
import dto.Provincias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.sql.ConectaDb;

public class DaoProvinciasImpl implements DaoProvincias {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoProvinciasImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> provinciasQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("regionprovincias.idprovincia,")
                .append("departamentos.nombre,")
                .append("regionprovincias.nombre ")
                .append("FROM regionprovincias ")
                .append("INNER JOIN departamentos ")
                .append("ON regionprovincias.iddepartamento = departamentos.iddepartamento ")
                .append("ORDER BY regionprovincias.nombre ASC ")
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
                .append("SELECT COUNT(*) FROM regionprovincias");

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
    public String provinciasIns(Provincias provincias) {
        sql.delete(0, sql.length())
                .append("INSERT INTO regionprovincias (iddepartamento, nombre) VALUES(?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, provincias.getIddepartamento());
            ps.setString(2, provincias.getNombre());

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
    public String provinciasDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM regionprovincias WHERE idprovincia = ?");

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
    public Provincias provinciasGet(Integer idprovincia) {
        Provincias provincias = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idprovincia,")
                .append("iddepartamento,")
                .append("nombre ")
                .append("FROM regionprovincias ")
                .append("WHERE idprovincia = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idprovincia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    provincias = new Provincias();

                    provincias.setIdprovincia(rs.getInt(1));
                    provincias.setIddepartamento(rs.getInt(2));
                    provincias.setNombre(rs.getString(3));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return provincias;
    }

    @Override
    public String provinciasUpd(Provincias provincias) {
        sql.delete(0, sql.length())
                .append("UPDATE regionprovincias SET iddepartamento=?, nombre=? WHERE idprovincia=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, provincias.getIddepartamento());
            ps.setString(2, provincias.getNombre());
            ps.setInt(3, provincias.getIdprovincia());

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
    public List<Object[]> provinciasCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idprovincia,")
                .append("nombre ")
                .append("FROM regionprovincias");

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
