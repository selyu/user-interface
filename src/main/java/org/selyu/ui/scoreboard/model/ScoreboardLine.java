package org.selyu.ui.scoreboard.model;

import net.kyori.adventure.text.Component;
import org.selyu.ui.model.AnimatedText;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class ScoreboardLine extends AnimatedText {
    public ScoreboardLine(int updateSpeed, Component... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    public ScoreboardLine(int updateSpeed, String... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<ScoreboardLine> lines = new ArrayList<>();

        private Builder() {
        }

        public Builder addLine(String... frames) {
            return addLine(-1, frames);
        }

        public Builder addLine(Component... frames) {
            return addLine(-1, frames);
        }

        public Builder addLine(int updateSpeed, String... frames) {
            requireNonNull(frames, "frames");
            lines.add(new ScoreboardLine(updateSpeed, frames));
            return this;
        }

        public Builder addLine(int updateSpeed, Component... frames) {
            requireNonNull(frames, "frames");
            lines.add(new ScoreboardLine(updateSpeed, frames));
            return this;
        }

        public List<ScoreboardLine> build() {
            return lines;
        }
    }
}
