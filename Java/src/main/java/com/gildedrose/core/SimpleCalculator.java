package com.gildedrose.core;

import com.gildedrose.core.rule.OrderedRuleEngine;

import java.util.List;

import static com.gildedrose.GildedRoseConstants.*;
import static com.gildedrose.core.rule.Rule.otherwise;
import static com.gildedrose.core.rule.Rule.rule;

public class SimpleCalculator implements Calculator {

    private final OrderedRuleEngine<Integer> qualityCalculationRuleEngine;

    public SimpleCalculator() {
        //this can be moved to separate calculators and tested indivudally. but i want to wriste a dsl :p
        qualityCalculationRuleEngine = new OrderedRuleEngine<>(
            List.of(
                rule(SULFURAS, SimpleCalculator::calculateSulfurasNextQuality),
                rule(AGED_BRIE, SimpleCalculator::calculateAgedBrieNextQuality),
                rule(BACKSTAGE_PASSES, SimpleCalculator::calculateBackstagePassesNextQuality),
                otherwise(SimpleCalculator::calculateDefaultNextQuality)
            )
        );
    }

    @Override
    public StockItem calculateNext(StockItem stockItem) {
        return stockItem.copy(
            stockItem.sellInType().next(),
            calculateNextQuality(stockItem)
        );
    }


    private int calculateNextQuality(StockItem item) {
        return qualityCalculationRuleEngine.retrieveFirstMatchingRule(item.asStockName())
            .apply(item.asStockProperties());
    }

    private static int calculateDefaultNextQuality(StockProperties item) {
        if (item.sellIn() > 0) {
            return decreasedItemQualityWithMin0(item, 1);
        } else {
            return decreasedItemQualityWithMin0(item, 2);
        }
    }

    private static int calculateBackstagePassesNextQuality(StockProperties item) {
        if (item.sellIn() >= 11) {
            return increasedItemQualityWithMax50(item, 1);
        } else if (6 <= item.sellIn()) {
            return increasedItemQualityWithMax50(item, 2);
        } else if (0 < item.sellIn()) {
            return increasedItemQualityWithMax50(item, 3);
        } else {
            return 0;
        }
    }

    private static int calculateAgedBrieNextQuality(StockProperties item) {
        if (item.sellIn() > 0) {
            return increasedItemQualityWithMax50(item, 1);
        } else {
            return increasedItemQualityWithMax50(item, 2);
        }
    }

    private static int calculateSulfurasNextQuality(StockProperties item) {
        return item.quality();
    }

    private static int decreasedItemQualityWithMin0(StockProperties item, int decrement) {
        if (item.quality() < MIN_QUALITY) {
            //keep same behavior
            return item.quality();
        } else {
            return Math.max(MIN_QUALITY, item.quality() - decrement);
        }
    }

    private static int increasedItemQualityWithMax50(StockProperties item, int increment) {
        if (item.quality() > MAX_QUALITY) {
            //keep same behavior
            return item.quality();
        } else {
            return Math.min(MAX_QUALITY, item.quality() + increment);
        }
    }

}
