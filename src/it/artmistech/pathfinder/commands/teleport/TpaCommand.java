package it.artmistech.pathfinder.commands.teleport;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends AbstractCommand {
    public TpaCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "tpa");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;
        if (!player.hasPermission("pathfinder.tpa")) return;

        if (strings.length == 1) {
            if (!strings[0].equalsIgnoreCase("accept") && !strings[0].equalsIgnoreCase("deny")) {

                if (strings[0].equals(player.getName())) {
                    player.sendMessage("§4Error");
                    return;
                }

                Player target = Bukkit.getPlayerExact(strings[0]);
                if (target != null && target.isOnline()) {
                    if (!getPathFinder().getTpaManager().init(player, target)) {
                        player.sendMessage("§4Error");
                        return;
                    }
                }
            } else if (strings[0].equalsIgnoreCase("accept")) {
                if (!getPathFinder().getTpaManager().acceptTpa(player)) {
                    player.sendMessage("§4Error");
                    return;
                }
            } else if (strings[0].equalsIgnoreCase("deny")) {
                if (!getPathFinder().getTpaManager().denyTpa(player)) {
                    player.sendMessage("§4Error");
                    return;
                }
            }
        }
    }
}
