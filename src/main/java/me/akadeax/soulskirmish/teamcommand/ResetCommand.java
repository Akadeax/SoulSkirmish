package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SoulSkirmish.gameStarted = false;
        SoulSkirmish.teamHandler.resetTeams();
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().clear();
            p.setHealth(0);
        }
        Bukkit.broadcastMessage("§6The game has been reset.");
        return true;
    }
}
