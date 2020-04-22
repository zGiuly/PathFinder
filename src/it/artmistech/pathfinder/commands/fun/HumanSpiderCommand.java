package it.artmistech.pathfinder.commands.fun;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HumanSpiderCommand extends AbstractCommand {
    public HumanSpiderCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "humanspider");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(!sender.hasPermission("pathfinder.humanspider")) return;

        Player player = (Player)sender;

        Horse horse = player.getWorld().spawn(player.getLocation(), Horse.class);

        horse.addPassenger(player);
        horse.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,  10000, 255, false, false));
        horse.setInvulnerable(true);
        horse.setAdult();
        horse.setTamed(true);
        horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

        CaveSpider spider = player.getWorld().spawn(horse.getLocation(), CaveSpider.class);

        horse.addPassenger(spider);
    }
}
