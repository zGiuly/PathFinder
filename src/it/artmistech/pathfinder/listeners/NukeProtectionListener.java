package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NukeProtectionListener extends AbstractListener {
    public NukeProtectionListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void explosionNuke(EntityExplodeEvent event) {
        if(!(event.getEntity() instanceof TNTPrimed)) return;

        TNTPrimed primed = (TNTPrimed)event.getEntity();

        if(!primed.getCustomName().equals("§c§lNuke")) return;

        event.setCancelled(true);

        for (Entity nearbyEntity : primed.getNearbyEntities(10, 10, 10)) {
            if(nearbyEntity instanceof LivingEntity) {
                LivingEntity entity = (LivingEntity)nearbyEntity;

                double health = entity.getHealth();

                entity.setHealth(health-event.getYield());
            }
        }
    }

}
