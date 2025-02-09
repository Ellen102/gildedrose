package com.gildedrose.core;

import com.gildedrose.core.sellin.DailyDecreasingSellIn;
import com.gildedrose.core.sellin.SellInType;
import com.gildedrose.core.sellin.StableSellIn;

public record StockItem(
    String name,
    SellInType sellInType,
    int quality
) {

    public StockItem copy(SellInType nextSellIn, int nextQuality) {
        return new StockItem(name, nextSellIn, nextQuality);
    }

    public int sellIn() {
        return switch (sellInType) {
            case DailyDecreasingSellIn dailyDecreasingSellIn -> dailyDecreasingSellIn.value();
            case StableSellIn stableSellIn -> throw new IllegalStateException("Should not request sellIn for " + name);
        };
    }

    public StockProperties stockProperties() {
        return new StockProperties(sellInType, quality);
    }

    public StockName stockName() {
        return new StockName(name);
    }
}

