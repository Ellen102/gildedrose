package com.gildedrose.core.sellin;

public sealed interface SellInType permits DailyDecreasingSellIn, StableSellIn {

    SellInType next();

    int currentValue();

    ///public methods
    static SellInType dailyDecreasing(int sellIn) {
        return new DailyDecreasingSellIn(sellIn);
    }

    SellInType STABLE = new StableSellIn();

}
