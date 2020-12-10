package net.vergessxner.gungame;

import net.vergessxner.gungame.command.SetUpCommand;
import net.vergessxner.gungame.database.IDataBase;
import net.vergessxner.gungame.database.mongo.MongoDataBase;
import net.vergessxner.gungame.listener.DeathListener;
import net.vergessxner.gungame.listener.JoinQuitListener;
import net.vergessxner.gungame.utils.file.ConfigLoader;
import net.vergessxner.gungame.utils.file.Locations;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class GunGame extends JavaPlugin {

    public static final String PREFIX = "";

    private static GunGame INSTANCE;

    private IDataBase dataBase;
    private ConfigLoader<Locations> loader;

    @Override
    public void onLoad() {
        INSTANCE = this;

        dataBase = new MongoDataBase(null);
        dataBase.connect();
        getLogger().info("DataBase connected");

        File dir = new File(System.getProperty("user.dir")
                + "/plugins/GunGame");
        dir.mkdirs();

        File file = new File(System.getProperty("user.dir") + "/plugins/GunGame/locations.json");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loader = new ConfigLoader<>(file, Locations.class);
        loader.load();

        getLogger().info("Loader is ready");
    }

    @Override
    public void onEnable() {
        getCommand("setup").setExecutor(new SetUpCommand());

        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public IDataBase getDataBase() {
        return dataBase;
    }

    public ConfigLoader<Locations> getLoader() {
        return loader;
    }

    public static GunGame getINSTANCE() {
        return INSTANCE;
    }
}
