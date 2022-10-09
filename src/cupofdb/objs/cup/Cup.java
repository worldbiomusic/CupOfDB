package cupofdb.objs.cup;


import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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

    public ResultSet query(String query) throws SQLException {
        if (!isOk()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);
        try (statement) {
            return statement.executeQuery();
        }
    }


    public void query(String query, Consumer<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = query(query);
        Statement stmt = resultSet.getStatement();
        try (resultSet; stmt) {
            consumer.accept(resultSet);
        }
    }

    public int update(String query) throws SQLException {
        if (!isOk()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);
        try(statement) {
            return statement.executeUpdate();
        }
    }

    public void update(String query, Consumer<Integer> consumer) throws SQLException {
        int result = update(query);
        consumer.accept(result);
    }


    @Override
    public void close() throws Exception {
        this.inUse = false;
        connection.close();
    }
}
