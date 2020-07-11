package org.selyu.ui.scoreboard.task;

import org.jetbrains.annotations.NotNull;
import org.selyu.ui.scoreboard.Scoreboard;
import org.selyu.ui.scoreboard.entry.ScoreboardEntry;
import org.selyu.ui.scoreboard.objective.ScoreboardObjective;
import org.selyu.ui.scoreboard.title.ScoreboardTitle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class ScoreboardTask implements Runnable {
    private final Map<UUID, Scoreboard> scoreboardMap;

    public ScoreboardTask(@NotNull Map<UUID, Scoreboard> scoreboardMap) {
        this.scoreboardMap = scoreboardMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        scoreboardMap.forEach((owner, scoreboard) -> {
            List<ScoreboardObjective> objectives = scoreboard.getAdapter().getObjectives();
            Collections.reverse(objectives);

            // Remove excess entries
            if (scoreboard.getScoreboardEntryList().size() > objectives.size()) {
                for (int idx = objectives.size(); idx < scoreboard.getScoreboardEntryList().size(); idx++) {
                    scoreboard.removeEntry(idx);
                }
            }

            for (int idx = 0; idx < objectives.size(); idx++) {
                ScoreboardEntry scoreboardEntry = scoreboard.getEntry(idx);
                ScoreboardObjective scoreboardObjective = objectives.get(idx);
                ScoreboardTitle scoreboardTitle = scoreboard.getAdapter().getTitle();

                if (scoreboardEntry == null) {
                    scoreboardEntry = new ScoreboardEntry(scoreboard, scoreboardObjective, scoreboardTitle);
                    scoreboard.addEntry(scoreboardEntry);
                }

                // Compare the possibly previously existing entries frame list to the one from the adapter
                // This is to keep animations actually updating instead of just being the first frame from the adapter
                if (scoreboardObjective.getFramesList().equals(scoreboardEntry.getObjective().getFramesList())) {
                    scoreboardObjective.setFrameCount(scoreboardEntry.getObjective().getFrameCount());
                    scoreboardObjective.setCurrentFrameIdx(scoreboardEntry.getObjective().getCurrentFrameIdx());
                }

                if (scoreboardTitle.getFramesList().equals(scoreboardEntry.getTitle().getFramesList())) {
                    scoreboardTitle.setFrameCount(scoreboardEntry.getTitle().getFrameCount());
                    scoreboardTitle.setCurrentFrameIdx(scoreboardEntry.getTitle().getCurrentFrameIdx());
                }

                if (!scoreboard.getBukkitObjective().getDisplayName().equals(scoreboardTitle.getCurrentFrame()))
                    scoreboard.getBukkitObjective().setDisplayName(scoreboardTitle.getCurrentFrame());

                scoreboardEntry.setObjective(scoreboardObjective);
                scoreboardEntry.setTitle(scoreboardTitle);
                scoreboardEntry.getBukkitTeam().setPrefix(scoreboardEntry.getObjective().getCurrentFramePrefixSuffix()[0]);
                scoreboardEntry.getBukkitTeam().setSuffix(scoreboardEntry.getObjective().getCurrentFramePrefixSuffix()[1]);

                scoreboard.getBukkitObjective().getScore(scoreboardEntry.getTeamName()).setScore(idx);
                scoreboardEntry.getObjective().nextFrame();
                scoreboardEntry.getTitle().nextFrame();
            }
        });
    }
}
