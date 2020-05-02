package moe.yo3explorer.azusa.azusa.entity;

import java.sql.Date;

public class ProductInShelf
{
    public int Id;
    public int IconId;
    public String Name;
    public double Price;
    public int NumberOfDiscs;
    public boolean ContainsUndumped;
    public int relatedShelf;
    public Date BoughtOn;
    public long ScreenshotSize;
    public int MissingGraphData;
    public long CoverSize;
    public boolean NSFW;
}
