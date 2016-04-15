/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoZonas;
import dto.Zonas;
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
public class DaoZonasImpl implements DaoZonas {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoZonasImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    /**
     *
     * @param numpag
     * @param ctasfils
     * @return
     */
    @Override
    public List<Object[]> zonasQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idzona,")
                .append("nombre ")
                .append("FROM zonas ")
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

    /**
     *
     * @param filsXpag
     * @return
     */
    @Override
    public Integer[] ctasPaginas(int filsXpag) {
        Integer[] result = null;
        sql.delete(0, sql.length())
                .append("SELECT COUNT(*) FROM zonas");

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

    /**
     *
     * @param zonas
     * @return
     */
    @Override
    public String zonasIns(Zonas zonas) {
        sql.delete(0, sql.length())
                .append("INSERT INTO zonas (nombre) VALUES(?) ");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, zonas.getNombre());

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                throw new SQLException("0 filas afectadas");
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    /**
     *
     * @param ids
     * @return
     */
    @Override
    public String zonasDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM zonas WHERE idzona = ?");

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

    /**
     *
     * @param idzona
     * @return
     */
    @Override
    public Zonas zonasGet(Integer idzona) {
        Zonas zonas = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idzona,")
                .append("nombre ")
                .append("FROM zonas ")
                .append("WHERE idzona= ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idzona);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    zonas = new Zonas();

                    zonas.setIdzona(rs.getInt(1));
                    zonas.setNombre(rs.getString(2));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return zonas;
    }

    /**
     *
     * @param zonas
     * @return
     */
    @Override
    public String zonasUpd(Zonas zonas) {
        sql.delete(0, sql.length())
                .append("UPDATE zonas SET nombre=? WHERE idzona=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, zonas.getNombre());
            ps.setInt(2, zonas.getIdzona());

            int ctos = ps.executeUpdate();
            if (ctos == 0) {
                throw new SQLException("0 filas afectadas");
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return message;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Object[]> zonasCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idzona,")
                .append("nombre ")
                .append("FROM zonas");

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
