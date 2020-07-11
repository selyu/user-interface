package org.selyu.ui.scoreboard.objective;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.animation.AnimatedText;

import java.util.ArrayList;
import java.util.List;

public final class ScoreboardObjective extends AnimatedText {
    public ScoreboardObjective(@NotNull String firstFrame) {
        super(firstFrame);
    }

    public static Builder builder() {
        return new Builder();
    }

    // From https://github.com/selyu/Tablist/blob/master/api/src/main/java/xyz/selyu/tablist/util/TabUtil.java#L65
    private static String[] splitText(String text) {
        if (text.length() > 16) {
            String prefix = text.substring(0, 16);
            String suffix = "";

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

    public static final class Builder {
        private final List<ScoreboardObjective> objectives = new ArrayList<>();

        private Builder() {
        }

        public Builder addObjective(@NotNull String frame) {
            return addObjective(frame, true);
        }

        public Builder addObjective(@NotNull String frame, boolean color) {
            if (color)
                frame = ChatColor.translateAlternateColorCodes('&', frame);
            objectives.add(new ScoreboardObjective(frame));
            return this;
        }

        public Builder addObjective(@NotNull String... frames) {
            return addObjective(true, frames);
        }

        public Builder addObjective(boolean color, @NotNull String... frames) {
            if (color) {
                for (int idx = 0; idx < frames.length; idx++) {
                    frames[idx] = ChatColor.translateAlternateColorCodes('&', frames[idx]);
                }
            }
            ScoreboardObjective objective = new ScoreboardObjective(frames[0]);

            for (int idx = 1; idx < frames.length; idx++) {
                objective.addFrame(frames[idx]);
            }

            objectives.add(objective);
            return this;
        }

        public @NotNull List<ScoreboardObjective> build() {
            return objectives;
        }
    }
}
