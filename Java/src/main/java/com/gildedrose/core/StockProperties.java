package com.gildedrose.core;

import com.gildedrose.core.sellin.SellInType;

public record StockProperties(
    SellInType sellInType,
    int quality
) {
    public int sellIn() {
        return sellInType.currentValue();
    }
}
