package org.trinitycore.frontend.entities;

import com.google.inject.Inject;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.trinity.api.database.DaoQueryManager;
import org.trinity.api.database.model.annotations.PrimaryQueryField;
import org.trinity.api.database.model.annotations.QueryField;
import org.trinitycore.backend.hooks.LevelHook;
import org.trinitycore.backend.hooks.TrinityHook;
import org.trinitycore.database.TrinityDatabaseService;
import org.trinitycore.database.managers.ClientManager;

/**
 * Managed by romain on 30/09/2014.
 */
public class Client {
    @Getter
    @PrimaryQueryField
    private final String name;
    @Getter
    @QueryField
    private long level, experience, money, skillPoints;
    @Getter
    @QueryField
    private int pvmDeaths, pvmWins, pvpDeaths, pvpWins;
    @QueryField
    private double pvmRatio, pvpRatio;
    @Getter
    private Player player;
    private DaoQueryManager<Client> manager;

    @Inject
    LevelHook hook;
    @Inject
    TrinityDatabaseService database;
    @Inject
    TrinityHook trinity;

    public Client() {this(null);}

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, long level, long experience, long money, long skillPoints, int pvmDeaths, int pvmWins, int pvpDeaths, int pvpWins) {
        this.name = name;
        this.level = level;
        this.experience = experience;
        this.money = money;
        this.skillPoints = skillPoints;
        this.pvmDeaths = pvmDeaths;
        this.pvmWins = pvmWins;
        this.pvpDeaths = pvpDeaths;
        this.pvpWins = pvpWins;

        manager = (DaoQueryManager<Client>) database.getQueryManagers().get(ClientManager.class);
    }

    public void setLevel(long level) {
        this.level = level;
        this.experience = hook.getExperienceNeededTo(level);
    }

    public void addExperience(long experience) {
        final long newExp = this.experience + experience;

        long lvlExp;
        while((lvlExp = hook.getExperienceNeededTo(level+1)) != -1 && newExp >= lvlExp)
            level++;

        this.experience = newExp;
    }

    public void addMoney(long money) {
        this.money += money;
    }

    public void delMoney(long money) {
        this.money -= money;
    }

    public void addSkillPoint(long points) {
        this.skillPoints += points;
    }

    public void delSkillPoint(long points) {
        this.skillPoints -= points;
    }

    public void addPvmDeath() {
        this.pvmDeaths++;
    }

    public void addPvmWin() {
        this.pvmWins++;
    }

    public void addPvpDeath() {
        this.pvpDeaths++;
    }

    public void addPvpWin() {
        this.pvpWins++;
    }

    public double getPvmRatio() {
        return (pvmRatio = Math.floor((pvmWins/pvmDeaths) * 100)) / 100;
    }

    public double getPvpRatio() {
        return (pvpRatio = Math.floor((pvpWins/pvpDeaths) * 100)) / 100;
    }

    public Player getPlayer() {
        return player != null ? player : (player = trinity.getServer().getPlayer(name));
    }

    public void save() {
        manager.update(this);
    }

    public Client create() {
        manager.create(this);
        return this;
    }

    public void remove() {
        manager.delete(this);
    }
}
