package com.gildedrose.core.calculators;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

import static com.gildedrose.GildedRoseConstants.MIN_QUALITY;

public final class DecreaseFasterAfterSellInQualityCalculator implements QualityCalculator {
    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        if (stockProperties.sellIn() > 0) {
            return decreasedItemQualityWithMin0(stockProperties, 1);
        } else {
            return decreasedItemQualityWithMin0(stockProperties, 2);
        }
    }

    private static int decreasedItemQualityWithMin0(StockProperties item, int decrement) {
        if (item.quality() < MIN_QUALITY) {
            //keep original behavior
            return item.quality();
        } else {
            return Math.max(MIN_QUALITY, item.quality() - decrement);
        }
    }
}
