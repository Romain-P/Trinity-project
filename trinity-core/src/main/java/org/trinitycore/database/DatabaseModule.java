package org.trinitycore.database;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.trinity.api.database.DaoQueryManager;
import org.trinity.api.database.DatabaseService;
import org.trinitycore.database.example.ExampleManager;

/**
 * Created by Return on 03/09/2014.
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseService.class).to(LoginDatabaseService.class).asEagerSingleton();
        Multibinder<DaoQueryManager> managers = Multibinder.newSetBinder(binder(), DaoQueryManager.class);
        managers.addBinding().to(ExampleManager.class);
    }
}
