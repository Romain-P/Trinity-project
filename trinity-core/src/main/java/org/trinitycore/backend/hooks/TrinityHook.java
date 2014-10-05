package org.trinitycore.backend.hooks;

import com.google.inject.Inject;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Managed by romain on 28/09/2014.
 */
public class TrinityHook {
    @Getter
    private final Logger logger;

    @Inject
    public TrinityHook(JavaPlugin plugin) {
        this.logger = plugin.getLogger();
    }
}
