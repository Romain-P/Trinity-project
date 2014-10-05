package org.trinitycore.database;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.trinity.api.database.DAO;
import org.trinity.api.database.DaoQueryManager;
import org.trinity.api.database.DatabaseService;
import org.trinity.api.database.DlaoQueryManager;
import org.trinitycore.hooks.LevelHook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Return on 03/09/2014.
 */
public class TrinityDatabaseService implements DatabaseService {
    @Getter
    private final ReentrantLock locker;
    @Getter
    private Connection connection;
    @Getter
    private Map<Class, DaoQueryManager> queryManagers;
    @Getter
    private Map<Class, DlaoQueryManager> loadManagers;

    @Inject
    Config config;
    @Inject
    Set<DaoQueryManager> managers;
    @Inject
    Set<DlaoQueryManager> lManagers;
    @Inject
    LevelHook hook;

    public TrinityDatabaseService() {
        this.locker = new ReentrantLock();
        this.queryManagers = new HashMap<>();
        this.loadManagers = new HashMap<>();
    }

    public TrinityDatabaseService start() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://"+
                        config.getString("trinity.database.host")+"/"+
                        config.getString("trinity.database.name"),
                        config.getString("trinity.database.user"),
                        config.getString("trinity.database.pass")
        );
        if (!connection.isValid(1000)) return null;
        connection.setAutoCommit(true);
        /** static data **/
        for(DlaoQueryManager manager: lManagers) {
            loadManagers.put(manager.getClass(), manager);
            manager.loadAll();
        }

        /** dynamic data **/
        for(DaoQueryManager manager: managers)
            queryManagers.put(manager.getClass(), manager);

        return this;
    }

    public void stop() {
        try {
            if(connection != null && !connection.isClosed())
                connection.close();
        } catch(SQLException exception){}
    }
}
