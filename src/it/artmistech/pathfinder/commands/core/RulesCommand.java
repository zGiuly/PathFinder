package it.artmistech.pathfinder.commands.core;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RulesCommand extends AbstractCommand {
    public RulesCommand(PathFinder pathFinder) {
        super(SenderEnum.ALL, pathFinder, "rules");
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        if(configString("rules").isEmpty()) {
            sender.sendMessage("§cNo rules set");
            return;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configString("rules").replaceAll("%n", "\n")));
    }
}
