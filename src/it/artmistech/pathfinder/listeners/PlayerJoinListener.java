package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.utils.PathFinderUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends AbstractListener {
    public PlayerJoinListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void changeNickname(PlayerJoinEvent event) {
        String newName = PathFinderUtils.extractNewNameFromDatabase(getPathFinder().getDefaultDatabase(), event.getPlayer().getName());

        if(newName == null) return;

        event.getPlayer().setDisplayName(newName);
        event.getPlayer().setCustomName(newName);
        event.getPlayer().setPlayerListName(newName);
    }


    @EventHandler
    public void setBalance(PlayerJoinEvent event) {
        if(getEconomy().hasAccount(event.getPlayer())) return;

        getEconomy().createPlayerAccount(event.getPlayer());
        getEconomy().depositPlayer(event.getPlayer(), configDouble("economy.start-balance"));
    }
}
