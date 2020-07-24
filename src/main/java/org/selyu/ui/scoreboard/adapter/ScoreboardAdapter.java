package org.selyu.ui.scoreboard.adapter;

import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;

import java.util.List;

public interface ScoreboardAdapter {
    /**
     * @return Title of the scoreboard
     */
    ScoreboardTitle getTitle();

    /**
     * @return Objectives parsed and displayed to the player
     */
    List<ScoreboardObjective> getObjectives();
}
