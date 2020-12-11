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
    private int maxLevel = 1;

    private int killStreak = 0;

    private GunGameTeam gunGameTeam;

    public int getKillStreak() {
        return killStreak;
    }

    public GunGameTeam getGunGameTeam() {
        return gunGameTeam;
    }

    public void setGunGameTeam(GunGameTeam gunGameTeam) {
        this.gunGameTeam = gunGameTeam;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public double getKD() {
        return (double) Math.round((double)kills / deaths * 100) / 100;
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
