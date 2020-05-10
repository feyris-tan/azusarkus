package moe.yo3explorer.azusa.vapor.control;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jp.gr.java_conf.dangan.util.lha.LhaHeader;
import moe.yo3explorer.azusa.vapor.entity.Game;
import moe.yo3explorer.azusa.vapor.entity.MapFile;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.util.Date;
import java.util.Optional;

@Singleton
public class MapService {

    public @NotNull MapFile createMapFile(@NotNull LhaHeader lhaHeader, byte[] unpackedEntry) {
        String fname = lhaHeader.getPath();
        fname = fname.substring(3);
        fname = fname.substring(0,fname.length() - 4);

        MapFile mapFile = new MapFile();
        mapFile.mapnumber = Short.parseShort(fname);
        mapFile.dateadded = new Date();
        mapFile.mapdata = unpackedEntry;
        mapFile.filedate = lhaHeader.getLastModified();
        return mapFile;
    }

    public MapFile findMapFile(@NotNull Game game, short mapId)
    {
        Optional<MapFile> mapFile = MapFile.find("gameid = ?1 AND mapnumber = ?2", game.id, mapId).firstResultOptional();
        return mapFile.orElse(null);
    }
}
