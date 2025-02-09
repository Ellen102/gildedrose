package com.gildedrose.core.sellin;

public record DailyDecreasingSellIn(int value) implements SellInType {

    @Override
    public SellInType next() {
        return new com.gildedrose.core.sellin.DailyDecreasingSellIn(value - 1);
    }

    @Override
    public int currentValue() {
        return value;
    }
}
