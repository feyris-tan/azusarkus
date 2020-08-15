package moe.yo3explorer.azusa.azusa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import moe.yo3explorer.azusa.azusa.control.ByteArrayBase64Adapter;
import moe.yo3explorer.azusa.azusa.control.DateUnixtimeAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products",schema = "azusa")
@NamedQueries({
        @NamedQuery(name = ProductsEntity.FIND_BY_SHELF_WITHOUT_GRAPHICS, query = "SELECT new ProductsEntity(a.id,a.inshelf,a.name,a.price,a.boughtOn,a.sku,a.platform,a.supplier,a.countryOfOrigin,a.dateadded,a.consistent,a.nsfw,a.complete,a.dateupdated) FROM ProductsEntity a WHERE a.inshelf = :shelf"),
        @NamedQuery(name = ProductsEntity.FIND_FULL_LIST_FOR_MOBILE_APP, query = "SELECT new ProductsEntity(a.id,a.inshelf,a.name,a.price,a.boughtOn,a.sku,a.platform,a.supplier,a.countryOfOrigin,a.dateadded,a.consistent,a.nsfw,a.complete,a.dateupdated) FROM ProductsEntity a")
})
public class ProductsEntity extends PanacheEntityBase
{
    public static final String FIND_BY_SHELF_WITHOUT_GRAPHICS = "Products.FindByShelfWithoutGraphics";
    public static final String FIND_FULL_LIST_FOR_MOBILE_APP = "Products.FindAll";
    public ProductsEntity() {

    }

    public ProductsEntity(int id, int inshelf, String name, Double price, Date boughtOn, String sku, Integer platform, Integer supplier, Integer countryOfOrigin, Date dateadded, boolean consistent, Boolean nsfw, boolean complete, Date dateupdated) {
        this.id = id;
        this.inshelf = inshelf;
        this.name = name;
        this.price = price;
        this.boughtOn = boughtOn;
        this.sku = sku;
        this.platform = platform;
        this.supplier = supplier;
        this.countryOfOrigin = countryOfOrigin;
        this.dateadded = dateadded;
        this.consistent = consistent;
        this.nsfw = nsfw;
        this.complete = complete;
        this.dateupdated = dateupdated;
    }

    @Id
    public int id;
    public int inshelf;
    public String name;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] picture;
    public Double price;
    @Column(name = "\"boughtOn\"")
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date boughtOn;
    public String sku;
    public Integer platform;
    public Integer supplier;
    @Column(name = "\"countryOfOrigin\"")
    public Integer countryOfOrigin;
    @JsonbTypeAdapter(ByteArrayBase64Adapter.class)
    public byte[] screenshot;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date dateadded;
    public boolean consistent;
    public Boolean nsfw;
    public boolean complete;
    @JsonbTypeAdapter(DateUnixtimeAdapter.class)
    public Date dateupdated;
}
