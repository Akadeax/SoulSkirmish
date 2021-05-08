package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

class Run {
    public int minutesDelayAfter;
    public RunFunction function;

    public Run(int minutesDelayAfter, RunFunction function) {
        this.minutesDelayAfter = minutesDelayAfter;
        this.function = function;
    }
}

interface RunFunction {
    void run();
}

public class StartCommand implements CommandExecutor {
    private double initialBorderSize;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;

        Bukkit.broadcastMessage("§6The game has begun, §2Good luck§6!");
        SoulSkirmish.gameStarted = true;

        WorldBorder border = p.getWorld().getWorldBorder();
        initialBorderSize = border.getSize();

        final int shrinkTimeSecs = 60;
        runInOrder(
                new Run(60, () -> {
                    Bukkit.broadcastMessage(String.format("§6The world border has been set to §2%sx%s§6.", initialBorderSize, initialBorderSize));
                }),
                new Run(30, () -> {
                    double newSize = initialBorderSize * 0.75d;
                    border.setSize(newSize, shrinkTimeSecs);
                    Bukkit.broadcastMessage(String.format("§6The world border will now begin shrinking to §2%sx%s§6.", newSize, newSize));
                }),
                new Run(20, () -> {
                    double newSize = initialBorderSize * 0.5d;
                    border.setSize(newSize, shrinkTimeSecs);
                    Bukkit.broadcastMessage(String.format("§6The world border will now begin shrinking to §2%sx%s§6.", newSize, newSize));
                }),
                new Run(15, () -> {
                    double newSize = initialBorderSize * 0.25d;
                    border.setSize(newSize, shrinkTimeSecs);
                    Bukkit.broadcastMessage(String.format("§6The world border will now begin shrinking to §2%sx%s§6.", newSize, newSize));
                }),
                new Run(10, () -> {
                    double newSize = initialBorderSize * 0.1d;
                    border.setSize(newSize, shrinkTimeSecs);
                    Bukkit.broadcastMessage(String.format("§6The world border will now begin shrinking to §2%sx%s§6.", newSize, newSize));
                }),
                new Run(5, () -> {
                    double newSize = initialBorderSize * 0.05d;
                    border.setSize(newSize, shrinkTimeSecs);
                    Bukkit.broadcastMessage(String.format("§6The world border will now begin shrinking to §2%sx%s§6.", newSize, newSize));
                }),
                new Run(0, () -> {
                    Bukkit.broadcastMessage("§6All players have been given the wither effect. May the best team live last.");
                    for(Player currP : Bukkit.getOnlinePlayers()) {
                        if(currP.getGameMode().equals(GameMode.SURVIVAL)) {
                            currP.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 99999, 0));
                        }
                    }
                })
        );

        return true;
    }

    void runInOrder(Run... functions) {
        System.out.printf("running %s functions%n", functions.length);
        runSingle(0, functions);
    }

    private void runSingle(int current, Run... functions) {
        if(current > functions.length - 1) return;

        Run curr = functions[current];
        curr.function.run();

        new BukkitRunnable() {
            @Override
            public void run() {
                runSingle(current + 1, functions);
            }
        }.runTaskLater(SoulSkirmish.getInstance(), (long)curr.minutesDelayAfter * 20 * 60);
        // * 20 * 60 to convert minutes to ticks
    }
}
