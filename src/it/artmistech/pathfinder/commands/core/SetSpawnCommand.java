package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.io.PluginFile;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends AbstractCommand {
    public SetSpawnCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "setspawn");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if(!player.hasPermission("pathfinder.setspawn")) return;

        CustomLocation spawnLocation = CustomLocation.fromLocation(player.getLocation());

        if(!spawnLocation.isSafe()) {
            player.sendMessage("§4This location is unsafe");
            return;
        }

        PluginFile config = getPathFinder().getBaseConfig();

        config.setLocation("spawn", spawnLocation);

        config.save();
        config.reload();

        player.sendMessage("§aSpawn set!");
    }
}
