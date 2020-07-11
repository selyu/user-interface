package org.selyu.ui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.listener.UserInterfaceListener;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UserInterfaceProvider {
    private final JavaPlugin plugin;
    private final ScoreboardManager scoreboardManager;
    private final Map<UUID, Scoreboard> bukkitScoreboardMap = new HashMap<>();

    /**
     * @param plugin The {@link JavaPlugin} registering the provider
     * @param updateSpeedTicks The speed in ticks (20/s) that the task will run
     */
    public UserInterfaceProvider(@NotNull JavaPlugin plugin, long updateSpeedTicks) {
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
    public void setBoard(@NotNull Player player, @NotNull ScoreboardAdapter adapter) {
        scoreboardManager.setBoard(player, adapter);
    }

    /**
     * Removes the players current scoreboard
     *
     * @param player The player
     */
    public void removeBoard(@NotNull Player player) {
        scoreboardManager.removeBoard(player);
        bukkitScoreboardMap.remove(player.getUniqueId());
    }

    /**
     * @param player The player the scoreboard is for
     * @return The scoreboard
     */
    public @NotNull Scoreboard getBukkitScoreboard(@NotNull Player player) {
        //noinspection ConstantConditions
        return bukkitScoreboardMap.computeIfAbsent(player.getUniqueId(), (uuid) -> plugin.getServer().getScoreboardManager().getNewScoreboard());
    }
}
