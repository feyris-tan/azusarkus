package moe.yo3explorer.azusa.web.entity.arbitrary;

import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

public class PingResponse
{
    public PingResponse()
    {
        timestamp = new Date();
        uuid = UUID.randomUUID();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostname = localHost.getHostName();
        }
        catch (Exception e)
        {
            hostname = "localhost";
        }
    }

    private Date timestamp;
    private UUID uuid;
    private int randomNumber;
    private String hostname;

    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date getTimestamp() {
        return timestamp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public String getHostname() {
        return hostname;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
}
