package it.artmistech.pathfinder.types;

import it.artmistech.pathfinder.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.util.Random;

public class CustomLocation extends Location {
    public CustomLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    public CustomLocation(World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    /**
     * Create a CustomLocation from location
     * @param location
     * @return
     */
    public static CustomLocation fromLocation(Location location) {
        return new CustomLocation(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }


    public static void randomSafeLocation(int max, int maxAttempts, Location location, JavaPlugin plugin, Consumer<CustomLocation> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            int attempts = 0;
            Random rand = new Random();
            double angle = rand.nextDouble()*360;
            double x = location.getX() + (rand.nextDouble()*max*Math.cos(Math.toRadians(angle)));
            double z = location.getZ() + (rand.nextDouble()*max*Math.sin(Math.toRadians(angle)));
            CustomLocation newloc = new CustomLocation(location.getWorld(), x, location.getY(), z);

            while(!newloc.isSafe()) {
                if(attempts==maxAttempts) {
                    consumer.accept(null);
                    return;
                }

                angle = rand.nextDouble()*360;
                x = location.getX() + (rand.nextDouble()*max*Math.cos(Math.toRadians(angle)));
                z = location.getZ() + (rand.nextDouble()*max*Math.sin(Math.toRadians(angle)));
                newloc.setX(x);
                newloc.setZ(z);

                attempts++;
            }
            consumer.accept(newloc);
        });
    }

    /**
     * Check if location is safe
     * @return
     */
    public boolean isSafe() {
        return LocationUtils.isSafeLocation(this);
    }


}
