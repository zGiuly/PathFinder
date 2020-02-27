package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreativeDamageListener extends AbstractListener {
    public CreativeDamageListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void creativeDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(((Player) event.getEntity()).getGameMode() != GameMode.CREATIVE) return;

            if(!configBoolean("gamemode.creative-damage-allow")) {
                event.setCancelled(true);
            }
        }
    }
}
