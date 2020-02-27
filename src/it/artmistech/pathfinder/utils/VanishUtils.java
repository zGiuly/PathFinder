package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.staff.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishUtils {
    public static void setInvisible(PathFinder pathFinder, Player player) {
        VanishCommand.getVanishPlayers().add(player.getName());
        player.setInvulnerable(true);
        player.setCanPickupItems(false);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!onlinePlayer.hasPermission("pathfinder.see.vanish")) {
                onlinePlayer.hidePlayer(pathFinder, player);
            }
        });
    }


    public static void setVisible(PathFinder pathFinder, Player player) {
        VanishCommand.getVanishPlayers().remove(player.getName());
        player.setInvulnerable(false);
        player.setCanPickupItems(true);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(pathFinder, player));
    }
}
