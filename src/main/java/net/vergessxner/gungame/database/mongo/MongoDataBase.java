package net.vergessxner.gungame.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.vergessxner.gungame.database.IDataBase;
import net.vergessxner.gungame.database.IStatsProvider;
import org.bson.Document;
import org.bukkit.Bukkit;

/**
 * @author Jonas
 * Created: 10.12.2020
 * Class: MongoDataBase
 */

public class MongoDataBase implements IDataBase {

    private final String connectionString;

    public MongoDataBase(String connectionString) {
        this.connectionString = connectionString;
    }

    private IStatsProvider statsProvider;

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public void connect() {
        if(connectionString == null)
            client = MongoClients.create();
        else
            client = MongoClients.create(connectionString);

        database = client.getDatabase("gungame");
        collection = database.getCollection("stats");

        statsProvider = new MongoStatsProvider(collection);
        Bukkit.getConsoleSender().sendMessage("§aMongoDB connected!");
    }

    @Override
    public void disconnect() {
        client.close();
        client = null;
    }


    @Override
    public boolean isConnected() {
        return client != null;
    }

    @Override
    public IStatsProvider getStatsProvider() {
        return statsProvider;
    }
}
