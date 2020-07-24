package org.selyu.ui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.selyu.ui.listener.UserInterfaceListener;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class UserInterfaceProvider {
    private final JavaPlugin plugin;
    private final ScoreboardManager scoreboardManager;
    private final Map<UUID, Scoreboard> bukkitScoreboardMap = new HashMap<>();

    /**
     * @param plugin           The {@link JavaPlugin} registering the provider
     * @param updateSpeedTicks The speed in ticks (20/s) that the task will run
     */
    public UserInterfaceProvider(JavaPlugin plugin, long updateSpeedTicks) {
        requireNonNull(plugin, "plugin");
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new UserInterfaceListener(this), plugin);
        scoreboardManager = new ScoreboardManager(plugin, this, updateSpeedTicks);
    }

    /**
     * Add the player to the scoreboard map for updating with the task
     *
     * @param player  The player
     * @param adapter The adapter
     */
    public void setBoard(Player player, ScoreboardAdapter adapter) {
        requireNonNull(player, "player");
        requireNonNull(adapter, "adapter");
        scoreboardManager.setBoard(player, adapter);
    }

    /**
     * Removes the players current scoreboard
     *
     * @param player The player
     */
    public void removeBoard(Player player) {
        requireNonNull(player, "player");
        scoreboardManager.removeBoard(player);
        bukkitScoreboardMap.remove(player.getUniqueId());
    }

    /**
     * @param player The player the scoreboard is for
     * @return The scoreboard
     */
    public Scoreboard getBukkitScoreboard(Player player) {
        requireNonNull(player, "player");
        //noinspection ConstantConditions
        return bukkitScoreboardMap.computeIfAbsent(player.getUniqueId(), (uuid) -> plugin.getServer().getScoreboardManager().getNewScoreboard());
    }
}
