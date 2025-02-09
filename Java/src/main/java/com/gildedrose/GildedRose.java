package com.gildedrose;

import com.gildedrose.core.Calculator;
import com.gildedrose.core.StockItem;
import com.gildedrose.core.sellin.DailyDecreasingSellIn;
import com.gildedrose.core.sellin.SellInType;
import com.gildedrose.core.sellin.StableSellIn;

import java.util.Arrays;
import java.util.List;


class GildedRose {
    Item[] items;

    private static final Calculator calculator = GildedRoseConstants.FEATURE_FLAGS.calculator();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        List<AntiCorruptionItem> deepCopyOfItems = Arrays.stream(items)
            .map(AntiCorruptionItem::create)
            .toList();

        List<AntiCorruptionItem> finishedItemCalculation = deepCopyOfItems
            .stream()
            .map(AntiCorruptionItem::calculateNext)
            .toList();

        finishedItemCalculation
            .forEach(AntiCorruptionItem::applyChanges);

    }


    private record AntiCorruptionItem(Item item, StockItem stockItem) {

        public static AntiCorruptionItem create(Item item) {
            StockItem antiCorruptionItem = new StockItem(item.name, determineSellInType(item), item.quality);
            return new AntiCorruptionItem(item, antiCorruptionItem);
        }

        public void applyChanges() {
            item.quality = stockItem.quality();
            item.sellIn = switch (stockItem.sellInType()) {
                case DailyDecreasingSellIn dailyDecreasingSellIn -> dailyDecreasingSellIn.value();
                case StableSellIn stableSellIn -> item.sellIn;
            };
        }

        public AntiCorruptionItem calculateNext() {
            return new AntiCorruptionItem(item, calculator.calculateNext(stockItem));
        }
    }

    private static SellInType determineSellInType(Item item) {
        final SellInType sellIn;
        if (item.name.equals(GildedRoseConstants.SULFURAS)) {
            sellIn = SellInType.STABLE;
        } else {
            sellIn = SellInType.dailyDecreasing(item.sellIn);
        }
        return sellIn;
    }


}
