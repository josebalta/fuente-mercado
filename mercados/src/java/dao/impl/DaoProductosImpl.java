/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dto.Productos;
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
public class DaoProductosImpl implements dao.DaoProductos {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoProductosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> productosQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("productos.idproducto, ")
                .append("productos.descripcion, ")
                .append("productos.preciounitario, ")
                .append("estados.descripcion ")
                .append("FROM productos ")
                .append("INNER JOIN estados ")
                .append("ON productos.idestado = estados.idestado ")
                .append("ORDER BY productos.descripcion ASC ")
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
                    u[2] = rs.getDouble(3);
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
                .append("SELECT COUNT(*) FROM productos");

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
    public String productosIns(Productos productos) {
        sql.delete(0, sql.length())
                .append("INSERT INTO productos (descripcion, preciounitario, idestado) VALUES(?,?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, productos.getDescripcion());
            ps.setDouble(2, productos.getPreciounitario());
            ps.setShort(3, productos.getIdestado());

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
    public String productosDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM productos WHERE idproducto = ?");

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
    public Productos productosGet(Integer idproducto) {
        Productos productos = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idproducto, ")
                .append("descripcion, ")
                .append("preciounitario, ")
                .append("idestado ")
                .append("FROM productos ")
                .append("WHERE idproducto= ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idproducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    productos = new Productos();

                    productos.setIdproducto(rs.getInt(1));
                    productos.setDescripcion(rs.getString(2));
                    productos.setPreciounitario(rs.getDouble(3));
                    productos.setIdestado(rs.getShort(4));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return productos;
    }

    @Override
    public String productosUpd(Productos productos) {
        sql.delete(0, sql.length())
                .append("UPDATE productos SET descripcion=?, preciounitario=?, idestado=? WHERE idproducto=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setString(1, productos.getDescripcion());
            ps.setDouble(2, productos.getPreciounitario());
            ps.setShort(3, productos.getIdestado());
            ps.setInt(4, productos.getIdproducto());

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
    public List<Object[]> productosCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idproducto,")
                .append("descripcion ")
                .append("FROM productos");

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
