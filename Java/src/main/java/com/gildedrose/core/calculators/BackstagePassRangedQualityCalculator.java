package com.gildedrose.core.calculators;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

import static com.gildedrose.GildedRoseConstants.MAX_QUALITY;

public class BackstagePassRangedQualityCalculator implements QualityCalculator {
    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        if (stockProperties.sellIn() >= 11) {
            return increasedItemQualityWithMax50(stockProperties, 1);
        } else if (6 <= stockProperties.sellIn()) {
            return increasedItemQualityWithMax50(stockProperties, 2);
        } else if (0 < stockProperties.sellIn()) {
            return increasedItemQualityWithMax50(stockProperties, 3);
        } else {
            return 0;
        }
    }

    private static int increasedItemQualityWithMax50(StockProperties item, int increment) {
        if (item.quality() > MAX_QUALITY) {
            //keep original behavior
            return item.quality();
        } else {
            return Math.min(MAX_QUALITY, item.quality() + increment);
        }
    }
}
