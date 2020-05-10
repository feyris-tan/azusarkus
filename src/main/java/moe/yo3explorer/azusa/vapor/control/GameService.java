package moe.yo3explorer.azusa.vapor.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.vapor.entity.Game;
import moe.yo3explorer.azusa.vapor.entity.ResourceFile;

import javax.inject.Singleton;
import javax.json.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class GameService
{
    public boolean testForSku(String sku)
    {
        long count = Game.count("vapor_sku = ?1", sku);
        return count > 0;
    }

    public Game findGameBySku(String sku)
    {
        Optional<Game> game = Game.find("vapor_sku = ?1", sku).firstResultOptional();
        return game.orElse(null);
    }

    public JsonObject buildIndexJson(Game game)
    {
        LinkedList<ResourceFile> allResources = new LinkedList<>();
        Game dependentGame = game;
        while (dependentGame != null)
        {
            List<ResourceFile> dependentResources = ResourceFile.find("gameid = ?1", dependentGame.id).list();
            allResources.addAll(dependentResources);
            if (dependentGame.resourcedependency != null)
                dependentGame = Game.findById(dependentGame.resourcedependency);
            else
                dependentGame = null;
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        for (ResourceFile allResource : allResources) {
            String value = allResource.filename;
            String key = value.toLowerCase();

            if (key.endsWith(".png") || key.endsWith(".avi") || key.endsWith(".mid") || key.endsWith(".wav"))
            {
                key = key.substring(0,key.length() - 4);
            }
            else if (key.endsWith(".dll"))
            {
                /* Pass. */
            }
            else
            {
                throw new RuntimeException("failed to determine json key value for: " + value);
            }
            objectBuilder.add(key,value);
        }

        return objectBuilder.build();
    }
}
