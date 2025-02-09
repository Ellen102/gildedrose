package com.gildedrose;

import com.gildedrose.core.Calculator;
import com.gildedrose.core.StockItem;
import com.gildedrose.core.valueobjects.SellInType;

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

        public AntiCorruptionItem with(StockItem stockItem) {
            return new AntiCorruptionItem(item, stockItem);
        }

        public void applyChanges() {
            item.quality = stockItem.quality();
            item.sellIn = switch (stockItem.sellInType()) {
                case SellInType.DailyDecreasingSellIn dailyDecreasingSellIn -> dailyDecreasingSellIn.value();
                case SellInType.StableSellIn stableSellIn -> item.sellIn;
            };
        }

        public AntiCorruptionItem calculateNext() {
            return with(calculator.calculateNext(stockItem));
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
