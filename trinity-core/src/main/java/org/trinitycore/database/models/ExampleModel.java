package org.trinitycore.database.models;

import org.trinity.commons.sql.model.DefaultQueryModel;
import org.trinitycore.database.example.Example;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Return on 03/09/2014.
 */
public class ExampleModel extends DefaultQueryModel<Example> {
    public ExampleModel() {
        super("accounts", new Example());
    }

    @Override
    public Map<String, String> getColumnModel() {
        Map<String, String> model = new HashMap<>();
        model.put("id", "guid");
        model.put("name", "account");
        model.put("objective", "other");

        return model;
    }
}
