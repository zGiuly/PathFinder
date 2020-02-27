package it.artmistech.pathfinder.manager;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Temporarily broken
 */
@Deprecated
public class TpaManager {
    private final Map<String, String> players;
    private final Map<String, BukkitTask> playersTask;
    private final PathFinder pathFinder;

    public TpaManager(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        players = new HashMap<>();
        playersTask = new HashMap<>();
    }

    public boolean init(Player executor, Player target) {
        if(tpaInHandling(executor)) return false;

        int maxtime = pathFinder.getConfig().getInt("tpa.max-time");

        BukkitTask task = Bukkit.getScheduler().runTaskLater(pathFinder, () -> {
            String executorName = executor.getName();
            String targetName = target.getName();

            if(executor != null && target != null) {
                executor.sendMessage("§4Time exceeded for tpa");
                target.sendMessage("§4Time exceeded for tpa");
            }

            players.remove(targetName);

        }, maxtime);

        playersTask.put(target.getName(), task);
        players.put(target.getName(), executor.getName());

        target.sendMessage("§aTPA request by " + executor.getName() + " accept with /tpa accept or deny with /tpa deny");
        executor.sendMessage("§aRequest sent!");

        return true;
    }


    public boolean acceptTpa(Player player) {
        if(!tpaInHandling(player)) return false;

        playersTask.get(player.getName()).cancel();
        playersTask.remove(player.getName());
        Player target = Bukkit.getPlayerExact(players.get(player.getName()));

        if(target == null || !target.isOnline()) return false;

        CustomLocation location = CustomLocation.fromLocation(target.getLocation());

        if(!location.isSafe()) {
            players.remove(player.getName());
            return false;
        }

        player.teleport(location);
        player.sendMessage("§aTpa accepted!");
        target.sendMessage("§aTpa accepted!");

        return true;
    }

    public boolean denyTpa(Player player) {
        if(!tpaInHandling(player)) return false;

        playersTask.get(player.getName()).cancel();
        playersTask.remove(player.getName());
        Player target = Bukkit.getPlayerExact(players.get(player.getName()));
        players.remove(player.getName());

        player.sendMessage("§4Tpa denied!");
        if(target != null && target.isOnline()) {
            target.sendMessage("§4Tpa denied!");
        }

        return true;
    }

    public boolean tpaInHandling(Player player) {
        return players.containsKey(player.getName());
    }

    public Map<String, String> getPlayers() {
        return players;
    }
}
