package parainfo.sql;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        ConectaDb db = new ConectaDb();

        try (Connection cn = db.getConnection()) {
            
            System.out.println("Ok");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
