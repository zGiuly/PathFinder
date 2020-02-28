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

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if (!sender.hasPermission("pathfinder.nick")) return;
        int maxLenght = configInt("nickname.lenght");

        Player player = (Player) sender;

        if (strings.length == 1) {

            if(strings[0].equalsIgnoreCase("clear")) {
                player.setCustomName(player.getName());
                player.setDisplayName(player.getName());
                player.setPlayerListName(player.getName());

                NicknameUtils.removeName(getPathFinder().getDefaultDatabase(), player.getName());

                player.sendMessage("§aNickname reset");
                return;
            } else if(strings[0].equals(player.getName())) {
                player.setCustomName(player.getName());
                player.setDisplayName(player.getName());
                player.setPlayerListName(player.getName());

                NicknameUtils.removeName(getPathFinder().getDefaultDatabase(), player.getName());

                player.sendMessage("§aNickname reset");
                return;
            }

            String coloredNick = strings[0];

            if(configBoolean("nickname.colors")) {
                coloredNick = ChatColor.translateAlternateColorCodes('&', strings[0]);
            }

            if(strings[0].length() >= maxLenght) {
                player.sendMessage("§cNickname max lenght is " + maxLenght);
                return;
            }

            if (!nicknameExists(strings[0])) {
                player.sendMessage("§cNickname already exists");
                return;
            }

            player.setDisplayName(coloredNick);
            player.setCustomName(coloredNick);
            player.setPlayerListName(coloredNick);

            player.sendMessage("§aNew nickname is §e" + strings[0]);

            NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), strings[0], player.getName());
        } else if (strings.length == 2) {
            if (!player.hasPermission("pathfinder.nick.other")) return;

            if (!nicknameExists(strings[1])) {
                player.sendMessage("§cNickname already exists");
                return;
            }

            if(strings[1].length() >= maxLenght) {
                player.sendMessage("§cNickname max lenght is " + maxLenght);
                return;
            }

            String coloredNick = strings[1];

            if(configBoolean("nickname.colors")) {
                coloredNick = ChatColor.translateAlternateColorCodes('&', strings[1]);
            }

            Player target = Bukkit.getPlayerExact(strings[0]);

            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer offline!");
                return;
            }

            if(strings[1].equals(target.getName())) {
                target.setDisplayName(target.getName());
                target.setCustomName(target.getName());

                NicknameUtils.removeName(getPathFinder().getDefaultDatabase(), target.getName());
            }

            target.setCustomName(coloredNick);
            target.setDisplayName(coloredNick);
            player.setPlayerListName(coloredNick);
            target.sendMessage("§cNow your name is §e" + strings[1]);

            NicknameUtils.saveName(getPathFinder().getDefaultDatabase(), strings[1], target.getName());
        } else {
            player.sendMessage("§cSyntax error");
        }
    }


    private boolean nicknameExists(String name) {
        return NicknameUtils.extractRealNameFromDatabase(getPathFinder().getDefaultDatabase(), name) == null;
    }
}
