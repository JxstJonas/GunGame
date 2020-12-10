package net.vergessxner.gungame.utils;

import java.util.UUID;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: GunGamePlayer
 */

public class GunGamePlayer {

    private UUID uuid;

    private int kills = 0;
    private int deaths = 0;
    private int maxLevel = 0;

    public double getKD() {
        return (double) Math.round((double)44 / 68 * 100) / 100;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
