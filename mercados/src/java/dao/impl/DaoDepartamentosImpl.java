package dao.impl;

import dao.DaoDepartamentos;
import dto.Departamentos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.sql.ConectaDb;

public class DaoDepartamentosImpl implements DaoDepartamentos {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoDepartamentosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> departamentosQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("iddepartamento,")
                .append("nombre ")
                .append("FROM departamentos ")
                .append("ORDER BY nombre ASC ")
                .append("LIMIT ? OFFSET ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, ctasfils);    // cantidad de filas
            ps.setInt(2, (numpag * ctasfils)); // desde (numpag == 0 para la primera

            try (ResultSet rs = ps.executeQuery()) {

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

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return list;
    }

    @Override
    public Integer[] ctasPaginas(int filsXpag) {
        Integer[] result = null;
        sql.delete(0, sql.length())
                .append("SELECT COUNT(*) FROM departamentos");

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
    public String departamentosIns(Departamentos departamentos) {
        sql.delete(0, sql.length())
                .append("INSERT INTO departamentos (nombre) VALUES(?) ");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, departamentos.getNombre());

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
    public String departamentosDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM departamentos WHERE iddepartamento IN(?)");

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
    public Departamentos departamentosGet(Integer iddepartamento) {
        Departamentos departamentos = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("iddepartamento,")
                .append("nombre ")
                .append("FROM departamentos ")
                .append("WHERE iddepartamento= ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, iddepartamento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    departamentos = new Departamentos();

                    departamentos.setIddepartamento(rs.getInt(1));
                    departamentos.setNombre(rs.getString(2));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return departamentos;
    }

    @Override
    public String departamentosUpd(Departamentos departamentos) {
        sql.delete(0, sql.length())
                .append("UPDATE departamentos SET nombre=? WHERE iddepartamento=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, departamentos.getNombre());
            ps.setInt(2, departamentos.getIddepartamento());

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
    public List<Object[]> departamentosCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("iddepartamento,")
                .append("nombre ")
                .append("FROM departamentos");

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
