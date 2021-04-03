package org.selyu.ui.scoreboard.adapter;

import org.selyu.ui.scoreboard.model.ScoreboardLine;
import org.selyu.ui.scoreboard.model.ScoreboardTitle;

import java.util.List;

public interface IScoreboardAdapter {
    /**
     * @return Title of the scoreboard
     */
    ScoreboardTitle getTitle();

    /**
     * @return Scoreboard lines displayed on the scoreboard
     */
    List<ScoreboardLine> getLines();
}
