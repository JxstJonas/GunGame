package net.vergessxner.gungame.listener;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.GunGamePlayer;
import net.vergessxner.gungame.utils.GunGameUpgrade;
import net.vergessxner.gungame.utils.file.Locations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: DeathListener
 */

public class DeathListener implements Listener {

    private static HashMap<Player, Player> lastHit = new HashMap<>();
    private static HashMap<Player, Integer> kills = new HashMap<>();


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlock().isLiquid()) {
            if (GunGame.getINSTANCE().getLoader().getConfig().getSpawn() != null)
                player.teleport(GunGame.getINSTANCE().getLoader().getConfig().getSpawn().toLocation());

            Bukkit.getScheduler().runTaskLater(GunGame.getINSTANCE(), () -> player.setVelocity(new Vector()), 1);


            if (lastHit.containsKey(player)) {
                player.sendMessage(GunGame.PREFIX + "§cDu wurdest von §7" + lastHit.get(player).getDisplayName() + " §cgetötet!");
                lastHit.get(player).sendMessage(GunGame.PREFIX + "§aDu hast §7" + player.getDisplayName() + " §agetötet!");

                GunGamePlayer gunGameTarget = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(lastHit.get(player).getUniqueId());
                gunGameTarget.setKills(gunGameTarget.getKills() + 1);

                GunGameUpgrade.levelUp(lastHit.get(player));
                lastHit.get(player).setHealth(lastHit.get(player).getMaxHealth());

                lastHit.remove(player);
            } else {
                player.sendMessage(GunGame.PREFIX + "§cDu bist gestorben!");

                //Removes all last hits he made on other players
                removeLastHit(player);

                GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
                gunGamePlayer.setDeaths(gunGamePlayer.getDeaths() + 1);

                kills.remove(player);
                GunGameUpgrade.levelDown(player);

            }
            player.setHealth(player.getMaxHealth());
        }
    }


    @EventHandler
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            if(isInProtection(player) || isInProtection(target)) {
                event.setCancelled(true);
                return;
            }

            GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
            GunGamePlayer gunGameTarget = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(target.getUniqueId());

            if(gunGamePlayer.getGunGameTeam() == gunGameTarget.getGunGameTeam() && gunGamePlayer.getGunGameTeam() != null) {
                event.setCancelled(true);
                return;
            }

            lastHit.put(player, target);

            if((player.getHealth() - event.getDamage()) <= 0) {
                event.setDamage(0);

                if (GunGame.getINSTANCE().getLoader().getConfig().getSpawn() != null)
                    player.teleport(GunGame.getINSTANCE().getLoader().getConfig().getSpawn().toLocation());
                Bukkit.getScheduler().runTaskLater(GunGame.getINSTANCE(), () -> player.setVelocity(new Vector()), 1);

                player.sendMessage(GunGame.PREFIX + "§cDu wurdest von §7" + target.getName() + " §cgetötet!");
                target.sendMessage(GunGame.PREFIX + "§aDu hast §7" + player.getName() + " §agetötet!");

                //Removes all last hits he made on other player
                removeLastHit(player);

                gunGamePlayer.setDeaths(gunGamePlayer.getDeaths() + 1);
                gunGameTarget.setKills(gunGameTarget.getKills() + 1);

                target.playSound(player.getLocation(), Sound.LEVEL_UP, 8, 8);

                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 1));
                player.playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 8, 8);

                //Killstreak
                if(!kills.containsKey(target)) {
                    kills.put(target, 1);
                } else {
                    int previousKills = kills.get(target);
                    kills.put(target, previousKills + 1);
                    gunGameTarget.setKillStreak(kills.get(target));
                }
                if(gunGameTarget.getKillStreak() <= kills.get(target)) {
                    gunGameTarget.setKillStreak(kills.get(target));
                }

                kills.remove(player);

                GunGameUpgrade.levelUp(target);
                GunGameUpgrade.levelDown(player);

                player.setHealth(player.getMaxHealth());
                target.setHealth(target.getMaxHealth());
            }
        }
    }


    private void removeLastHit(Player player) {
        if(!lastHit.isEmpty()) {
            Iterator<Map.Entry<Player, Player>> it = lastHit.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Player, Player> entry = it.next();
                if(entry.getValue() == player) {
                    it.remove();
                }
            }

        }
    }


    public boolean isInProtection(Player player) {
        Location loc = player.getLocation();
        GunGame.getINSTANCE().getLoader().load();
        Locations locations = GunGame.getINSTANCE().getLoader().getConfig();
        if(locations == null || locations.getPos1() == null || locations.getPos2() == null) return false;


        if(loc.getBlockX() < locations.getPos1().getX() || loc.getBlockX() > locations.getPos2().getX()) return false;
        if (loc.getBlockZ() < locations.getPos1().getZ() || loc.getBlockZ() > locations.getPos2().getZ()) return false;
        if (loc.getBlockY() < locations.getPos1().getY() || loc.getBlockY() > locations.getPos2().getY()) return false;

        return true;
    }

}
