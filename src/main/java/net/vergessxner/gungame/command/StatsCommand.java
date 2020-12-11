package net.vergessxner.gungame.command;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.GunGamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: StatsCommand
 */

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(args.length == 0) {
            GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());
            player.sendMessage(GunGame.PREFIX + "§7Deine Stats:");
            player.sendMessage(GunGame.PREFIX + "");
            player.sendMessage(GunGame.PREFIX + "Kills: " + gunGamePlayer.getKills());
            player.sendMessage(GunGame.PREFIX + "Deaths: " + gunGamePlayer.getDeaths());
            player.sendMessage(GunGame.PREFIX + "K/D: " + gunGamePlayer.getKD());
            player.sendMessage(GunGame.PREFIX + "Max-Level: " + gunGamePlayer.getMaxLevel());
            player.sendMessage(GunGame.PREFIX + "");
        }else player.sendMessage(GunGame.PREFIX + "Verwende: §cA/stats");
        return false;
    }
}
