package net.vergessxner.gungame.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.vergessxner.gungame.GunGame;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author Jonas
 * Created: 11.12.2020
 * Class: GunGameScoreboard
 */

public class GunGameScoreboard {

    public GunGameScoreboard() {
        if(Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(GunGame.getINSTANCE(), () -> {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(onlinePlayer);

                    PlayerConnection playerConnection = ((CraftPlayer) onlinePlayer).getHandle().playerConnection;
                    IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "§aTeams erlaubt "+ "\"}");
                    playerConnection.sendPacket(new PacketPlayOutChat(chatBaseComponent, (byte) 2));
                }
            }, 20, 0);
        }
    }

    public void setScoreboard(Player player) {
        if(player.getScoreboard() != null) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

            scoreboard.registerNewTeam("team").setSuffix(" §7[§d❤§7]");

            Objective objective = scoreboard.registerNewObjective("gun", "game");

            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§9§lDEINSERVER.NET");

            GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());

            objective.getScore(" ").setScore(14);
            objective.getScore("Replay-ID: ").setScore(13);
            objective.getScore("§eDeaktiviert").setScore(12);
            objective.getScore("§5").setScore(11);
            objective.getScore("Top §aKillstreak:").setScore(10);

            Team killstreak = scoreboard.registerNewTeam("killstreak");
            killstreak.setSuffix(gunGamePlayer.getKillStreak() + "");
            killstreak.addEntry("§e");
            objective.getScore("§e").setScore(9);

            objective.getScore("§6").setScore(8);
            objective.getScore("Top Level:").setScore(7);

            Team level = scoreboard.registerNewTeam("level");
            level.setSuffix(gunGamePlayer.getMaxLevel() + "");
            level.addEntry("§r§e");
            objective.getScore("§r§e").setScore(6);

            objective.getScore("§7").setScore(5);
            objective.getScore("Spieler: ").setScore(4);

            Team playerTeam = scoreboard.registerNewTeam("player");
            playerTeam.setSuffix( Bukkit.getOnlinePlayers().size() + "");
            playerTeam.addEntry("§a");
            objective.getScore("§a").setScore(3);

            objective.getScore("§8").setScore(2);
            objective.getScore("Map: ").setScore(1);
            objective.getScore("§b" + player.getWorld().getName()).setScore(0);

            player.setScoreboard(scoreboard);
        }
    }

    public void updateScoreboard(Player player) {
        if(player.getScoreboard() == null) {
            setScoreboard(player);
            return;
        }

        Scoreboard scoreboard = player.getScoreboard();
        GunGamePlayer gunGamePlayer = GunGame.getINSTANCE().getDataBase().getStatsProvider().getPlayer(player.getUniqueId());

        scoreboard.getTeam("killstreak").setSuffix(gunGamePlayer.getKillStreak() + "");
        scoreboard.getTeam("level").setSuffix(gunGamePlayer.getMaxLevel() + "");
        scoreboard.getTeam("player").setSuffix(Bukkit.getOnlinePlayers().size() + "");
    }

}
