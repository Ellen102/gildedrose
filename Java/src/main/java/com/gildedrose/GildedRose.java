package com.gildedrose;

import com.gildedrose.core.Calculator;
import com.gildedrose.core.SimpleCalculator;
import com.gildedrose.core.StockItem;

import java.util.Arrays;
import java.util.List;

class GildedRose {
    Item[] items;
    private static final Calculator calculator = new SimpleCalculator();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        List<ItemToAntiCorruptionItem> deepCopyOfItems = Arrays.stream(items)
            .map(ItemToAntiCorruptionItem::create)
            .toList();

        List<ItemToAntiCorruptionItem> finishedItemCalculation = deepCopyOfItems
            .stream()
            .map(AntiCorruptionActions::new)
            .map(AntiCorruptionActions::calculateNext)
            .toList();

        finishedItemCalculation
            .forEach(ItemToAntiCorruptionItem::applyChanges);

    }

    private record AntiCorruptionActions(ItemToAntiCorruptionItem item) {
        public ItemToAntiCorruptionItem calculateNext() {
            return item.withStockItem(calculator.calculateNext(item.stockItem));
        }
    }


    private record ItemToAntiCorruptionItem(Item item, StockItem stockItem) {

        public static ItemToAntiCorruptionItem create(Item item) {
            return new ItemToAntiCorruptionItem(item, new StockItem(item.name, item.sellIn, item.quality));
        }

        public ItemToAntiCorruptionItem withStockItem(StockItem stockItem) {
            return new ItemToAntiCorruptionItem(item, stockItem);
        }

        public void applyChanges() {
            item.quality = stockItem.quality();
            item.sellIn = stockItem.sellIn();
        }

    }


}
