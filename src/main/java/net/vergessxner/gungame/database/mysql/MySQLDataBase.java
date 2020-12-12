package net.vergessxner.gungame.database.mysql;

import net.vergessxner.gungame.database.IDataBase;
import net.vergessxner.gungame.database.IStatsProvider;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: MySQLDataBase
 */

public class MySQLDataBase implements IDataBase {

    private Connection connection;
    private IStatsProvider mysqlStatsProvider;

    private final String host;
    private final String username;
    private final String password;
    private final String database;
    private final int port;


    public MySQLDataBase(String host, String username, String password, String database, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;

    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);

            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS stats (uuid VARCHAR(100), json LONGTEXT)");
            preparedStatement.executeUpdate();
            preparedStatement.close();

            mysqlStatsProvider = new MySQLStatsProvider(connection);
            Bukkit.getConsoleSender().sendMessage("§aMySQL connected!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return connection.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public IStatsProvider getStatsProvider() {
        return mysqlStatsProvider;
    }
}
