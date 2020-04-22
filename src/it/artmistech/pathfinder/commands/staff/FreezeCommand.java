package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.utils.PathFinderUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FreezeCommand extends AbstractCommand {
    private static Map<String, String> freezedPlayers;

    public FreezeCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "freeze");
        freezedPlayers = new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.freeze")) return;

        if (strings.length != 1) return;

        Player target = Bukkit.getPlayerExact(strings[0]);

        if (target == null || !target.isOnline()) {
            player.sendMessage("§cPlayer offline!");
            return;
        }

        if(target.hasPermission("pathfinder.freeze.ignore")) {
            player.sendMessage("§cYou can't freeze this player");
            return;
        }

        if (!PathFinderUtils.isBlocked(target.getName())) {
            PathFinderUtils.blockUser(target.getName(), player.getName());
            player.sendMessage("§aPlayer §e" + target.getName() + "§a is blocked!");
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', configString("freeze.start-message")));
        } else {
            player.sendMessage("§cYou're already checking");
        }
    }

    public static Map<String, String> getFreezedPlayers() {
        return freezedPlayers;
    }
}
