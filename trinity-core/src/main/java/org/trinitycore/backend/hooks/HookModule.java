package org.trinitycore.backend.hooks;

import com.google.inject.AbstractModule;

/**
 * Managed by romain on 28/09/2014.
 */
public class HookModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LevelHook.class).asEagerSingleton();
        bind(TrinityHook.class).asEagerSingleton();
    }
}
