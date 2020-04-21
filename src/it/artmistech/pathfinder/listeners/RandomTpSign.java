package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class RandomTpSign extends AbstractListener {
    public RandomTpSign(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void RandomTpSignReplace(SignChangeEvent event) {

    }

    @EventHandler
    public void RandomTpSignInteract(PlayerInteractEvent event) {

    }
}
