package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import it.artmistech.pathfinder.utils.PathFinderUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KillAllCommand extends AbstractCommand {
    public KillAllCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "killall");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.killall")) return;

        if(strings.length != 1) return;

        String type = strings[0].toLowerCase();

        switch (type) {
            case "monsters":
                player.sendMessage("§a" + PathFinderUtils.killAllMonsters(player.getWorld()) + " monsters have been killed!");
                break;
            case "animals":
                player.sendMessage("§a" + PathFinderUtils.killAllAnimals(player.getWorld()) + " animals have been killed!");
                break;
            case "entities":
                player.sendMessage("§a" + PathFinderUtils.killAllEntities(player.getWorld()) + " entities have been killed!");
                break;
            case "drops":
                player.sendMessage("§a" + PathFinderUtils.killAllDrops(player.getWorld()) + " items have been killed!");
                break;
        }
    }
}
