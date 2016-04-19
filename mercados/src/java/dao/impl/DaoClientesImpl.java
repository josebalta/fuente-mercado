/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dto.Clientes;
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
public class DaoClientesImpl implements dao.DaoClientes {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoClientesImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> clientesQry(int numpag, int ctasfils) {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("clientes.idcliente, ")
                .append("vendedores.nombre, ")
                .append("clientes.nombre, ")
                .append("mercados.nombre, ")
                .append("clientes.direccion, ")
                .append("clientes.telefono, ")
                .append("clientes.fechaingreso, ")
                .append("clientes.fechacese, ")
                .append("estados.descripcion ")
                .append("FROM clientes ")
                .append("INNER JOIN vendedores ")
                .append("ON clientes.idvendedor = vendedores.idvendedor ")
                .append("INNER JOIN mercados ")
                .append("ON clientes.idmercado = mercados.idmercado ")
                .append("INNER JOIN estados ")
                .append("ON clientes.idestado = estados.idestado ")
                .append("ORDER BY clientes.nombre ASC ")
                .append("LIMIT ? OFFSET ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, ctasfils);    // cantidad de filas
            ps.setInt(2, (numpag * ctasfils)); // desde (numpag == 0 para la primera

            try (ResultSet rs = ps.executeQuery()) {

                list = new ArrayList<>();

                while (rs.next()) { // filas de consulta
                    Object[] u = new Object[9];

                    u[0] = rs.getInt(1);
                    u[1] = rs.getString(2);
                    u[2] = rs.getString(3);
                    u[3] = rs.getString(4);
                    u[4] = rs.getString(5);
                    u[5] = rs.getInt(6);
                    u[6] = rs.getDate(7);
                    u[7] = rs.getDate(8);
                    u[8] = rs.getString(9);

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
                .append("SELECT COUNT(*) FROM clientes");

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
    public String clientesIns(Clientes clientes) {
        sql.delete(0, sql.length())
                .append("INSERT INTO clientes ")
                .append("(idvendedor, idmercado, nombre, direccion, telefono, idestado, fechaingreso, fechacese) ")
                .append("VALUES(?,?,?,?,?,?,?,?)");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, clientes.getIdvendedor());
            ps.setInt(2, clientes.getIdmercado());
            ps.setString(3, clientes.getNombre());
            ps.setString(4, clientes.getDireccion());
            ps.setInt(5, clientes.getTelefono());
            ps.setShort(6, clientes.getIdestado());
            ps.setDate(7, clientes.getFechaingreso());
            ps.setDate(8, clientes.getFechacese());

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
    public String clientesDel(List<Integer> ids) {
        sql.delete(0, sql.length())
                .append("DELETE FROM clientes WHERE idcliente = ?");

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
    public Clientes clientesGet(Integer idcliente) {
        Clientes clientes = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idcliente, ")
                .append("idvendedor, ")
                .append("nombre, ")
                .append("idmercado, ")
                .append("direccion, ")
                .append("telefono, ")
                .append("fechaingreso, ")
                .append("fechacese, ")
                .append("idestado ")
                .append("FROM clientes ")
                .append("WHERE idcliente = ?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, idcliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    clientes = new Clientes();

                    clientes.setIdcliente(rs.getInt(1));
                    clientes.setIdvendedor(rs.getInt(2));
                    clientes.setNombre(rs.getString(3));
                    clientes.setIdmercado(rs.getInt(4));
                    clientes.setDireccion(rs.getString(5));
                    clientes.setTelefono(rs.getInt(6));
                    clientes.setFechaingreso(rs.getDate(7));
                    clientes.setFechacese(rs.getDate(8));
                    clientes.setIdestado(rs.getShort(9));

                } else {
                    throw new SQLException("ID no existe");
                }
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return clientes;
    }

    @Override
    public String clientesUpd(Clientes clientes) {
        sql.delete(0, sql.length())
                .append("UPDATE clientes ")
                .append("SET ")
                .append("idvendedor=?, idmercado=?, nombre=?, direccion=?, telefono=?, idestado=?, fechaingreso=?, fechacese=? ")
                .append("WHERE idcliente=?");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            ps.setInt(1, clientes.getIdvendedor());
            ps.setInt(2, clientes.getIdmercado());
            ps.setString(3, clientes.getNombre());
            ps.setString(4, clientes.getDireccion());
            ps.setInt(5, clientes.getTelefono());
            ps.setShort(6, clientes.getIdestado());
            ps.setDate(7, clientes.getFechaingreso());
            ps.setDate(8, clientes.getFechacese());
            ps.setInt(9, clientes.getIdcliente());

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
    public List<Object[]> clientesCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT ")
                .append("idcliente,")
                .append("nombre ")
                .append("FROM clientes");

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
