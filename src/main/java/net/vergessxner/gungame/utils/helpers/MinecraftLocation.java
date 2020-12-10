package net.vergessxner.gungame.utils.helpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: MinecraftLocation
 */

public class MinecraftLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String worldName;

    public void setLocation(Location location) {
        x = location.getX();;
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
        worldName = location.getWorld().getName();
    }

    public Location toLocation() {
        try {
            return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
        }catch (NullPointerException event) {
            return null;
        }
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setYaw(int yaw) {
        this.yaw = yaw;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public String getWorldName() {
        return worldName;
    }

}
