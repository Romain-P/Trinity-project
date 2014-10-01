package org.trinity.api.database;

import org.trinity.api.database.model.Query;

import java.sql.SQLException;

/**
 * Managed by romain on 30/09/2014.
 */
public abstract class DlaoQueryManager implements DLAO{
    protected abstract Query[] createNewQueries() throws SQLException;
}
