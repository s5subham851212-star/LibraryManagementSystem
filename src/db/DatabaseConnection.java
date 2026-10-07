import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {

        try {

            Properties properties = new Properties();

            FileInputStream file =
                    new FileInputStream("db.properties");

            properties.load(file);
            file.close();

            URL = properties.getProperty("DB_URL");
            USER = properties.getProperty("DB_USER");
            PASSWORD = properties.getProperty("DB_PASSWORD");

        } catch (IOException e) {

            System.out.println(
                    "Could not load database configuration."
            );
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}