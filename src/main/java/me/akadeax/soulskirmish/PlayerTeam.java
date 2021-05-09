package me.akadeax.soulskirmish;

import org.bukkit.scoreboard.Team;

import java.util.HashSet;
import java.util.UUID;

public class PlayerTeam {
    public int respawns = 3;

    public Team team;

    public PlayerTeam(Team team) {
        this.team = team;
    }
}
