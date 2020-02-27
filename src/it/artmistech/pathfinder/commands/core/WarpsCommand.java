package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpsCommand extends AbstractCommand {
    public WarpsCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "warps");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.warps")) return;

        StringBuilder builder = new StringBuilder();

        for (String warps : getPathFinder().getConfig().getConfigurationSection("warps").getKeys(false)) {
            builder.append("§a ").append(warps);
        }

        String data = builder.toString();

        if(data.isEmpty()) {
            player.sendMessage("§cNo warps found!");
            return;
        }

        player.sendMessage("§aWarp list:" + data);
    }
}
