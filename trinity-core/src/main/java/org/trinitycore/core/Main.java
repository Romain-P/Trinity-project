package org.trinitycore.core;

import com.google.inject.Guice;
import com.google.inject.Inject;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.trinity.api.controllers.Controller;
import org.trinity.api.database.DatabaseService;
import org.trinitycore.database.DatabaseModule;
import org.trinitycore.backend.hooks.HookModule;
import org.trinitycore.frontend.FrontendModule;

import java.sql.SQLException;
import java.util.Set;

/**
 * Managed by romain on 28/09/2014.
 */
public class Main extends JavaPlugin {
    @Inject
    DatabaseService database;

    @Inject
    Set<Controller> controllers;

    public void onEnable() {
        getLogger().info("initializing guice injector..");
        Guice.createInjector(new MainModule(this), new DatabaseModule(), new HookModule(), new FrontendModule());

        getLogger().info("initializing database..");
        try {
            database.start();
        } catch (SQLException e) {
            getLogger().warning("can't connect to database, please check your sql account");
            getLogger().warning("can't run, reload the server");
        }

        getLogger().info("initializing controllers..");
        for(Controller controller: controllers) controller.start();

        getLogger().info("plugin is now enabled");
    }

    public void onDisable() {
        getLogger().info("stopping database..");
        database.stop();

        getLogger().info("stopping controllers..");
        for(Controller controller: controllers)
            controller.stop();
        HandlerList.unregisterAll(this);

        getLogger().info("plugin is now disabled");
    }
}
