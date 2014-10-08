package org.trinity.api.controllers;

import com.google.common.collect.ImmutableList;
import org.trinity.api.bukkit.ImprovedListener;

/**
 * Managed by romain on 07/10/2014.
 */
public interface Controller {
    ImmutableList<ImprovedListener> getListeners();
    void start();
    void stop();
}
