package org.selyu.ui.scoreboard.entry;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;
import org.selyu.ui.scoreboard.Scoreboard;
import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.requireNonNull;

public final class ScoreboardEntry {
    private final Team bukkitTeam;
    private final String teamName;
    private ScoreboardObjective objective;
    private ScoreboardTitle title;

    public ScoreboardEntry(Scoreboard scoreboard, ScoreboardObjective objective, ScoreboardTitle title) {
        requireNonNull(scoreboard, "scoreboard");
        requireNonNull(objective, "objective");
        requireNonNull(title, "title");
        this.objective = objective;
        this.title = title;
        teamName = createUniqueTeamName(scoreboard.getBukkitScoreboard());

        bukkitTeam = scoreboard.getBukkitScoreboard().registerNewTeam(teamName);
        bukkitTeam.addEntry(teamName);
    }

    /**
     * Creates a unique name to be used for a {@link org.bukkit.scoreboard.Team}
     * Gets a random {@link ChatColor}, If a team is registered under that then add another
     * {@link ChatColor} to the name and do that recursively until the name isn't taken.
     * Substrings the name if its length is > 16
     *
     * @param bukkitScoreboard The scoreboard
     * @return Unique team name
     */
    private static String createUniqueTeamName(org.bukkit.scoreboard.Scoreboard bukkitScoreboard) {
        requireNonNull(bukkitScoreboard, "bukkitScoreboard");
        String teamName = ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)] + "";
        while (bukkitScoreboard.getTeam(teamName) != null)
            teamName += ChatColor.values()[ThreadLocalRandom.current().nextInt(ChatColor.values().length)];

        if (teamName.length() > 16)
            teamName = teamName.substring(16);

        return teamName;
    }

    public ScoreboardObjective getObjective() {
        return objective;
    }

    public void setObjective(ScoreboardObjective objective) {
        requireNonNull(objective, "objective");
        this.objective = objective;
    }

    public ScoreboardTitle getTitle() {
        return title;
    }

    public void setTitle(ScoreboardTitle title) {
        requireNonNull(title, "title");
        this.title = title;
    }

    public Team getBukkitTeam() {
        return bukkitTeam;
    }

    public String getTeamName() {
        return teamName;
    }
}
