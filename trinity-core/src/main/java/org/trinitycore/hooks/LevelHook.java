package org.trinitycore.hooks;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Managed by romain on 30/09/2014.
 */
public class LevelHook {
    /** level -> experience **/
    @Getter
    private final Map<Integer, Level> levels;

    public LevelHook() {
        this.levels = new HashMap<>();
    }

    public void addNewLevel(Level level) {
        levels.put(level.getLevel(), level);
    }

    public long getExperienceNeededTo(int level) {
        return levels.get(level).getExperience();
    }

    public long getStepExperienceAt(int level) {
        return levels.get(level).getStepExperience();
    }

    public long getMaxLevel() {
        return levels.size();
    }

    public static class Level {
        @Getter
        private final int level;
        @Getter
        private final long experience, stepExperience;

        public Level() {
            this(-1,-1,-1);
        }

        public Level(int level, long experience, long stepExperience) {
            this.level = level;
            this.experience = experience;
            this.stepExperience = stepExperience;
        }
    }
}
