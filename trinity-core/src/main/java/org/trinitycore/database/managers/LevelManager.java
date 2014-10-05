package org.trinitycore.database.managers;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.trinity.api.database.model.Query;
import org.trinity.commons.sql.DefaultDlaoQueryManager;
import org.trinity.commons.sql.model.DefaultQueryModel;
import org.trinitycore.hooks.LevelHook;
import org.trinitycore.hooks.TrinityHook;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Return on 03/09/2014.
 */
public class LevelManager extends DefaultDlaoQueryManager {
    @Inject
    LevelHook hook;
    @Inject
    TrinityHook trinity;

    public LevelManager() {
        super(new DefaultQueryModel<>("levels", new LevelHook.Level()).schematize());
    }

    @Override
    public void loadAll() {
        try {
            Query[] queries = createNewQueries();
            /** level -> stepExp **/
            Map<Integer, Long> levels = new TreeMap<>();

            for(Query query: queries)
                levels.put((int) query.getData().get("level"), (long) query.getData().get("experience"));

            for(Map.Entry<Integer, Long> entry: levels.entrySet()) {
                int level = entry.getKey();
                long stepExperience = entry.getValue();

                LevelHook.Level previous = hook.getLevels().get(level-1);
                long experience = stepExperience + (previous != null ? previous.getExperience() : 0);

                if(level != 1 && experience == 0)
                    for(int i = 1; i<level; i++)
                        experience += levels.get(i);
                hook.addNewLevel(new LevelHook.Level(level, experience, stepExperience));
            }
        } catch (SQLException exception) {
            trinity.getLogger().warning(exception.getMessage());
        }
    }
}
