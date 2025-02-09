package com.gildedrose.core.sellin;

public final class StableSellIn implements SellInType {

    StableSellIn() {
    }

    @Override
    public SellInType next() {
        return this;
    }

    @Override
    public int currentValue() {
        return 0;
    }
}
