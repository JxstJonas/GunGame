package net.vergessxner.gungame.utils;

import net.vergessxner.gungame.GunGame;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: GunGameUpgrade
 */

public class GunGameUpgrade {

    private static HashMap<Player, Integer> levelMap = new HashMap<>();

    public static void levelUp(Player player) {
        GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
        if(!levelMap.containsKey(player)) {
            levelMap.put(player, 2);
        }else {
            int previousLevel = levelMap.get(player);
            levelMap.put(player, previousLevel + 1);
        }

        if(gunGamePlayer.getMaxLevel() <= levelMap.get(player)) gunGamePlayer.setMaxLevel(levelMap.get(player));

        player.setMaxHealth(player.getMaxHealth() + 1);
        player.setLevel(levelMap.get(player));
    }


    public static void levelDown(Player player) {
        if(levelMap.containsKey(player)) {
            int previousLevel = levelMap.get(player);
            levelMap.put(player, previousLevel - 1);
            player.setLevel(levelMap.get(player));
        }else player.setLevel(1);

        if(player.getMaxHealth() > 20)
            player.setMaxHealth(player.getMaxHealth() - 1);
    }

}
