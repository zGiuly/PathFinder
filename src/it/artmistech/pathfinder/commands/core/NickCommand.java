package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.utils.NicknameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand extends AbstractCommand {
    public NickCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "nick");
    }

    //Currently not functional
    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.nick")) return;

        Player player = (Player) sender;

        if (strings.length == 1) {

            if(strings[0].equalsIgnoreCase("clear")) {
                player.setCustomName(player.getName());
                player.setDisplayName(player.getName());

                NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), player.getName(), player.getName());
                return;
            }

            String coloredNick = ChatColor.translateAlternateColorCodes('&', strings[0]);

            if (nicknameExists(strings[0])) {
                player.sendMessage("§cNickname already exists");
                return;
            }

            player.setDisplayName(coloredNick);
            player.setCustomName(coloredNick);

            player.sendMessage("§aNew nickname is §e" + strings[0]);

            NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), strings[0], player.getName());
        } else if (strings.length == 2) {
            if (!player.hasPermission("pathfinder.nick.other")) return;

            if (nicknameExists(strings[1])) {
                player.sendMessage("§cNickname already exists");
                return;
            }

            String coloredNick = ChatColor.translateAlternateColorCodes('&', strings[1]);

            Player target = Bukkit.getPlayerExact(strings[1]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            if(strings[1].equals(target.getName())) {
                target.setDisplayName(target.getName());
                target.setCustomName(target.getName());
                NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), target.getName(), strings[1]);
            }

            target.setCustomName(coloredNick);
            target.setDisplayName(coloredNick);
            target.sendMessage("§cNow your name is §e" + strings[1]);

            NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), strings[0], strings[1]);
        } else {
            player.sendMessage("§cSyntax error");
        }
    }


    private boolean nicknameExists(String name) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getDisplayName().equals(name)) return true;
        }
        return false;
    }
}
