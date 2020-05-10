package moe.yo3explorer.azusa.vapor.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import moe.yo3explorer.azusa.vapor.entity.Game;
import moe.yo3explorer.azusa.vapor.entity.MapFile;
import moe.yo3explorer.azusa.vapor.entity.ResourceFile;
import moe.yo3explorer.azusa.vapor.entity.ZBlob;

import javax.inject.Singleton;
import javax.json.*;
import java.io.FileNotFoundException;
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
        sku = sku.toUpperCase();
        Optional<Game> game = Game.find("vapor_sku = ?1", sku).firstResultOptional();
        return game.orElse(null);
    }

    public Game findGameById(int id)
    {
        Game byId = Game.findById(id);
        return byId;
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
            String value = allResource.filename.replace('\\','/');
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

        List<MapFile> mapList = MapFile.find("gameid = ?1", game.id).list();
        mapList.stream().map(x -> String.format("map%04d.lmu",x.mapnumber)).forEach(x -> objectBuilder.add(x,x));

        objectBuilder.add("rpg_rt.exe","RPG_RT.exe");
        objectBuilder.add("rpg_rt.ini","RPG_RT.ini");
        objectBuilder.add("rpg_rt.ldb","RPG_RT.ldb");
        objectBuilder.add("rpg_rt.lmt","RPG_RT.lmt");

        return objectBuilder.build();
    }

    public ResourceFile findResourceFile(Game game, String resCategory, String resName)
    {
        String filename = String.format("%s\\%s",resCategory,resName);

        Optional<ResourceFile> foundResource = ResourceFile.find("gameid = ?1 AND filename = ?2", game.id, filename).firstResultOptional();
        if (foundResource.isPresent())
            return foundResource.get();

        if (game.resourcedependency == null)
            throw new GameResourceMissingException(game,resCategory,resName);

        Game dependentGame = findGameById(game.resourcedependency);
        if (dependentGame == null)
            throw new GameResourceMissingException(game,resCategory,resName);
        return findResourceFile(dependentGame,resCategory,resName);
    }

}
