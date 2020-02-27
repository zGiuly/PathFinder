package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.GodModeCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodModeListener extends AbstractListener {
    public GodModeListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void godDamaged(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) return;

        if(!GodModeCommand.getUsersGodMode().contains(event.getEntity().getName())) return;

        event.setCancelled(true);
    }
}
