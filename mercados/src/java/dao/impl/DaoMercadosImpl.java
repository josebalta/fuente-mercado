/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dto.Mercados;
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
public class DaoMercadosImpl implements dao.DaoMercados {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoMercadosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> mercadosQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("mercados.idmercado, ")
                .append("mercados.abreviado, ")
                .append("mercados.nombre, ")
                .append("mercados.direccion, ")
                .append("distritos.nombre, ")
                .append("estados.descripcion ")
                .append("FROM mercados ")
                .append("INNER JOIN distritos ")
                .append("ON distritos.iddistrito = mercados.iddistrito ")
                .append("INNER JOIN estados ")
                .append("ON mercados.idestado = estados.idestado ")
                .append("ORDER BY mercados.nombre ASC ")
                .append("LIMIT ? OFFSET ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, ctasfils);    // cantidad de filas
            ps.setInt(2, (numpag * ctasfils)); // desde (numpag == 0 para la primera

            try (ResultSet rs = ps.executeQuery()) {

                list = new ArrayList<>();

                while (rs.next()) { // filas de consulta
                    Object[] u = new Object[6];

                    u[0] = rs.getInt(1);
                    u[1] = rs.getString(2);
                    u[2] = rs.getString(3);
                    u[3] = rs.getString(4);
                    u[4] = rs.getString(5);
                    u[5] = rs.getString(6);

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
                .append("SELECT COUNT(*) FROM mercados");

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
    public String mercadosIns(Mercados mercados) {
        sql.delete(0, sql.length())
                .append("INSERT INTO mercados (iddistrito, abreviado, nombre, direccion, idestado) VALUES(?,?,?,?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, mercados.getIddistrito());
            ps.setString(2, mercados.getAbreviado());
            ps.setString(3, mercados.getNombre());
            ps.setString(4, mercados.getDireccion());
            ps.setShort(5, mercados.getIdestado());

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
    public String mercadosDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM mercados WHERE idmercado = ?");

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
    public Mercados mercadosGet(Integer idmercado) {
        Mercados mercados = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idmercado, ")
                .append("iddistrito, ")
                .append("abreviado, ")
                .append("nombre, ")
                .append("direccion, ")
                .append("estado ")
                .append("FROM mercados ")
                .append("WHERE idmercado=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idmercado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mercados = new Mercados();

                    mercados.setIdmercado(rs.getInt(1));
                    mercados.setIddistrito(rs.getInt(2));
                    mercados.setAbreviado(rs.getString(3));
                    mercados.setNombre(rs.getString(4));
                    mercados.setDireccion(rs.getString(5));
                    mercados.setIdestado(rs.getShort(6));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return mercados;
    }

    @Override
    public String mercadosUpd(Mercados mercados) {
        sql.delete(0, sql.length())
                .append("UPDATE mercados SET iddistrito=?, abreviado=?, nombre=?, direccion=?, estado=? WHERE idmercado=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, mercados.getIddistrito());
            ps.setString(2, mercados.getAbreviado());
            ps.setString(3, mercados.getNombre());
            ps.setString(4, mercados.getDireccion());
            ps.setShort(5, mercados.getIdestado());
            ps.setInt(6, mercados.getIdmercado());

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
    public List<Object[]> mercadosCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idmercado,")
                .append("nombre ")
                .append("FROM mercados");

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
