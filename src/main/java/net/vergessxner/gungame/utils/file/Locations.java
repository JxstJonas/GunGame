package net.vergessxner.gungame.utils.file;

import net.vergessxner.gungame.utils.helpers.MinecraftLocation;
import org.bukkit.Location;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: Locations
 */

public class Locations {

    private MinecraftLocation spawn;

    public void setSpawn(Location spawn) {
        this.spawn = new MinecraftLocation();
        this.spawn.setLocation(spawn);
    }

    public MinecraftLocation getSpawn() {
        return spawn;
    }
}
