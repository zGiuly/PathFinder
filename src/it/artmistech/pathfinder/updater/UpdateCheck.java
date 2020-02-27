package it.artmistech.pathfinder.updater;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.Bukkit;
import org.bukkit.util.Consumer;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateCheck {
    private final PathFinder pathFinder;
    private final int resourceId;

    public UpdateCheck(PathFinder pathFinder, int resourceId) {
        this.pathFinder = pathFinder;
        this.resourceId = resourceId;
    }

    public void isOutdated() {
        Consumer<String> consumer = s -> {
            if(!pathFinder.getDescription().getVersion().equals(s)) {
                pathFinder.getLogger().warning("A new update is avaiable at https://www.spigotmc.org/resources/pathfinder.75513/");
            }
        };

        Bukkit.getScheduler().runTaskAsynchronously(pathFinder, ()-> {
           try(InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource="+resourceId).openStream()) {
               Scanner scanner = new Scanner(inputStream);

               if(scanner.hasNext()) {
                   consumer.accept(scanner.next());
               }
           }catch (Exception e) {
               e.printStackTrace();
           }
        });
    }
}
