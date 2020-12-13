package net.vergessxner.gungame.listener;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.file.Locations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author Jonas
 * Created: 13.12.2020
 * Class: ProtectionListener
 */

public class ProtectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageByPlayer(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            if(isInProtection(player) || isInProtection(target)) event.setCancelled(true);

        }else event.setCancelled(true);
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
