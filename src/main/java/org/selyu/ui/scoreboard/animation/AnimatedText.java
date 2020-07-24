package org.selyu.ui.scoreboard.animation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AnimatedText {
    public static int globalUpdateSpeed = 3;

    protected final List<String> framesList = new ArrayList<>();
    protected int frameCount = 0;
    protected int currentFrameIdx = 0;
    private int updateSpeed = -1;

    public AnimatedText(@NotNull String... frames) {
        framesList.addAll(Arrays.asList(frames));
    }

    public final void addFrame(@NotNull String frame) {
        framesList.add(frame);
    }

    public void nextFrame() {
        if (framesList.size() > 1) {
            frameCount++;
            if (frameCount % (updateSpeed == -1 ? globalUpdateSpeed : updateSpeed) == 0) {
                currentFrameIdx++;
            }
        }
    }

    public final @NotNull String getCurrentFrame() {
        if (currentFrameIdx >= framesList.size())
            currentFrameIdx = 0;
        return framesList.get(currentFrameIdx);
    }

    public final @NotNull List<String> getFramesList() {
        return framesList;
    }

    public final int getFrameCount() {
        return frameCount;
    }

    public final void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public final int getCurrentFrameIdx() {
        return currentFrameIdx;
    }

    public final void setCurrentFrameIdx(int currentFrameIdx) {
        this.currentFrameIdx = currentFrameIdx;
    }

    public int getUpdateSpeed() {
        return updateSpeed;
    }

    public void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }
}
