package moe.yo3explorer.azusa.skyscraper.entity;

import java.sql.Timestamp;

public class CalendarEvent
{
    private final String title;
    private final String start;
    private final String end;
    private final String id;

    public CalendarEvent(Event event)
    {
        this.title = event.title;
        this.start = event.starttime.toString();
        this.end = event.endtime.toString();
        this.id = Long.toString(event.eventid);
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getId() {
        return id;
    }
}
