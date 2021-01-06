package net.vergessxner.gungame.listener;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.database.IDataBase;
import net.vergessxner.gungame.utils.GunGamePlayer;
import net.vergessxner.gungame.utils.GunGameTeam;
import net.vergessxner.gungame.utils.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: JoinQuitListener
 */

public class JoinQuitListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        if(GunGame.getINSTANCE().getLoader().getConfig().getSpawn() != null)
            player.teleport(GunGame.getINSTANCE().getLoader().getConfig().getSpawn().toLocation());

        player.setFoodLevel(20);
        player.setMaxHealth(20);
        player.setHealth(20);
        player.setLevel(1);

        player.getInventory().setItem(0, new ItemBuilder(Material.WOOD_AXE).setName("Level").setUnbreakable(true).toItemStack());

        GunGamePlayer gunGamePlayer = null;

        IDataBase dataBase = GunGame.getINSTANCE().getDataBase();
        if(!dataBase.getStatsProvider().isExist(player.getUniqueId())) {
            gunGamePlayer = new GunGamePlayer();
            gunGamePlayer.setUuid(player.getUniqueId());
            dataBase.getStatsProvider().updatePlayer(gunGamePlayer);
        } else
            gunGamePlayer = dataBase.getStatsProvider().getPlayer(player.getUniqueId());

        GunGame.getINSTANCE().getGunGameScoreboard().setScoreboard(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();

        player.getInventory().clear();

        IDataBase dataBase = GunGame.getINSTANCE().getDataBase();
        GunGamePlayer gunGamePlayer = dataBase.getStatsProvider().getPlayer(player.getUniqueId());

        if(gunGamePlayer.getGunGameTeam() != null) {

            if(gunGamePlayer.getGunGameTeam().getOwner() == player) {
                for (Player teamMate : gunGamePlayer.getGunGameTeam().getPlayerList()) {
                    teamMate.sendMessage(GunGame.PREFIX + "§cDein Team wurde gelöscht!");
                }
                GunGameTeam.deleteTeam(player);
            } else {
                for (Player teamMate : gunGamePlayer.getGunGameTeam().getPlayerList()) {
                    teamMate.sendMessage(GunGame.PREFIX + "§7" + player.getDisplayName() + " hat das Team verlassen!");
                }
                gunGamePlayer.getGunGameTeam().removeTeam(player);
            }
        }

        dataBase.getStatsProvider().updatePlayer(dataBase.getStatsProvider().getPlayer(player.getUniqueId()));
        dataBase.getStatsProvider().unregisterPlayer(dataBase.getStatsProvider().getPlayer(player.getUniqueId()));
    }

}
