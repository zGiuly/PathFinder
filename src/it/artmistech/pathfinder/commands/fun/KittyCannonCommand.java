package it.artmistech.pathfinder.commands.fun;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class KittyCannonCommand extends AbstractCommand {
    public KittyCannonCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "kittycannon");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player) sender;

        if (!player.hasPermission("pathfinder.kittycannon")) return;

        Ocelot ocelot = (Ocelot) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.OCELOT);

        ocelot.setVelocity(player.getEyeLocation().getDirection().multiply(2));

        Bukkit.getScheduler().runTaskLater(getPathFinder(), () -> {
            ocelot.getWorld().createExplosion(ocelot.getLocation().getX(), ocelot.getLocation().getY(), ocelot.getLocation().getZ(), 1, false, false);
            ocelot.remove();
        }, 60);
    }
}
