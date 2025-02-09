package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

class IncreasingQuality implements QualityCalculator {
    private final int increment;

    public IncreasingQuality(int increment) {
        this.increment = increment;
    }

    public IncreasingQuality() {
        this(1);
    }


    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        return stockProperties.quality() + increment;
    }
}
