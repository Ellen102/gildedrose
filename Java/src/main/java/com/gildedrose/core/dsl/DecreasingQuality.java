package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

class DecreasingQuality implements QualityCalculator {
    private final int decrement;

    public DecreasingQuality(int decrement) {
        this.decrement = decrement;
    }

    public DecreasingQuality() {
        this(1);
    }


    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        return stockProperties.quality() - decrement;
    }
}
