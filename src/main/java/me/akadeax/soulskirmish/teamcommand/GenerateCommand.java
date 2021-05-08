package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GenerateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int amt;
        try {
            amt = Integer.parseInt(args[0]);
        } catch(Exception ignored) {
            return false;
        }

        SoulSkirmish.teamHandler.generateTeams(amt);
        sender.sendMessage(String.format("§6Successfully generated §2%s §6teams.", args[0]));
        return true;
    }
}
