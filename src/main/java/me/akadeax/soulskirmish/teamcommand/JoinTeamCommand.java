package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import me.akadeax.soulskirmish.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class JoinTeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;

        if(!SoulSkirmish.teamHandler.addPlayer(p, args[0])) return false;

        ChatColor teamColor = TeamHandler.colors.get(args[0]);
        String teamName = args[0].replace('_', ' ');
        Bukkit.broadcastMessage(String.format("§6Player §2%s §6has joined Team %s%s§6!", p.getName(), teamColor, teamName));
        return true;
    }
}
