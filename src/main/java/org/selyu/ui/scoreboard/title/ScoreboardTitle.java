package org.selyu.ui.scoreboard.title;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.animation.AnimatedText;

public final class ScoreboardTitle extends AnimatedText {
    private ScoreboardTitle(int updateSpeed, @NotNull String... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    public static @NotNull ScoreboardTitle of(@NotNull String... frames) {
        return of(true, -1, frames);
    }

    public static ScoreboardTitle of(boolean color, String... frames) {
        return of(color, -1, frames);
    }

    public static @NotNull ScoreboardTitle of(int updateSpeed, String... frames) {
        return of(true, updateSpeed, frames);
    }

    public static @NotNull ScoreboardTitle of(boolean color, int updateSpeed, @NotNull String... frames) {
        if (color) {
            for (int idx = 0; idx < frames.length; idx++) {
                frames[idx] = ChatColor.translateAlternateColorCodes('&', frames[idx]);
            }
        }

        return new ScoreboardTitle(updateSpeed, frames);
    }
}
