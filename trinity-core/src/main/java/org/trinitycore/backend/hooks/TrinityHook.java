package org.trinitycore.backend.hooks;

import com.google.inject.Inject;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.trinity.api.database.DaoQueryManager;
import org.trinity.api.database.DatabaseService;
import org.trinitycore.database.managers.ClientManager;
import org.trinitycore.database.managers.LevelManager;
import org.trinitycore.frontend.entities.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Managed by romain on 28/09/2014.
 */
public class TrinityHook {
    @Getter
    private final Logger logger;
    @Getter
    private final Server server;
    @Getter
    private final Map<String, Client> clients;
    @Getter
    private final DaoQueryManager<Client> clientManager;
    @Getter
    private final DaoQueryManager<LevelHook.Level> levelManager;

    @Inject
    public TrinityHook(JavaPlugin plugin, DatabaseService database) {
        this.logger = plugin.getLogger();
        this.server = plugin.getServer();
        this.clients = new HashMap<>();
        this.clientManager = (DaoQueryManager<Client>) database.getQueryManagers().get(ClientManager.class);
        this.levelManager = (DaoQueryManager<LevelHook.Level>) database.getQueryManagers().get(LevelManager.class);
    }
}
