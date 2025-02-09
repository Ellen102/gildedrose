package com.gildedrose.core.helpers;

import com.gildedrose.core.StockProperties;
import com.gildedrose.core.sellin.SellInType;

import static com.gildedrose.GildedRoseConstants.SULFURAS_QUALITY;

public class StockPropertiesBuilder {
    private int sellIn;
    private int quality;


    public static StockPropertiesBuilder anItem(){
        return new StockPropertiesBuilder(1,1);
    }
    public static StockPropertiesBuilder anSulfurasItem(){
        return new StockPropertiesBuilder(0, SULFURAS_QUALITY);
    }


    public StockPropertiesBuilder withSellIn(int sellIn){
        this.sellIn = sellIn;
        return this;
    }

    public StockPropertiesBuilder withQuality(int quality){
        this.quality = quality;
        return this;
    }

    StockPropertiesBuilder(int sellIn, int quality) {
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public StockProperties build() {
        return new StockProperties( SellInType.dailyDecreasing(sellIn), quality);
    }
}
