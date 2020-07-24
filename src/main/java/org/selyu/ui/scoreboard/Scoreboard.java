package org.selyu.ui.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;
import org.selyu.ui.scoreboard.entry.ScoreboardEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class Scoreboard {
    private final UUID owner;
    private final JavaPlugin plugin;
    private final ScoreboardAdapter adapter;
    private final org.bukkit.scoreboard.Scoreboard bukkitScoreboard;
    private final Objective bukkitObjective;
    private final List<ScoreboardEntry> scoreboardEntryList = new ArrayList<>();

    public Scoreboard(UUID owner, JavaPlugin plugin, ScoreboardAdapter adapter, org.bukkit.scoreboard.Scoreboard bukkitScoreboard) {
        requireNonNull(owner, "owner");
        requireNonNull(plugin, "plugin");
        requireNonNull(adapter, "adapter");
        requireNonNull(bukkitScoreboard, "bukkitScoreboard");
        this.owner = owner;
        this.plugin = plugin;
        this.adapter = adapter;
        this.bukkitScoreboard = bukkitScoreboard;

        bukkitObjective = bukkitScoreboard.registerNewObjective("default", "dummy", adapter.getTitle().getCurrentFrame());
        bukkitObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        getOwnerAsPlayer().setScoreboard(bukkitScoreboard);
    }

    public ScoreboardEntry getEntry(int index) {
        // Avoid IndexOutOfBounds
        return index >= scoreboardEntryList.size() ? null : scoreboardEntryList.get(index);
    }

    /**
     * Add an entry to the scoreboard
     *
     * @param entry The entry to add
     */
    public void addEntry(ScoreboardEntry entry) {
        requireNonNull(entry, "entry");
        if (!scoreboardEntryList.contains(entry))
            scoreboardEntryList.add(entry);
    }

    /**
     * Reset the entry's scores and remove it from the list
     *
     * @param index The position of the entry
     */
    public void removeEntry(int index) {
        ScoreboardEntry entry = scoreboardEntryList.get(index);
        if (entry == null)
            return;

        scoreboardEntryList.remove(index);
        bukkitScoreboard.resetScores(entry.getTeamName());
    }

    public ScoreboardAdapter getAdapter() {
        return adapter;
    }

    /**
     * @return The player
     * @throws NullPointerException If player is null/offline
     */
    public Player getOwnerAsPlayer() {
        Player player = plugin.getServer().getPlayer(owner);
        if (player == null)
            throw new NullPointerException("Player is null");

        return player;
    }

    public org.bukkit.scoreboard.Scoreboard getBukkitScoreboard() {
        return bukkitScoreboard;
    }

    public Objective getBukkitObjective() {
        return bukkitObjective;
    }

    public List<ScoreboardEntry> getScoreboardEntryList() {
        return scoreboardEntryList;
    }
}
