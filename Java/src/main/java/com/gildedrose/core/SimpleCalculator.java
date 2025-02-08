package com.gildedrose.core;

import com.gildedrose.core.predicate.IsExactlyPredicate;
import com.gildedrose.core.predicate.StockItemPredicate;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.gildedrose.GildedRoseConstants.*;

public class SimpleCalculator implements Calculator {

    private final Stream<Rule> orderedRules;

    public SimpleCalculator() {
        orderedRules = Stream.of(
            rule(SULFURAS, SimpleCalculator::calculateSulfurasQuality),
            rule(AGED_BRIE, SimpleCalculator::calculateSulfurasQuality),
            rule(BACKSTAGE_PASSES, SimpleCalculator::calculateSulfurasQuality),
            always(SimpleCalculator::calculateSulfurasQuality)
        );
    }

    @Override
    public StockItem calculateNext(StockItem stockItem) {
        return stockItem.copy(
            calculateNextSellIn(stockItem),
            calculateNextQuality(stockItem)
        );
    }

    record Rule(StockItemPredicate predicate, Function<StockItem, Integer> calculate) {
    }

    private static Rule rule(String name, Function<StockItem, Integer> calculate) {
        return new Rule(new IsExactlyPredicate(name), calculate);
    }

    private static Rule always(Function<StockItem, Integer> calculate) {
        return new Rule(StockItemPredicate.ALWAYS_TRUE, calculate);
    }


    private static int calculateNextQuality(StockItem item) {
        switch (item.name()) {
            case SULFURAS -> {
                return calculateSulfurasQuality(item);
            }

            case AGED_BRIE -> {
                return calculateAgedBrieQuality(item);
            }

            case BACKSTAGE_PASSES -> {
                return calculateBackstagePassesQuality(item);
            }

            default -> {
                return calculateDefaultQuality(item);
            }
        }
    }

    private static int calculateDefaultQuality(StockItem item) {
        if (item.sellIn() > 0) {
            return decreasedItemQualityWithMin0(item, 1);
        } else {
            return decreasedItemQualityWithMin0(item, 2);
        }
    }

    private static int calculateBackstagePassesQuality(StockItem item) {
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

    private static int calculateAgedBrieQuality(StockItem item) {
        if (item.sellIn() > 0) {
            return increasedItemQualityWithMax50(item, 1);
        } else {
            return increasedItemQualityWithMax50(item, 2);
        }
    }

    private static int calculateSulfurasQuality(StockItem item) {
        return item.quality();
    }

    private static int decreasedItemQualityWithMin0(StockItem item, int decrement) {
        if (item.quality() < 0) {
            //keep same behavior
            return item.quality();
        } else {
            return Math.max(0, item.quality() - decrement);
        }
    }

    private static int increasedItemQualityWithMax50(StockItem item, int increment) {
        if (item.quality() > 50) {
            //keep same behavior
            return item.quality();
        } else {
            return Math.min(50, item.quality() + increment);
        }
    }

    private static int calculateNextSellIn(StockItem item) {
        if (SULFURAS.equals(item.name())) {
            return item.sellIn();
        } else {
            return item.sellIn() - 1;
        }
    }
}
