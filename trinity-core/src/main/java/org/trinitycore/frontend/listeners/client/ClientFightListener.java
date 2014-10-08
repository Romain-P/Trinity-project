package org.trinitycore.frontend.listeners.client;

import com.google.inject.Inject;
import com.typesafe.config.Config;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.trinity.api.bukkit.ImprovedListener;
import org.trinitycore.backend.hooks.TrinityHook;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Managed by romain on 07/10/2014.
 */
public class ClientFightListener extends ImprovedListener {
    private final ReentrantLock locker;
    private final Map<UUID, Fight> fights;
    private final Map<String, Fighter> fighters;

    public ClientFightListener() {
        this.locker = new ReentrantLock();
        this.fights = new HashMap<>();
        this.fighters = new HashMap<>();
    }

    @Inject
    TrinityHook trinity;
    @Inject
    Config config;

    @EventHandler
    public void onEntityAttacked(EntityDamageByEntityEvent event) {
        locker.lock();

        try {
            Entity attacked = event.getEntity();
            Entity attacker = event.getDamager();

            if (!(attacker instanceof Player && !(attacked instanceof Player))) return;

            Player player = (Player) attacker;

            Fight fight = fights.get(attacked.getUniqueId());
            if (fight == null) fights.put(attacked.getUniqueId(), (fight = new Fight(attacked)));

            if (fight.getAttackers().contains(attacker)) {
                fighters.get(player.getName()).getFights().put(attacked.getUniqueId(), System.currentTimeMillis());
                return;
            }

            fight.addAttacker(player);

            Fighter fighter = fighters.get(player.getName());
            if (fighter == null) fighters.put(player.getName(), (fighter = new Fighter(player)));

            if (!fighter.getFights().containsKey(fight.getMonster().getUniqueId()))
                fighter.addFight(fight);
        } finally {
            locker.unlock();
        }
    }

    @EventHandler
    public void onEntityDie(EntityDeathEvent event) {
        locker.lock();

        try {
            Player player = get(event);
            if(player != null) {
                pullPlayer(player);
                return;
            }
            Entity monster = event.getEntity();
            Fight fight = fights.get(monster.getUniqueId());

            if(fight == null) return;

            for(Player attacker: fight.getAttackers()) {
                long lastHit = fighters.get(attacker.getName()).getFights().get(monster.getUniqueId());

                if(System.currentTimeMillis() - lastHit <= config.getInt("trinity.server.fights.limit-time")*1000) {
                    long exp = fight.getAttackers().size() == 1 ? event.getDroppedExp() : event.getDroppedExp() * 70 / 100;
                    trinity.getClients().get(attacker.getName()).addExperience(exp*config.getInt("trinity.server.rates.pvm"));
                }

                fight.delAttacker(attacker);
                Fighter fighter = fighters.get(attacker.getName());
                fighter.delFight(fight);
                fights.remove(monster.getUniqueId());

                if(fighter.getFights().isEmpty()) fighters.remove(attacker.getName());
            }
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void pullPlayer(Player player) {
        locker.lock();
        try {
            for (UUID fightId : fighters.get(player.getName()).getFights().keySet()) {
                Fight fight = fights.get(fightId);
                fight.delAttacker(player);
                if (fight.getAttackers().isEmpty())
                    fights.remove(fight.getMonster().getUniqueId());
            }
            fighters.remove(player.getName());
        } finally {
            locker.unlock();
        }
    }

    private class Fight {
        @Getter
        private final Entity monster;
        @Getter
        private final List<Player> attackers;

        public Fight(Entity monster) {
            this.monster = monster;
            this.attackers = new ArrayList<>();
        }

        public void addAttacker(Player player) {
            this.attackers.add(player);
        }

        public void delAttacker(Player player) {
            this.attackers.remove(player);
        }
    }

    private class Fighter {
        @Getter
        private final Player player;
        @Getter
        private final Map<UUID, Long> fights;

        public Fighter(Player player) {
            this.player = player;
            this.fights = new HashMap<>();
        }

        public void addFight(Fight fight) {
            this.fights.put(fight.getMonster().getUniqueId(), System.currentTimeMillis());
        }

        public void delFight(Fight fight) {
            this.fights.remove(fight.getMonster().getUniqueId());
        }
    }
}
