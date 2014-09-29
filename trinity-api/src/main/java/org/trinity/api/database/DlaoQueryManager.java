package org.trinity.api.database;

import org.trinity.api.database.model.Query;

import java.sql.SQLException;

/**
 * Managed by romain on 30/09/2014.
 */
public abstract class DlaoQueryManager {
    protected abstract Query createNewQuery(Object primary) throws SQLException;
}
