package net.vergessxner.gungame.utils;

import net.vergessxner.gungame.GunGame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: GunGameTeam
 */

public class GunGameTeam {

    private final Player owner;

    private ArrayList<Player> playerList = new ArrayList<>();
    private ArrayList<Player> invites = new ArrayList<>();

    //All Teams
    private static HashMap<String, GunGameTeam> gunGameTeamMap = new HashMap<>();

    private GunGameTeam(Player owner) {
        this.owner = owner;
    }

    public void joinTeam(Player player) {
        if(!playerList.contains(player)) playerList.add(player);
        if(playerList.size() >= 2) {
            for (Player teamMates : getPlayerList()) {
                getPlayerList().forEach((team -> teamMates.getScoreboard().getTeam("team").addPlayer(team)));
            }
        }
        GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
        gunGamePlayer.setGunGameTeam(this);

        if(invites.contains(player)) invites.remove(player);
    }

    public void removeTeam(Player player) {
        for (Player players : playerList) {
            players.getScoreboard().getTeam("team").removePlayer(player);
            player.getScoreboard().getTeam("team").removePlayer(players);
        }
        if(playerList.contains(player)) playerList.remove(player);
        GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
        gunGamePlayer.setGunGameTeam(null);

        if(playerList.size() == 1) deleteTeam(owner);
        owner.sendMessage(GunGame.PREFIX + "§cDas Team wurde gelöscht!");
    }

    public void inviteTeam(Player player) {
        if(playerList.contains(player) && invites.contains(player)) return;
        invites.add(player);
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public ArrayList<Player> getInvites() {
        return invites;
    }

    public static HashMap<String, GunGameTeam> getGunGameTeamMap() {
        return gunGameTeamMap;
    }



    public static void createTeam(Player owner) {
        GunGameTeam gunGameTeam = new GunGameTeam(owner);
        gunGameTeam.joinTeam(owner);

        gunGameTeamMap.put(owner.getName(), gunGameTeam);
    }

    public static void deleteTeam(Player owner) {
        GunGameTeam gunGameTeam = gunGameTeamMap.get(owner.getName());
        for (Player player : gunGameTeam.getPlayerList()) {
            for (Player teamMate : gunGameTeam.getPlayerList()) {
                player.getScoreboard().getTeam("team").removePlayer(teamMate);
            }

            GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(owner.getUniqueId());
            gunGamePlayer.setGunGameTeam(null);
        }

        gunGameTeamMap.remove(owner.getName());
    }


}
