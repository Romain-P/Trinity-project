package org.trinitycore.core;

import com.google.inject.AbstractModule;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Managed by romain on 28/09/2014.
 */
public class MainModule extends AbstractModule {
    private JavaPlugin plugin;

    public MainModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);
        bind(TrinityHook.class).asEagerSingleton();
    }
}
