package net.vergessxner.gungame.database;

import net.vergessxner.gungame.utils.GunGamePlayer;

import java.util.UUID;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: IStatsProvider
 */

public interface IStatsProvider {

    void updatePlayer(GunGamePlayer gunGamePlayer);
    void deletePlayer(UUID uuid);

    GunGamePlayer getPlayer(UUID uuid);
    void unregisterPlayer(GunGamePlayer gunGamePlayer);

    boolean isExist(UUID uuid);

}
