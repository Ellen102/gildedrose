package com.gildedrose.helpers;

import com.gildedrose.Item;
import com.gildedrose.GildedRoseConstants;

import static com.gildedrose.GildedRoseConstants.*;

public class ItemBuilder {
    private String name;
    private int sellIn;
    private int quality;


    public static ItemBuilder anItem(){
        return new ItemBuilder("foo",1,1);
    }
    public static ItemBuilder anSulfurasItem(){
        return new ItemBuilder(SULFURAS,-1, SULFURAS_QUALITY);
    }

    public ItemBuilder withName(String name){
        this.name = name;
        return this;
    }

    public ItemBuilder withSellIn(int sellIn){
        this.sellIn = sellIn;
        return this;
    }

    public ItemBuilder withQuality(int quality){
        this.quality = quality;
        return this;
    }

    ItemBuilder(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public Item build() {
        return new Item(name, sellIn, quality);
    }
}
