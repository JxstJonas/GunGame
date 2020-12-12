package net.vergessxner.gungame.database.mysql;

import com.google.gson.Gson;
import net.vergessxner.gungame.database.IStatsProvider;
import net.vergessxner.gungame.utils.GunGamePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: MySQLStatsProvider
 */

public class MySQLStatsProvider implements IStatsProvider {

    private static final Gson GSON = new Gson();
    private HashMap<UUID, GunGamePlayer> map = new HashMap<>();
    private final Connection connection;

    public MySQLStatsProvider(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void updatePlayer(GunGamePlayer gunGamePlayer) {
        try {
            if(isExist(gunGamePlayer.getUuid())) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stats SET json ='" + GSON.toJson(gunGamePlayer) +"' WHERE  uuid = '" + gunGamePlayer.getUuid() +"'");
                preparedStatement.executeUpdate();
            }else {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stats (uuid, json) VALUE (?,?)");
                preparedStatement.setString(1, gunGamePlayer.getUuid().toString());
                preparedStatement.setString(2, GSON.toJson(gunGamePlayer));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deletePlayer(UUID uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM stats WHERE uuid = '" + uuid.toString() +"'");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public GunGamePlayer getPlayer(UUID uuid) {
        if(map.containsKey(uuid)) {
            return map.get(uuid);
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stats WHERE uuid = '" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            GunGamePlayer gunGamePlayer = null;
            if(!resultSet.next()) {
                gunGamePlayer = new GunGamePlayer();
                gunGamePlayer.setUuid(uuid);
            }else {
                String json = resultSet.getString("json");
                gunGamePlayer = GSON.fromJson(json, GunGamePlayer.class);
            }

            map.put(uuid, gunGamePlayer);

            resultSet.close();
            preparedStatement.close();
            return gunGamePlayer;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void unregisterPlayer(GunGamePlayer gunGamePlayer) {
        if(map.containsValue(gunGamePlayer)) map.remove(gunGamePlayer.getUuid());
    }

    @Override
    public boolean isExist(UUID uuid) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stats WHERE uuid = '" + uuid.toString() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean b = resultSet.next();
            resultSet.close();
            return b;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
