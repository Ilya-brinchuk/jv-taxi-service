package core.basesyntax.util;

import core.basesyntax.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "2323");

        String url = "jdbc:mysql://localhost:3306/service_taxi?useUnicode=true&serverTimezone=UTC";
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, dbProperties);
        } catch (SQLException throwable) {
            throw new DataProcessingException("Can't establish the connection to DB", throwable);
        }
        return connection;
    }
}
