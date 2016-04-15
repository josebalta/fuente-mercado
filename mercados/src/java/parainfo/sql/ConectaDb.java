package parainfo.sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author parainformaticos.com
 */
public class ConectaDb {

    private final Properties props = new Properties();

    public Connection getConnection() {
        Connection cn = null;

        try {
            Class.forName(props.getProperty("jdbc.driver")).newInstance();
            cn = DriverManager.getConnection(
                    props.getProperty("jdbc.url"),
                    props.getProperty("jdbc.user"),
                    props.getProperty("jdbc.password"));

        } catch (SQLException | ClassNotFoundException |
                InstantiationException | IllegalAccessException ex) {
        }

        return cn;
    }

    public ConectaDb() {
        InputStream in;

        try {
            in = getClass().getClassLoader().getResourceAsStream(
                    "config/database.properties");
            props.load(in);
            in.close();

        } catch (IOException ex) {
        }
    }
}
