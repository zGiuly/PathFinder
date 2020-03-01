package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.FollowCommand;
import it.artmistech.pathfinder.commands.staff.FreezeCommand;
import it.artmistech.pathfinder.commands.staff.StaffChatCommand;
import it.artmistech.pathfinder.commands.teleport.TpaCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;
import java.util.Map;

public class PlayerDisconnectListener extends AbstractListener {
    public PlayerDisconnectListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void playerDisconnect(PlayerQuitEvent event) {
        for (Map.Entry<String, String> stringStringEntry : FollowCommand.getPassengers().entrySet()) {
            Map.Entry item = (Map.Entry) stringStringEntry;

            if (item.getValue().equals(event.getPlayer().getName())) {
                FollowCommand.getPassengers().remove(item.getKey());
                break;
            }
        }

        disconnectStaffChat(event.getPlayer().getName());
        disconnectControlledPlayer(event.getPlayer().getName());
        disconnectTpa(event.getPlayer().getName());
    }

    private void disconnectStaffChat(String name) {
        StaffChatCommand.getStaffChat().remove(name);
    }

    private void disconnectControlledPlayer(String name) {
        FreezeCommand.getFreezedPlayers().remove(name);
    }

    private void disconnectTpa(String name) {
        TpaCommand.getInProgress().remove(name);
        TpaCommand.getCooldown().remove(name);
    }
}
