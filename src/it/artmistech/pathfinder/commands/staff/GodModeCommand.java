package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GodModeCommand extends AbstractCommand {
    private static List<String> usersGodMode;
    public GodModeCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "god");
        usersGodMode = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.god")) return;

        if(strings.length == 1) {
            if(!player.hasPermission("pathfinder.god.other")) return;

            if(strings[0].equals(player.getName())) {
                player.sendMessage("§cSyntax error");
                return;
            }

            Player target = Bukkit.getPlayerExact(strings[0]);

            if(target == null || !target.isOnline()) {
                player.sendMessage("§4Player offline");
                return;
            }

            if(!usersGodMode.contains(strings[0])) {
                usersGodMode.add(strings[0]);
                player.sendMessage("§aGod mode enabled for §e" + strings[0]);
                target.sendMessage("§aGod mode enabled by §e" + player.getName());
            } else {
                usersGodMode.remove(strings[0]);
                player.sendMessage("§aGod mode disabled for §e" + strings[0]);
                target.sendMessage("§cGod mode disabled by §e" + player.getName());
            }
        } else {
            if(!usersGodMode.contains(player.getName())) {
                usersGodMode.add(player.getName());
                player.sendMessage("§aGod mode enabled");
            } else {
                usersGodMode.remove(player.getName());
                player.sendMessage("§aGod mode disabled");
            }
        }
    }

    public static List<String> getUsersGodMode() {
        return usersGodMode;
    }
}
