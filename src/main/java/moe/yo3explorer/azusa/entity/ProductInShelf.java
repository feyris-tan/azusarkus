package moe.yo3explorer.azusa.entity;

import moe.yo3explorer.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import java.util.Date;

public class ProductInShelf
{
    public int Id;
    public int IconId;
    public String Name;
    public Double Price;
    public int NumberOfDiscs;
    public Boolean ContainsUndumped;
    public int relatedShelf;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date BoughtOn;
    public Long ScreenshotSize;
    public long MissingGraphData;
    public Long CoverSize;
    public Boolean NSFW;
}
