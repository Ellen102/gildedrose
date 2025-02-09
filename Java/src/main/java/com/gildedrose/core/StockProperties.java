package com.gildedrose.core;

import com.gildedrose.core.valueobjects.SellInType;

public record StockProperties(
    SellInType sellInType,
    int quality
) {
    public int sellIn() {
        return sellInType.currentValue();
    }
}
