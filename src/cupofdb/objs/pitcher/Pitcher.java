package cupofdb.objs.pitcher;

import cupofdb.objs.cup.Cup;
import cupofdb.util.Config;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Connection pool manager.
 */
public abstract class Pitcher implements DataSource, AutoCloseable {
    private String name;
    private String url;
    private String username;
    private String password;
    private List<Cup> cups;
    private PrintWriter logWriter;
    private Spoon spoon;

    public Pitcher(String name, String url, String username, String password) throws SQLException {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.cups = new ArrayList<>();
        setLogWriter(new PrintWriter(System.out));
        setLoginTimeout(Config.LOGIN_TIMEOUT);
    }

    public void setSpoon(Spoon spoon) {
        this.spoon = spoon;
    }

    public Spoon getSpoon() {
        return this.spoon;
    }

    public void stirSpoon() {
        new Thread(this.spoon).start();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return DataSource.super.createConnectionBuilder();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public List<Cup> getCups() {
        return this.cups;
    }

    public Cup getCup() {
        for (Cup cup : this.cups) {
            if (!cup.isInUse()) {
                cup.use();
                return cup;
            }
        }
        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public void close() throws Exception {
        for (Cup connection : this.cups) {
            connection.close();
        }
    }
}
