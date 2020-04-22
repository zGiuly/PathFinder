package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.FollowCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.Map;

public class PlayerEjectListener extends AbstractListener {
    public PlayerEjectListener(PathFinder pathFinder) {
        super(pathFinder);
    }

    @EventHandler
    public void eject(EntityDismountEvent event) {
        if(event.getDismounted() instanceof Player) {
            Player player = (Player)event.getDismounted();

            for (Map.Entry<String, String> stringStringEntry : FollowCommand.getPassengers().entrySet()) {
                Map.Entry item = (Map.Entry) stringStringEntry;

                if (item.getValue().equals(player.getName())) {
                    FollowCommand.getPassengers().remove(item.getKey());
                    break;
                }
            }
        }
    }
}
