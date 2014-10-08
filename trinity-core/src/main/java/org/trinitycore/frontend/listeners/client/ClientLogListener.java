package org.trinitycore.frontend.listeners.client;

import com.google.inject.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.trinity.api.bukkit.ImprovedListener;
import org.trinity.api.controllers.Controller;
import org.trinity.api.controllers.PullableController;
import org.trinity.api.database.DaoQueryManager;
import org.trinitycore.backend.hooks.TrinityHook;
import org.trinitycore.frontend.entities.Client;

import java.util.Set;

/**
 * Managed by romain on 07/10/2014.
 */
public class ClientLogListener extends ImprovedListener {
    private final DaoQueryManager<Client> manager;

    @Inject
    TrinityHook trinity;
    @Inject
    Set<PullableController> controllers;

    public ClientLogListener() {
        this.manager = trinity.getClientManager();
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        Player player = get(event);
        Client client = manager.load(player.getName());

        if(client == null)
            client = new Client(player.getName()).create();

        trinity.getClients().put(player.getName(), client);
        player.sendMessage("Bienvenue sur Trinity");
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = get(event);
        Client client = trinity.getClients().get(player);

        for(PullableController controller: controllers)
            controller.pullPlayer(player);

        client.save();
        trinity.getClients().remove(client.getName());
    }
}
