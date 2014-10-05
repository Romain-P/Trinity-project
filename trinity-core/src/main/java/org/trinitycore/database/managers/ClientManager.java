package org.trinitycore.database.managers;

import com.google.inject.Inject;
import org.trinity.api.database.model.Query;
import org.trinity.api.database.model.enums.OnlyExecuteQueryEnum;
import org.trinity.commons.sql.DefaultDaoQueryManager;
import org.trinity.commons.sql.model.DefaultQueryModel;
import org.trinitycore.frontend.entities.Client;
import org.trinitycore.backend.hooks.TrinityHook;

import java.sql.SQLException;

/**
 * Managed by romain on 05/10/2014.
 */
public class ClientManager extends DefaultDaoQueryManager<Client> {
    @Inject
    TrinityHook trinity;

    public ClientManager() {
        super(new DefaultQueryModel<>("clients", new Client()).schematize());
    }

    @Override
    public boolean create(Client obj) {
        try {
            Query query = model.createNewQuery()
                    .setData("name", obj.getName())
                    .setData("level", obj.getLevel())
                    .setData("experience", obj.getExperience())
                    .setData("money", obj.getMoney())
                    .setData("skillPoints", obj.getSkillPoints())
                    .setData("pvmDeaths", obj.getPvmDeaths())
                    .setData("pvmWins", obj.getPvmWins())
                    .setData("pvpDeaths", obj.getPvpDeaths())
                    .setData("pvpWins", obj.getPvpWins())
                    .setData("pvmRatio", obj.getPvmRatio())
                    .setData("pvpRatio", obj.getPvpRatio());
            execute(query, OnlyExecuteQueryEnum.CREATE);
        } catch (Exception exception) {
            trinity.getLogger().warning(exception.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public boolean delete(Client obj) {
        try {
            execute(model, obj.getName(), OnlyExecuteQueryEnum.DELETE);
        } catch (Exception exception) {
            trinity.getLogger().warning(exception.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public boolean update(Client obj) {
        try {
            Query query = model.createNewQuery()
                    .setData("name", obj.getName())
                    .setData("level", obj.getLevel())
                    .setData("experience", obj.getExperience())
                    .setData("money", obj.getMoney())
                    .setData("skillPoints", obj.getSkillPoints())
                    .setData("pvmDeaths", obj.getPvmDeaths())
                    .setData("pvmWins", obj.getPvmWins())
                    .setData("pvpDeaths", obj.getPvpDeaths())
                    .setData("pvpWins", obj.getPvpWins())
                    .setData("pvmRatio", obj.getPvmRatio())
                    .setData("pvpRatio", obj.getPvpRatio());
            execute(query, OnlyExecuteQueryEnum.UPDATE);
        } catch (Exception exception) {
            trinity.getLogger().warning(exception.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public Client load(Object primary) {
        try {
            Query query = createNewQuery(primary);
            return new Client(
                    (String) query.getData().get("name"),
                    (long) query.getData().get("level"),
                    (long) query.getData().get("experience"),
                    (long) query.getData().get("money"),
                    (long) query.getData().get("skillPoints"),
                    (int) query.getData().get("pvmDeaths"),
                    (int) query.getData().get("pvmWins"),
                    (int) query.getData().get("pvpDeaths"),
                    (int) query.getData().get("pvpWins"));
        } catch (SQLException exception) {
            trinity.getLogger().warning(exception.getMessage());
            return null;
        }
    }
}
