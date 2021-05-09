package me.akadeax.soulskirmish;

import me.akadeax.soulskirmish.teamcommand.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoulSkirmish extends JavaPlugin {
    private static SoulSkirmish instance;
    public static SoulSkirmish getInstance() {
        return instance;
    }

    public static TeamHandler teamHandler = new TeamHandler();
    public static boolean gameStarted = false;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        instance = this;
        getCommand("join").setExecutor(new JoinTeamCommand());
        getCommand("join").setTabCompleter(new JoinTeamCompleter());

        getCommand("start").setExecutor(new StartCommand());
        getCommand("generate").setExecutor(new GenerateCommand());
        getCommand("reset").setExecutor(new ResetCommand());

        Bukkit.getPluginManager().registerEvents(new CancelListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
    }
}
