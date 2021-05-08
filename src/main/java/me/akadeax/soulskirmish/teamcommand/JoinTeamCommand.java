package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import org.bukkit.Bukkit;
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

        Bukkit.broadcastMessage(String.format("§6Player §2%s §6has joined Team %s!", p.getName(), args[0]));
        return true;
    }
}
