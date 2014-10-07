package org.trinity.api.controllers;

import com.google.common.collect.ImmutableList;
import org.bukkit.event.Listener;

/**
 * Managed by romain on 07/10/2014.
 */
public interface Controller {
    ImmutableList<Listener> getListeners();
    void start();
}
