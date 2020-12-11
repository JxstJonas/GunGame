package net.vergessxner.gungame.database.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.vergessxner.gungame.database.IStatsProvider;
import net.vergessxner.gungame.utils.GunGamePlayer;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: MongoStatsProvider
 */

public class MongoStatsProvider implements IStatsProvider {

    private static final Gson GSON = new Gson();
    private final MongoCollection<Document> collection;
    private HashMap<UUID, GunGamePlayer> map = new HashMap<>();

    public MongoStatsProvider(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    @Override
    public void updatePlayer(GunGamePlayer gunGamePlayer) {
        gunGamePlayer.setGunGameTeam(null);

        String json = GSON.toJson(gunGamePlayer);

        if(isExist(gunGamePlayer.getUuid())) {
            Document found = collection.find(Filters.eq("uuid", gunGamePlayer.getUuid().toString())).first();
            collection.updateOne(found, new Document("$set", Document.parse(json)));
            return;
        }

        collection.insertOne(Document.parse(json));
    }

    @Override
    public void deletePlayer(UUID uuid) {
        collection.deleteMany(Filters.eq("uuid", uuid.toString()));
    }


    @Override
    public GunGamePlayer getPlayer(UUID uuid) {
        GunGamePlayer gunGamePlayer;

        if(map.containsKey(uuid)) {
            return map.get(uuid);
        }

        Document document = collection.find(Filters.eq("uuid", uuid.toString())).first();
        if(document == null) {
            gunGamePlayer = new GunGamePlayer();
            gunGamePlayer.setUuid(uuid);
        }else
            gunGamePlayer = GSON.fromJson(document.toJson(), GunGamePlayer.class);

        map.put(uuid, gunGamePlayer);
        return gunGamePlayer;
    }

    @Override
    public void unregisterPlayer(GunGamePlayer gunGamePlayer) {
        if(map.containsValue(gunGamePlayer)) map.remove(gunGamePlayer.getUuid());
    }

    @Override
    public boolean isExist(UUID uuid) {
        return collection.find(Filters.eq("uuid", uuid.toString())).first() != null;
    }
}
