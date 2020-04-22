package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class IgnoreCommand extends AbstractCommand {
    private static HashMap<String, List<String>> ignoredUsers;

    public IgnoreCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "ignore");
        ignoredUsers = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.ignore")) return;

        Player player = (Player) sender;

        if (strings.length == 1) {
            if (strings[0].equals(player.getName())) return;

            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            if(!ignoredUsers.containsKey(player.getName())) {
                ignoredUsers.put(player.getName(), new ArrayList<>());
            }

            if(ignoredUsers.get(player.getName()).contains(strings[0])) {
                ignoredUsers.get(player.getName()).remove(strings[0]);
                player.sendMessage("§aNow you ignore " + strings[0]);
            } else {
                ignoredUsers.get(player.getName()).add(strings[0]);
                player.sendMessage("§aNow you not ignore " + strings[0]);
            }
        }
    }

    public static HashMap<String, List<String>> getIgnoredUsers() {
        return ignoredUsers;
    }
}
