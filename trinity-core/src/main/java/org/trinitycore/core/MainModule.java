package org.trinitycore.core;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;
import org.trinitycore.hooks.LevelHook;
import org.trinitycore.hooks.TrinityHook;

import java.io.File;
import java.util.logging.Logger;

/**
 * Managed by romain on 28/09/2014.
 */
public class MainModule extends AbstractModule {
    private JavaPlugin plugin;

    @Inject TrinityHook trinity;

    public MainModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected void configure() {
        Config config = ConfigFactory.parseFile(new File("trinity.conf"));
        if(config.isEmpty()) {
            trinity.getLogger().warning("Failed to load trinity.conf");
            System.exit(1);
        }
        bind(Config.class).toInstance(config);
        bind(JavaPlugin.class).toInstance(plugin);
    }
}
