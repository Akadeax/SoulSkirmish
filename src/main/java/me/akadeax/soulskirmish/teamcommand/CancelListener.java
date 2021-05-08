package me.akadeax.soulskirmish.teamcommand;

import me.akadeax.soulskirmish.SoulSkirmish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CancelListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(!SoulSkirmish.gameStarted && !e.getPlayer().isOp()) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!SoulSkirmish.gameStarted && !e.getPlayer().isOp()) e.setCancelled(true);
    }
}
