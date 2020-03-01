package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import it.artmistech.pathfinder.types.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnListener extends AbstractListener {
    private CustomLocation spawnLocation;

    public SpawnListener(PathFinder pathFinder) {
        super(pathFinder);
        setupSpawnLocation();
    }

    @EventHandler
    public void playerJoinSpawn(PlayerJoinEvent event) {

        if(spawnLocation == null) return;

        if (spawnLocation.isSafe() && configBoolean("spawn-every-join")) {
            event.getPlayer().teleport(spawnLocation);
        } else if (!spawnLocation.isSafe()) {
            event.getPlayer().sendMessage("§4Spawn location is unsafe!\n§4Contact the admin for fix this");
        }
    }

    private void setupSpawnLocation() {
        World world = Bukkit.getWorld(configString("spawn.world"));
        double x = configDouble("spawn.x");
        double y = configDouble("spawn.y");
        double z = configDouble("spawn.z");
        float yaw = configInt("spawn.yaw");

        spawnLocation = new CustomLocation(world, x, y, z, yaw, 0);
    }
}
