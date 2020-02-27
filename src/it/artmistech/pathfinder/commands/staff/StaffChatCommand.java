package it.artmistech.pathfinder.commands.staff;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.commands.AbstractCommand;
import it.artmistech.pathfinder.enums.SenderEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffChatCommand extends AbstractCommand {
    private static List<String> staffChat;
    public StaffChatCommand(PathFinder pathFinder) {
        super(SenderEnum.PLAYER, pathFinder, "staffchat");
        staffChat = new ArrayList<>();
    }

    @Override
    public void execute(CommandSender sender, String[] strings) {
        Player player = (Player)sender;

        if(!player.hasPermission("pathfinder.staffchat")) return;

        if(!staffChat.contains(player.getName())) {
            staffChat.add(player.getName());
            player.sendMessage("§aNow you talk in staff chat!");
        } else {
            staffChat.remove(player.getName());
            player.sendMessage("§aNow you talk in public chat!");
        }
    }

    public static List<String> getStaffChat() {
        return staffChat;
    }
}
