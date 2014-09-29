package org.trinity.commons.sql;

import com.google.inject.Inject;
import org.trinity.api.database.DatabaseService;
import org.trinity.api.database.DlaoQueryManager;
import org.trinity.api.database.model.Query;
import org.trinity.api.database.model.QueryModel;
import org.trinity.api.database.model.builders.PreparedStatementBuilder;
import org.trinity.api.database.model.builders.QueryObjectBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Managed by romain on 30/09/2014.
 */
public abstract class DefaultDlaoQueryManager extends DlaoQueryManager {
    protected QueryModel model;
    @Inject
    DatabaseService database;

    protected Query createNewQuery(Object primary) throws SQLException {
        database.getLocker().lock();

        ResultSet resultSet;
        Query query = null;
        try {
            database.getConnection().setAutoCommit(false);
            PreparedStatement statement = PreparedStatementBuilder.newLoadQuery(model, primary, database.getConnection());
            resultSet = statement.executeQuery();
            database.getConnection().commit();

            query = QueryObjectBuilder.newQuery(model, resultSet);
            resultSet.getStatement().close();
            resultSet.close();
        } catch(SQLException exception) {
            throw exception;
        } finally {
            database.getConnection().setAutoCommit(true);
            database.getLocker().unlock();
            return query;
        }
    }
}
