package org.selyu.ui.scoreboard.title;

import org.bukkit.ChatColor;
import org.selyu.ui.scoreboard.animation.AnimatedText;

import static java.util.Objects.requireNonNull;

public final class ScoreboardTitle extends AnimatedText {
    private ScoreboardTitle(int updateSpeed, String... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    public static ScoreboardTitle of(String... frames) {
        return of(true, -1, frames);
    }

    public static ScoreboardTitle of(boolean color, String... frames) {
        return of(color, -1, frames);
    }

    public static ScoreboardTitle of(int updateSpeed, String... frames) {
        return of(true, updateSpeed, frames);
    }

    public static ScoreboardTitle of(boolean color, int updateSpeed, String... frames) {
        requireNonNull(frames, "frames");
        if (color) {
            for (int idx = 0; idx < frames.length; idx++) {
                frames[idx] = ChatColor.translateAlternateColorCodes('&', frames[idx]);
            }
        }

        return new ScoreboardTitle(updateSpeed, frames);
    }
}
