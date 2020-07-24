package org.selyu.ui.scoreboard.objective;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.animation.AnimatedText;

import java.util.ArrayList;
import java.util.List;

public final class ScoreboardObjective extends AnimatedText {
    public ScoreboardObjective(@NotNull String... frames) {
        super(frames);
    }

    public static ListBuilder builder() {
        return new ListBuilder();
    }

    // From https://github.com/selyu/Tablist/blob/master/api/src/main/java/xyz/selyu/tablist/util/TabUtil.java#L65
    private static String[] splitText(String text) {
        if (text.length() > 16) {
            String prefix = text.substring(0, 16);
            String suffix;

            if (prefix.charAt(15) == ChatColor.COLOR_CHAR || prefix.charAt(15) == '&') {
                prefix = prefix.substring(0, 15);
                suffix = text.substring(15);
            } else if (prefix.charAt(14) == ChatColor.COLOR_CHAR || prefix.charAt(14) == '&') {
                prefix = prefix.substring(0, 14);
                suffix = text.substring(14);
            } else {
                suffix = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', prefix)) + text.substring(16);
            }

            if (suffix.length() > 16)
                suffix = suffix.substring(0, 16);

            return new String[]{prefix, suffix};
        }

        return new String[]{text, ""};
    }

    public @NotNull String[] getCurrentFramePrefixSuffix() {
        return splitText(getCurrentFrame());
    }

    public static final class ListBuilder {
        private final List<ScoreboardObjective> objectives = new ArrayList<>();

        private ListBuilder() {
        }

        public ListBuilder addObjective(@NotNull String... frames) {
            return addObjective(true, -1, frames);
        }

        public ListBuilder addObjective(boolean color, @NotNull String... frames) {
            return addObjective(color, -1, frames);
        }

        public ListBuilder addObjective(int updateSpeed, @NotNull String... frames) {
            return addObjective(true, updateSpeed, frames);
        }

        public ListBuilder addObjective(boolean color, int updateSpeed, @NotNull String... frames) {
            if (color) {
                for (int idx = 0; idx < frames.length; idx++) {
                    frames[idx] = ChatColor.translateAlternateColorCodes('&', frames[idx]);
                }
            }

            ScoreboardObjective scoreboardObjective = new ScoreboardObjective(frames);
            scoreboardObjective.setUpdateSpeed(updateSpeed);
            objectives.add(scoreboardObjective);
            return this;
        }

        public @NotNull List<ScoreboardObjective> build() {
            return objectives;
        }
    }
}
