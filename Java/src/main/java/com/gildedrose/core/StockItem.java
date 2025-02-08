package com.gildedrose.core;

public record StockItem(
    String name,
    Integer sellIn,
    int quality
) {

    public StockItem copy(int nextSellIn, int nextQuality) {
        return new StockItem(name, nextSellIn, nextQuality);
    }
}
