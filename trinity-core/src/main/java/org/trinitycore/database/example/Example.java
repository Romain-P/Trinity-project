package org.trinitycore.database.example;

import com.google.inject.Inject;
import lombok.Getter;
import org.trinity.api.database.DatabaseService;
import org.trinity.api.database.model.annotations.PrimaryQueryField;
import org.trinity.api.database.model.annotations.QueryField;

/**
 * Created by Return on 03/09/2014.
 */
public class Example {
    @Getter
    @PrimaryQueryField
    private int id;
    @Getter
    @QueryField
    private String name;
    @Getter
    @QueryField
    private String objective;

    @Inject
    DatabaseService database;

    public Example() {}

    public Example(int id, String name, String objective) {
        this.id = id;
        this.name = name;
        this.objective = objective;
    }

    public void save() {
        database.getQueryManagers().get(getClass()).update(this);
    }
}
