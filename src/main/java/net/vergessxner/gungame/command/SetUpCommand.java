package net.vergessxner.gungame.command;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.file.Locations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: SetUpCommand
 */

public class SetUpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(!player.hasPermission("gungame.setup")) {
            player.sendMessage(GunGame.PREFIX + "§cDu hast keine Berechtigung für diesen Command!");
            return false;
        }

        if(args.length != 1) {
            player.sendMessage(GunGame.PREFIX + "§7Verwende: ");
            player.sendMessage(" ");
            player.sendMessage(GunGame.PREFIX + "§a/setup spawn");
            return false;
        }


        if(args[0].equalsIgnoreCase("spawn")) {
            Locations locations = GunGame.getINSTANCE().getLoader().getConfig();
            locations.setSpawn(player.getLocation());
            GunGame.getINSTANCE().getLoader().set(locations);

            player.sendMessage(GunGame.PREFIX + "§7Du hast den Spawn §agesetzt§7!");
        }

        return false;
    }
}
