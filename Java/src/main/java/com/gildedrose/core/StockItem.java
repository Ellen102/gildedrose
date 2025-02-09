package com.gildedrose.core;

import com.gildedrose.core.valueobjects.SellInType;
import com.gildedrose.core.valueobjects.StockName;

public record StockItem(
    String name,
    SellInType sellInType,
    int quality
) {

    public StockItem copy(SellInType nextSellIn, int nextQuality) {
        return new StockItem(name, nextSellIn, nextQuality);
    }

    public StockItem withQuality(int quality) {
        return new StockItem(name, sellInType, quality);
    }

    public StockItem withSellIn(SellInType sellIn) {
        return new StockItem(name, sellIn, quality);
    }

    public int sellIn() {
        return switch (sellInType) {
            case SellInType.DailyDecreasingSellIn dailyDecreasingSellIn -> dailyDecreasingSellIn.value();
            case SellInType.StableSellIn stableSellIn -> throw new IllegalStateException("Should not request sellIn for " + name);
        };
    }

    public StockProperties asStockProperties() {
        return new StockProperties(sellInType, quality);
    }

    public StockName asStockName() {
        return new StockName(name);
    }
}

