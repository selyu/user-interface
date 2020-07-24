package org.selyu.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.selyu.ui.scoreboard.Scoreboard;
import org.selyu.ui.scoreboard.adapter.ScoreboardAdapter;
import org.selyu.ui.scoreboard.task.ScoreboardTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

final class ScoreboardManager implements Listener {
    private final JavaPlugin plugin;
    private final UserInterfaceProvider userInterfaceProvider;
    private final Map<UUID, Scoreboard> scoreboardMap = new ConcurrentHashMap<>();

    ScoreboardManager(JavaPlugin plugin, UserInterfaceProvider userInterfaceProvider, long updateSpeedTicks) {
        requireNonNull(plugin, "plugin");
        requireNonNull(userInterfaceProvider, "userInterfaceProvider");
        this.plugin = plugin;
        this.userInterfaceProvider = userInterfaceProvider;
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new ScoreboardTask(scoreboardMap), 0, updateSpeedTicks);
    }

    public void setBoard(Player player, ScoreboardAdapter adapter) {
        requireNonNull(player, "player");
        requireNonNull(adapter, "adapter");
        scoreboardMap.put(player.getUniqueId(), new Scoreboard(
                player.getUniqueId(),
                plugin,
                adapter,
                userInterfaceProvider.getBukkitScoreboard(player)
        ));
    }

    public void removeBoard(Player player) {
        requireNonNull(player, "player");
        scoreboardMap.remove(player.getUniqueId());
    }

    public Map<UUID, Scoreboard> getScoreboardMap() {
        return scoreboardMap;
    }
}
