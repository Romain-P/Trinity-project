package org.trinitycore.database;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.trinity.api.database.DaoQueryManager;
import org.trinity.api.database.DatabaseService;
import org.trinity.api.database.DlaoQueryManager;
import org.trinitycore.database.example.ExampleManager;

/**
 * Created by Return on 03/09/2014.
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseService.class).to(TrinityDatabaseService.class).asEagerSingleton();
        Multibinder<DaoQueryManager> daoManagers = Multibinder.newSetBinder(binder(), DaoQueryManager.class);
        daoManagers.addBinding().to(ExampleManager.class);

        Multibinder<DlaoQueryManager> dlaoManagers = Multibinder.newSetBinder(binder(), DlaoQueryManager.class);

    }
}
