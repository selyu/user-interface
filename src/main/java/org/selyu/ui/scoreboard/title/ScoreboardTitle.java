package org.selyu.ui.scoreboard.title;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.animation.AnimatedText;

public final class ScoreboardTitle extends AnimatedText {
    private ScoreboardTitle(@NotNull String... frames) {
        super(frames);
    }

    public static @NotNull ScoreboardTitle of(@NotNull String... frames) {
        return of(true, frames);
    }

    public static @NotNull ScoreboardTitle of(boolean color, @NotNull String... frames) {
        if (color) {
            for (int idx = 0; idx < frames.length; idx++) {
                frames[idx] = ChatColor.translateAlternateColorCodes('&', frames[idx]);
            }
        }
        return new ScoreboardTitle(frames);
    }
}
