package org.selyu.ui.model;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class AnimatedText {
    public static int globalUpdateSpeed = 3;

    protected final List<Component> frames = new ArrayList<>();
    protected int frameCount = 0;
    protected int currentFrameIdx = 0;
    private int updateSpeed = -1;

    public AnimatedText(Component... frames) {
        requireNonNull(frames, "frames");
        this.frames.addAll(Arrays.asList(frames));
    }

    public AnimatedText(String... frames) {
        this(Arrays.stream(frames).map(LegacyComponentSerializer.legacyAmpersand()::deserialize).toArray(Component[]::new));
    }

    public final void addFrame(Component frame) {
        requireNonNull(frame, "frame");
        frames.add(frame);
    }

    public final void addFrame(String frame) {
        requireNonNull(frame, "frame");
        frames.add(LegacyComponentSerializer.legacyAmpersand().deserialize(frame));
    }

    public final void nextFrame() {
        if (frames.size() > 1) {
            frameCount++;
            if (frameCount % (updateSpeed == -1 ? globalUpdateSpeed : updateSpeed) == 0) {
                currentFrameIdx++;
            }
        }
    }

    public final Component getCurrentFrame() {
        if (currentFrameIdx >= frames.size())
            currentFrameIdx = 0;
        return frames.get(currentFrameIdx);
    }

    public final List<Component> getFrames() {
        return frames;
    }

    public final int getFrameCount() {
        return frameCount;
    }

    public final int getCurrentFrameIdx() {
        return currentFrameIdx;
    }

    public final int getUpdateSpeed() {
        return updateSpeed;
    }

    public final void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }
}
