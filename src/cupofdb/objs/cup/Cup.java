package cupofdb.objs.cup;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection wrapper.
 */
public class Cup implements AutoCloseable {
    private Connection connection;
    private boolean inUse;

    public Cup(String url,
               String user, String password) {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.inUse = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection use() {
        this.inUse = true;
        return connection;
    }

    public boolean isInUse() {
        return inUse;
    }

    public boolean isOk() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    /**
     * Gets the connection.
     */
    public Connection getConnection() {
        return connection;
    }


    @Override
    public void close() throws Exception {
        this.inUse = false;
        connection.close();
    }
}
