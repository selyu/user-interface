package org.selyu.ui.scoreboard.model;

import net.kyori.adventure.text.Component;
import org.selyu.ui.model.AnimatedText;

public final class ScoreboardTitle extends AnimatedText {
    private ScoreboardTitle(int updateSpeed, Component... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    private ScoreboardTitle(int updateSpeed, String... frames) {
        super(frames);
        setUpdateSpeed(updateSpeed);
    }

    public static ScoreboardTitle from(String... frames) {
        return new ScoreboardTitle(-1, frames);
    }

    public static ScoreboardTitle from(Component... frames) {
        return new ScoreboardTitle(-1, frames);
    }

    public static ScoreboardTitle from(int updateSpeed, String... frames) {
        return new ScoreboardTitle(updateSpeed, frames);
    }

    public static ScoreboardTitle from(int updateSpeed, Component... frames) {
        return new ScoreboardTitle(updateSpeed, frames);
    }
}
