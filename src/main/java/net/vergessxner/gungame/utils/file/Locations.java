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
    private MinecraftLocation pos1;
    private MinecraftLocation pos2;


    public MinecraftLocation getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = new MinecraftLocation();
        this.pos1.setLocation(pos1);
    }

    public MinecraftLocation getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = new MinecraftLocation();
        this.pos2.setLocation(pos2);
    }

    public void setSpawn(Location spawn) {
        this.spawn = new MinecraftLocation();
        this.spawn.setLocation(spawn);
    }

    public MinecraftLocation getSpawn() {
        return spawn;
    }
}
