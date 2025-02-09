package com.gildedrose.core.helpers;

import com.gildedrose.core.StockItem;
import com.gildedrose.core.sellin.SellInType;

import static com.gildedrose.GildedRoseConstants.SULFURAS;
import static com.gildedrose.GildedRoseConstants.SULFURAS_QUALITY;

public class StockItemBuilder {
    private String name;
    private int sellIn;
    private int quality;


    public static StockItemBuilder anItem(){
        return new StockItemBuilder("foo",1,1);
    }
    public static StockItemBuilder anSulfurasItem(){
        return new StockItemBuilder(SULFURAS,0, SULFURAS_QUALITY);
    }

    public StockItemBuilder withName(String name){
        this.name = name;
        return this;
    }

    public StockItemBuilder withSellIn(int sellIn){
        this.sellIn = sellIn;
        return this;
    }

    public StockItemBuilder withQuality(int quality){
        this.quality = quality;
        return this;
    }

    StockItemBuilder(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public StockItem build() {
        return new StockItem(name, SellInType.STABLE, quality);
    }
}
