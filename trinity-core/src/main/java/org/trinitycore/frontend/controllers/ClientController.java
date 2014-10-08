package org.trinitycore.frontend.controllers;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.trinity.api.bukkit.ImprovedListener;
import org.trinity.api.controllers.PullableController;
import org.trinitycore.frontend.listeners.client.ClientFightListener;
import org.trinitycore.frontend.listeners.client.ClientLogListener;

/**
 * Managed by romain on 07/10/2014.
 */
public class ClientController extends PullableController{
    @Getter
    private final ImmutableList<ImprovedListener> listeners;

    @Inject
    JavaPlugin plugin;
    @Inject
    Injector injector;

    public ClientController(ImprovedListener... listeners) {
        this.listeners = ImmutableList.copyOf(listeners);
    }

    @Override
    public void start() {
        for(Listener listener: listeners) {
            injector.injectMembers(listener);
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
        plugin.getLogger().info(String.format("controller %s started", getClass().getName()));
    }

    @Override
    public void stop() {
        /**
        for(Listener listener: listeners) {
            for(Method method: listener.getClass().getDeclaredMethods()) {
                for (Class<?> parameter : method.getParameterTypes()) {
                    if (parameter.getSuperclass() == Event.class) {
                        try {
                            HandlerList list = (HandlerList) parameter.getMethod("getHandlerList", HandlerList.class).getDefaultValue();
                            list.unregister(listener);
                        } catch (NoSuchMethodException e) {}
                    }
                }
            }
        } **/
        plugin.getLogger().info(String.format("controller %s stopped", getClass().getName()));
    }

    @Override
    public void pullPlayer(Player player) {
        for(ImprovedListener listener: listeners)
            listener.pullPlayer(player);
    }

    public static ClientController create() {
        return new ClientController(new ClientFightListener(), new ClientLogListener());
    }
}
