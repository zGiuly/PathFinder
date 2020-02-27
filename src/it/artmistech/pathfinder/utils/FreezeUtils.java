package it.artmistech.pathfinder.utils;

import it.artmistech.pathfinder.commands.staff.FreezeCommand;

public class FreezeUtils {
    public static void blockUser(String name, String controller) {
        FreezeCommand.getFreezedPlayers().put(name, controller);
    }

    public static boolean isBlocked(String name) {
        return FreezeCommand.getFreezedPlayers().containsKey(name);
    }

    public static void unblockUser(String name) {
        FreezeCommand.getFreezedPlayers().remove(name);
    }
}
