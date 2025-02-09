package com.gildedrose.core.calculators;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

import static com.gildedrose.GildedRoseConstants.MAX_QUALITY;
import static com.gildedrose.GildedRoseConstants.MIN_QUALITY;

public final class DecreaseTwiceAsFastQualityCalculator implements QualityCalculator {
    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        if (stockProperties.sellIn() > 0) {
            return decreasedItemQualityWithMin0(stockProperties, 2);
        } else {
            return decreasedItemQualityWithMin0(stockProperties, 4);
        }
    }

    private static int clamp(int quality) {
        return Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, quality));
    }

    private static int decreasedItemQualityWithMin0(StockProperties item, int decrement) {
        return clamp(item.quality() - decrement);
    }
}
