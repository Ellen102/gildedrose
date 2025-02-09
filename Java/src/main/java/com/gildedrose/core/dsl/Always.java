package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

class Always implements QualityCalculator {
    private final int value;

    public Always(int value) {
        this.value = value;
    }

    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        return value;
    }
}
