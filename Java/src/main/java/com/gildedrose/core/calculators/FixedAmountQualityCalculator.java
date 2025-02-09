package com.gildedrose.core.calculators;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

public final class FixedAmountQualityCalculator implements QualityCalculator {

    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        return stockProperties.quality();
    }
}
