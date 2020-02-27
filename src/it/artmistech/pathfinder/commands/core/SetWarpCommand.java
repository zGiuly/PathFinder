package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.io.PluginFile;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand extends AbstractCommand {
    public SetWarpCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "setwarp");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.setwarp")) return;

        if (strings.length == 1) {
            CustomLocation warpLocation = CustomLocation.fromLocation(player.getLocation());

            PluginFile config = getPathFinder().getBaseConfig();

            if(configBoolean("warp.only-safe-location")) {
                if(!warpLocation.isSafe()) {
                    player.sendMessage("§4This location is unsafe!");
                    return;
                }
            }

            config.setLocation("warps."+strings[0], warpLocation);

            player.sendMessage("§aWarp " + strings[0] + " set!");
        } else {
            player.sendMessage("§4Name not found!");
        }
    }
}
