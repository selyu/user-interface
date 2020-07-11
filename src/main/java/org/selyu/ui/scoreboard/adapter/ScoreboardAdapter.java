package org.selyu.ui.scoreboard.adapter;

import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;

import java.util.List;

public interface ScoreboardAdapter {
    /**
     * @return Title of the scoreboard
     */
    @NotNull ScoreboardTitle getTitle();

    /**
     * @return Objectives parsed and displayed to the player
     */
    @NotNull List<ScoreboardObjective> getObjectives();
}
