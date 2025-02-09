package com.gildedrose.core.valueobjects;

public sealed interface SellInType {

    SellInType next();
    int currentValue();

    ///public methods
    static SellInType dailyDecreasing(int sellIn) {
        return new DailyDecreasingSellIn(sellIn);
    }

    SellInType STABLE = new StableSellIn();

    ///implementation
    record DailyDecreasingSellIn(int value) implements SellInType {

        @Override
        public SellInType next() {
            return new DailyDecreasingSellIn(value - 1);
        }

        @Override
        public int currentValue() {
            return value;
        }
    }

    final class StableSellIn implements SellInType {

        private StableSellIn() {
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
}
