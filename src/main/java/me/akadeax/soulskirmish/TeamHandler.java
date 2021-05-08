package me.akadeax.soulskirmish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class TeamHandler {
    private final TreeMap<String, ChatColor> colors = new TreeMap<String, ChatColor>() {
        {
            put("red", ChatColor.RED);
            put("blue", ChatColor.DARK_BLUE);
            put("green", ChatColor.GREEN);
            put("gold", ChatColor.GOLD);
            put("purple", ChatColor.DARK_PURPLE);
            put("aqua", ChatColor.AQUA);
        }
    };

    public HashSet<PlayerTeam> teams = new HashSet<>();

    public void generateTeams(int teamAmount) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();

        teams = new HashSet<>();
        for(Team team : board.getTeams()) {
            team.unregister();
        }

        Iterator<String> itr = colors.keySet().iterator();

        for(int i = 0; i < teamAmount; i++) {
            String curr = itr.next();

            Team newTeam = board.registerNewTeam(curr);
            PlayerTeam newPlayerTeam = new PlayerTeam(newTeam);

            newTeam.setColor(colors.get(curr));
            newTeam.setAllowFriendlyFire(false);
            newTeam.setCanSeeFriendlyInvisibles(true);
            newTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);

            teams.add(newPlayerTeam);
        }
    }

    public boolean addPlayer(Player player, String teamName) {
        for(PlayerTeam team : teams) {
            if(team.team.getName().equals(teamName)) {
                team.team.addEntry(player.getName());
                return true;
            }
        }
        return false;
    }

    public PlayerTeam getTeamOf(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        Scoreboard board = manager.getMainScoreboard();

        Team pTeam = board.getEntryTeam(player.getName());
        assert pTeam != null;

        for(PlayerTeam team : teams) {
            if(team.team.getName().equals(pTeam.getName())) return team;
        }

        return null;
    }
}
