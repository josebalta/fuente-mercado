/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dto.Distritos;
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
public class DaoDistritosImpl implements dao.DaoDistritos {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoDistritosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> distritosQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("distritos.iddistrito, ")
                .append("regionprovincias.nombre, ")
                .append("zonas.nombre, ")
                .append("distritos.nombre ")
                .append("FROM distritos ")
                .append("INNER JOIN regionprovincias ")
                .append("ON regionprovincias.idprovincia = distritos.idprovincia ")
                .append("INNER JOIN zonas ")
                .append("ON zonas.idzona = distritos.idzona ")
                .append("ORDER BY distritos.nombre ASC ")
                .append("LIMIT ? OFFSET ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, ctasfils);    // cantidad de filas
            ps.setInt(2, (numpag * ctasfils)); // desde (numpag == 0 para la primera

            try (ResultSet rs = ps.executeQuery()) {

                list = new ArrayList<>();

                while (rs.next()) { // filas de consulta
                    Object[] u = new Object[4];

                    u[0] = rs.getInt(1);
                    u[1] = rs.getString(2);
                    u[2] = rs.getString(3);
                    u[3] = rs.getString(4);

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
                .append("SELECT COUNT(*) FROM distritos");

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
    public String distritosIns(Distritos distritos) {
        sql.delete(0, sql.length())
                .append("INSERT INTO distritos (idprovincia, idzona, nombre) VALUES(?,?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, distritos.getIdprovincia());
            ps.setInt(2, distritos.getIdzona());
            ps.setString(3, distritos.getNombre());

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
    public String distritosDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM distritos WHERE iddistrito = ?");

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
    public Distritos distritosGet(Integer iddistrito) {
        Distritos distritos = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("iddistrito,")
                .append("idprovincia,")
                .append("idzona,")
                .append("nombre ")
                .append("FROM distritos ")
                .append("WHERE iddistrito = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, iddistrito);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    distritos = new Distritos();

                    distritos.getIdprovincia(rs.getInt(1));
                    distritos.getIdzona(rs.getInt(2));
                    distritos.getNombre(rs.getString(3));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return distritos;
    }

    @Override
    public String distritosUpd(Distritos distritos) {
        sql.delete(0, sql.length())
                .append("UPDATE distritos SET idprovincia=?, idzona=?, nombre=? WHERE iddistrito=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, distritos.getIdprovincia());
            ps.setInt(2, distritos.getIdzona());
            ps.setString(3, distritos.getNombre());
            ps.setInt(4, distritos.getIddistrito());

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
    public List<Object[]> distritosCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("iddistrito,")
                .append("nombre ")
                .append("FROM distritos");

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
