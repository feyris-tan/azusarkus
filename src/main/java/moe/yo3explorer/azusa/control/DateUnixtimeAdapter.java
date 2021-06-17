package moe.yo3explorer.azusa.control;


import com.arjuna.ats.jta.exceptions.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.bind.adapter.JsonbAdapter;
import java.sql.Timestamp;
import java.util.Date;

public class DateUnixtimeAdapter implements JsonbAdapter<Date, JsonNumber> {
    @Override
    public JsonNumber adaptToJson(@NotNull Date timestamp) throws Exception {
        long time = timestamp.getTime();
        time /= 1000;
        JsonNumber value = Json.createValue(time);
        return value;
    }

    @Override
    public Timestamp adaptFromJson(JsonNumber jsonNumber) throws Exception {
        throw new NotImplementedException();
    }
}
