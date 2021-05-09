package me.akadeax.soulskirmish;

import org.bukkit.*;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class DeathListener implements Listener {
    HashMap<UUID, Boolean> flying = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        PlayerTeam team = SoulSkirmish.teamHandler.getTeamOf(p);
        if(team == null) {
            e.setKeepInventory(true);
            e.getDrops().clear();
            return;
        }

        e.setDeathMessage(String.format("§6Player %s%s §6Has died.", TeamHandler.colors.get(team.team.getName()), p.getName()));

        if(team.respawns == 0) {
            p.setGameMode(GameMode.SPECTATOR);
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
            p.spawnParticle(Particle.REDSTONE, p.getLocation(), 50, dustOptions);
            return;
        }

        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 1);
        p.spawnParticle(Particle.REDSTONE, p.getLocation(), 50, dustOptions);
        team.respawns--;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        PlayerTeam team = SoulSkirmish.teamHandler.getTeamOf(p);
        if(team == null) {
            return;
        }
        if(p.getGameMode() != GameMode.SPECTATOR) {
            Location respawnLoc;

            respawnLoc = new Location(p.getWorld(), 0, 200, 0);
            flying.put(p.getUniqueId(), true);
            ItemStack elytra = new ItemStack(Material.ELYTRA);
            elytra.setDurability((short)412);
            p.getInventory().setChestplate(elytra);
            new BukkitRunnable() {

                @Override
                public void run() {
                    flying.put(p.getUniqueId(), false);
                    p.getInventory().setChestplate(new ItemStack(Material.AIR));
                }
            }.runTaskLater(SoulSkirmish.getInstance(), 300);
            p.sendMessage("§6You have 15 seconds with your elytra!");
            e.setRespawnLocation(respawnLoc);
        }
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        Player p = (Player)e.getViewers().get(0);
        if(flying.get(p.getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onD(PlayerDeathEvent e) {
        if(flying.get(e.getEntity().getUniqueId())) {
            e.getDrops().clear();
        }
    }
}
