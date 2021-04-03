package org.selyu.ui.scoreboard.task;

import org.selyu.ui.scoreboard.Scoreboard;
import org.selyu.ui.scoreboard.model.ScoreboardEntry;
import org.selyu.ui.scoreboard.model.ScoreboardLine;
import org.selyu.ui.scoreboard.model.ScoreboardTitle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class ScoreboardTask implements Runnable {
    private final Map<UUID, Scoreboard> scoreboardMap;

    public ScoreboardTask(Map<UUID, Scoreboard> scoreboardMap) {
        requireNonNull(scoreboardMap, "scoreboardMap");
        this.scoreboardMap = scoreboardMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            scoreboardMap.forEach((owner, scoreboard) -> {
                requireNonNull(scoreboard.getAdapter().getTitle(), "title");
                requireNonNull(scoreboard.getAdapter().getLines(), "lines");

                List<ScoreboardLine> adapterLines = scoreboard.getAdapter().getLines();
                Collections.reverse(adapterLines);

                // Remove excess entries
                if (scoreboard.getScoreboardEntryList().size() > adapterLines.size()) {
                    for (int i = adapterLines.size(); i < scoreboard.getScoreboardEntryList().size(); i++) {
                        scoreboard.removeEntry(i);
                    }
                }

                // The "lastTitle" is used for keeping track of the frame counter
                // So every time the lastTitle frames doesn't match the frames given by the adapter it should reset
                if (scoreboard.getLastTitle() == null || !scoreboard.getLastTitle().getFrames().equals(scoreboard.getAdapter().getTitle().getFrames())) {
                    scoreboard.setLastTitle(scoreboard.getAdapter().getTitle());
                }

                ScoreboardTitle title = scoreboard.getLastTitle();

                // No need to update if nothing is changed
                if (!scoreboard.getBukkitObjective().displayName().equals(title.getCurrentFrame())) {
                    scoreboard.getBukkitObjective().displayName(title.getCurrentFrame());
                }

                title.nextFrame();

                for (int i = 0; i < adapterLines.size(); i++) {
                    ScoreboardEntry scoreboardEntry = scoreboard.getEntry(i);
                    ScoreboardLine adapterLine = adapterLines.get(i);

                    if (scoreboardEntry == null) {
                        scoreboardEntry = new ScoreboardEntry(scoreboard, adapterLine);
                        scoreboard.addEntry(scoreboardEntry);
                    }

                    // Check if the adapterLine has been updated
                    if (!adapterLine.getFrames().equals(scoreboardEntry.getLine().getFrames())) {
                        scoreboardEntry.setLine(adapterLine);
                    }

                    // No need to update if nothing has changed
                    if (!scoreboardEntry.getBukkitTeam().prefix().equals(scoreboardEntry.getLine().getCurrentFrame())) {
                        scoreboardEntry.getBukkitTeam().prefix(scoreboardEntry.getLine().getCurrentFrame());
                    }

                    scoreboardEntry.getLine().nextFrame();
                    scoreboard.getBukkitObjective().getScore(scoreboardEntry.getTeamName()).setScore(i);
                }
            });
        } catch (Exception e) {
            System.out.println("Caught exception running ScoreboardTask: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
