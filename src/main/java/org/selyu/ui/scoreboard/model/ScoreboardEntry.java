package org.selyu.ui.scoreboard.model;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;
import org.selyu.ui.scoreboard.Scoreboard;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.requireNonNull;

public final class ScoreboardEntry {
    private final Team bukkitTeam;
    private final String teamName;
    private ScoreboardLine line;

    public ScoreboardEntry(Scoreboard scoreboard, ScoreboardLine line) {
        requireNonNull(scoreboard, "scoreboard");
        requireNonNull(line, "line");
        this.line = line;
        teamName = createUniqueTeamName(scoreboard.getBukkitScoreboard());

        bukkitTeam = scoreboard.getBukkitScoreboard().registerNewTeam(teamName);
        bukkitTeam.addEntry(teamName);
    }

    /**
     * Creates a unique name to be used for a {@link org.bukkit.scoreboard.Team}
     *
     * @param bukkitScoreboard The scoreboard
     * @return Unique team name
     */
    private static String createUniqueTeamName(org.bukkit.scoreboard.Scoreboard bukkitScoreboard) {
        requireNonNull(bukkitScoreboard, "bukkitScoreboard");
        String teamName = ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)].toString();
        do {
            teamName += ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)];

            // Try again
            if (teamName.length() > 16) {
                teamName = ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)].toString();
            }
        } while (bukkitScoreboard.getTeam(teamName) != null);

        return teamName;
    }

    public ScoreboardLine getLine() {
        return line;
    }

    public void setLine(ScoreboardLine line) {
        requireNonNull(line, "objective");
        this.line = line;
    }

    public Team getBukkitTeam() {
        return bukkitTeam;
    }

    public String getTeamName() {
        return teamName;
    }
}
