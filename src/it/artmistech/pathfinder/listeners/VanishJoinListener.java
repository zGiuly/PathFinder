package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.VanishCommand;
import it.artmistech.pathfinder.utils.VanishUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishJoinListener extends AbstractListener {
    public VanishJoinListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void vanishJoin(PlayerJoinEvent event) {
        if(VanishCommand.getVanishPlayers().contains(event.getPlayer().getName())) {
            VanishUtils.setInvisible(getPathFinder(), event.getPlayer());
        }
    }
}
