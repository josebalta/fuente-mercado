package dao.impl;

import dao.DaoRoles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parainfo.sql.ConectaDb;

public class DaoRolesImpl implements DaoRoles {

    private final ConectaDb db;
    private final StringBuilder sql;
    private String message;

    public DaoRolesImpl() {
        db = new ConectaDb();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Object[]> rolesCbo() {
        List<Object[]> list = null;
        sql.delete(0, sql.length())
                .append("SELECT idrol, descripcion ")
                .append("FROM roles ")
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
