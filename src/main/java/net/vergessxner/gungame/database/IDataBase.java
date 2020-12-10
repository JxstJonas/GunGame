package net.vergessxner.gungame.database;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: IDataBase
 */

public interface IDataBase {

    void connect();
    void disconnect();

    boolean isConnected();

    IStatsProvider getStatsProvider();

}
