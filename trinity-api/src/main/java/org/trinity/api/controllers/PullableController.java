package org.trinity.api.controllers;

import org.bukkit.entity.Player;
import org.trinity.api.bukkit.Pullable;

/**
 * Managed by romain on 08/10/2014.
 */
public abstract class PullableController extends Pullable implements Controller{
    public void pullPlayer(Player player) {}
}
