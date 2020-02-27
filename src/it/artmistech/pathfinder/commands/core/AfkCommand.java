package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AfkCommand extends AbstractCommand {
    private static List<String> afkPlayers;
    public AfkCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "afk");
        afkPlayers = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.afk")) return;

        if(!afkPlayers.contains(player.getName())) {
            afkPlayers.add(player.getName());
            player.sendMessage("§aNow you are afk");
        } else {
            afkPlayers.remove(player.getName());
            player.sendMessage("§aNow you are alive");
        }
    }

    public static List<String> getAfkPlayers() {
        return afkPlayers;
    }
}
