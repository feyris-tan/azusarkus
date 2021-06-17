package moe.yo3explorer.azusa.control;

import com.arjuna.ats.jta.exceptions.NotImplementedException;
import org.postgresql.util.Base64;

import javax.json.Json;
import javax.json.JsonString;
import javax.json.bind.adapter.JsonbAdapter;

public class ByteArrayBase64Adapter implements JsonbAdapter<byte[], JsonString> {
    @Override
    public JsonString adaptToJson(byte[] bytes) throws Exception {
        String s = Base64.encodeBytes(bytes);
        return Json.createValue(s);
    }

    @Override
    public byte[] adaptFromJson(JsonString jsonValue) throws Exception {
        throw new NotImplementedException();
    }
}
