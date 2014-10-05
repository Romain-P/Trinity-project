package org.trinitycore.hooks;

import lombok.Getter;
import org.trinity.api.database.model.annotations.PrimaryQueryField;
import org.trinity.api.database.model.annotations.QueryField;

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
        @PrimaryQueryField
        private final int level;
        @Getter
        @QueryField
        private final long experience;
        @Getter
        private final long stepExperience;

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
