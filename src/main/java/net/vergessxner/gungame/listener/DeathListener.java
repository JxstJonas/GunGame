package net.vergessxner.gungame.listener;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.GunGamePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: DeathListener
 */

public class DeathListener implements Listener {

    private static HashMap<Player, Player> lastHit = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getLocation().getBlock().isLiquid()) {
            if(GunGame.getINSTANCE().getLoader().getConfig().getSpawn() != null)
                player.teleport(GunGame.getINSTANCE().getLoader().getConfig().getSpawn().toLocation());
            player.setVelocity(player.getVelocity().zero());

            if(lastHit.containsKey(player)) {
                player.sendMessage(GunGame.PREFIX + "§cDu wurdest von §6" + lastHit.get(player).getName() + "§cgetötet!");
                lastHit.get(player).sendMessage(GunGame.PREFIX + "§aDu hast §6" + player.getName() + "§agetötet!");
                lastHit.remove(player);

                GunGamePlayer gunGameTarget = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(lastHit.get(player).getUniqueId());

                gunGameTarget.setKills(gunGameTarget.getKills() + 1);
            } else {
                player.sendMessage(GunGame.PREFIX + "§cDu bist gestorben!");

                if(lastHit.containsValue(player)) {

                    for (Map.Entry<Player, Player> playerPlayerEntry : lastHit.entrySet()) {
                        if(playerPlayerEntry.getValue() == player) {
                            lastHit.remove(playerPlayerEntry.getKey());
                        }
                    }
                }
            }

            GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
            gunGamePlayer.setDeaths(gunGamePlayer.getDeaths() + 1);
        }
    }


    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            if((player.getHealth() - event.getDamage()) <= 0) {
                event.setDamage(0);

                if (GunGame.getINSTANCE().getLoader().getConfig().getSpawn() != null)
                    player.teleport(GunGame.getINSTANCE().getLoader().getConfig().getSpawn().toLocation());
                player.setVelocity(player.getVelocity().zero());

                player.sendMessage(GunGame.PREFIX + "§cDu wurdest von §6" + target.getName() + "§cgetötet!");
                target.sendMessage(GunGame.PREFIX + "§aDu hast §6" + player.getName() + "§agetötet!");

                for (Map.Entry<Player, Player> playerPlayerEntry : lastHit.entrySet()) {
                    if(playerPlayerEntry.getValue() == player) {
                        lastHit.remove(playerPlayerEntry.getKey());
                    }
                }


                GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
                GunGamePlayer gunGameTarget = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(target.getUniqueId());

                gunGamePlayer.setDeaths(gunGamePlayer.getDeaths() + 1);
                gunGameTarget.setKills(gunGameTarget.getKills() + 1);

                target.setHealth(20);
                target.playSound(player.getLocation(), Sound.LEVEL_UP, 8, 8);

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
                player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 8, 8);
            }
        }
    }

}
