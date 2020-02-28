package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpy extends AbstractCommand {
    private static List<String> activeUsers;
    public CommandSpy(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "commandspy");
        activeUsers = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.commandspy")) return;

        if(!activeUsers.contains(player.getName())) {
            activeUsers.add(player.getName());
            player.sendMessage("§aCommand spy enabled");
        } else {
            activeUsers.remove(player.getName());
            player.sendMessage("§cCommand spy disabled");
        }
    }

    public static List<String> getActiveUsers() {
        return activeUsers;
    }
}
