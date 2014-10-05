package org.trinitycore.core;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;
import org.trinity.api.database.DatabaseService;
import org.trinitycore.database.DatabaseModule;
import org.trinitycore.backend.hooks.HookModule;

import java.sql.SQLException;

/**
 * Managed by romain on 28/09/2014.
 */
public class Main extends JavaPlugin {
    @Inject
    DatabaseService database;

    public void onEnable() {
        getLogger().info("initializing guice injector..");
        Guice.createInjector(new MainModule(this), new DatabaseModule(), new HookModule());

        getLogger().info("initializing database..");
        try {
            database.start();
        } catch (SQLException e) {
            getLogger().warning("can't connect to database, please check your sql account");
            getLogger().warning("can't run, reload the server");
        }

        getLogger().info("plugin is now enabled");
    }

    public void onDisable() {
        getLogger().info("stopping database..");
        database.stop();

        getLogger().info("plugin is now disabled");
    }
}
