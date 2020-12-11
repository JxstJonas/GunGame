package net.vergessxner.gungame.command;

import net.vergessxner.gungame.GunGame;
import net.vergessxner.gungame.utils.GunGamePlayer;
import net.vergessxner.gungame.utils.GunGameTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: TeamCommand
 */

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());


        if (args.length == 2) {
            //Register Target
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(GunGame.PREFIX + "§cDer Spieler wurde nicht gefunden!");
                return false;
            }
            GunGamePlayer gunGameTarget = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(target.getUniqueId());

            //Commands
            if (args[0].equalsIgnoreCase("invite")) {
                if (gunGamePlayer.getGunGameTeam() == null) {
                    //Create Team
                    GunGameTeam.createTeam(player);
                    gunGamePlayer.getGunGameTeam().inviteTeam(target);
                    target.sendMessage(GunGame.PREFIX + "§aDu wurdest von §7" + player.getDisplayName() + "§a in ein Team eingeladen!");

                } else if (gunGamePlayer.getGunGameTeam().getOwner() == player) {
                    //Invite in Existing Team
                    gunGamePlayer.getGunGameTeam().inviteTeam(target);
                    target.sendMessage(GunGame.PREFIX + "§aDu wurdest von §7" + player.getDisplayName() + "§a in ein Team eingeladen!");

                } else player.sendMessage(GunGame.PREFIX + "§cDu bist nicht der Team-Owner");
            } else if (args[0].equalsIgnoreCase("accept")) {
                //Accept Team
                if (gunGamePlayer.getGunGameTeam() != null) {
                    player.sendMessage(GunGame.PREFIX + "§cDu bist noch in einem Team");
                    return false;
                }

                if (gunGameTarget.getGunGameTeam() != null && gunGameTarget.getGunGameTeam().getInvites().contains(player)) {
                    gunGameTarget.getGunGameTeam().joinTeam(player);
                    for (Player teamMate : gunGameTarget.getGunGameTeam().getPlayerList()) {
                        teamMate.sendMessage(GunGame.PREFIX + "§7" + player.getDisplayName() + "§a ist dem Team beigetreten!");
                    }

                } else player.sendMessage(GunGame.PREFIX + "§cDu wurdest nicht von §7" + args[1] + " §ceingeladen!");
            } else if (args[0].equalsIgnoreCase("kick") && gunGamePlayer.getGunGameTeam() != null && gunGamePlayer.getGunGameTeam().getOwner() == player) {
                //Kick Team
                if (gunGamePlayer.getGunGameTeam() == gunGameTarget.getGunGameTeam()) {
                    for (Player teamMate : gunGameTarget.getGunGameTeam().getPlayerList()) {
                        teamMate.sendMessage(GunGame.PREFIX + "§7" + player.getDisplayName() + "§a hat das Team verlassen!");
                    }
                    gunGamePlayer.getGunGameTeam().removeTeam(target);
                }
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("leave")) {
                //Leave Team
                if (gunGamePlayer.getGunGameTeam() == null) {
                    player.sendMessage(GunGame.PREFIX + "§cDu bist in keinem Team!");
                    return false;
                }

                if (gunGamePlayer.getGunGameTeam().getOwner() == player) {
                    for (Player teamMate : gunGamePlayer.getGunGameTeam().getPlayerList()) {
                        teamMate.sendMessage(GunGame.PREFIX + "§cDas Team wurde gelöscht!");
                    }
                    GunGameTeam.deleteTeam(player);
                } else {
                    for (Player teamMate : gunGamePlayer.getGunGameTeam().getPlayerList()) {
                        teamMate.sendMessage(GunGame.PREFIX + "§7" + player.getDisplayName() + "§a hat das Team verlassen!");
                    }
                    gunGamePlayer.getGunGameTeam().removeTeam(player);
                }
            }
        }else {
            player.sendMessage(GunGame.PREFIX + "§aFolgende Befehle stehen zur Verfügung: ");
            player.sendMessage(GunGame.PREFIX + "§8- §7/team invite - Lade einen Spieler ein");
            player.sendMessage(GunGame.PREFIX + "§8- §7/team info - Zeigt dir eine Übersicht an Befehlen");
            player.sendMessage(GunGame.PREFIX + "§8- §7/team accept - Akzeptiere die Einladung eines Spielers");
            player.sendMessage(GunGame.PREFIX + "§8- §7/team kick - Entfernt einen Spieler aus deinem Team");
            player.sendMessage(GunGame.PREFIX + "§8- §7/team leave - Verlässt dein aktuelles Team");
        }

        return false;
    }
}
