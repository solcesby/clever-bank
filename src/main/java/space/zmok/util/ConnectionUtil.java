package space.zmok.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {

    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(ConfigUtil.VARIABLES.get("db_url"));
        ds.setUsername(ConfigUtil.VARIABLES.get("db_username"));
        ds.setPassword(ConfigUtil.VARIABLES.get("db_password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionUtil() {
    }

}
