package it.artmistech.pathfinder.listeners;

import it.artmistech.pathfinder.PathFinder;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class SpiderControlListener extends AbstractListener {
    public SpiderControlListener(PathFinder pathFinder) {
        super(pathFinder);
    }


    @EventHandler
    public void exitSpider(VehicleExitEvent event) {
        if(event.getVehicle().getType() == EntityType.CAVE_SPIDER) {
            CaveSpider spider = (CaveSpider)event.getVehicle();

            spider.setHealth(0);
        }
    }
}
