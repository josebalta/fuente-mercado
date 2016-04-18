/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoEstados;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.sql.ConectaDb;

/**
 *
 * @author Administrador
 */
public class DaoEstadosImpl implements DaoEstados {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoEstadosImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> estadosCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT idestado, descripcion ")
                .append("FROM estados ")
                .append("ORDER BY descripcion ASC");

        try (Connection cn = db.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            list = new ArrayList<>();

            while (rs.next()) { // filas de consulta
                Object[] reg = new Object[2];

                reg[0] = rs.getInt(1);
                reg[1] = rs.getString(2);

                list.add(reg);
            }

        } catch (SQLException e) {
            message = e.getMessage();
        }

        return list;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
