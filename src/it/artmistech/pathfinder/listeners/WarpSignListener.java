package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WarpSignListener extends AbstractListener {
    public WarpSignListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void signNameReplace(SignChangeEvent event) {
        if(!event.getPlayer().hasPermission("pathfinder.sign.warp.create")) return;

        if(event.getLine(0).equalsIgnoreCase("[Warp]") && event.getLines().length == 2) {
            
        }
    }

    @EventHandler
    public void signInteract(PlayerInteractEvent event) {

    }
}
