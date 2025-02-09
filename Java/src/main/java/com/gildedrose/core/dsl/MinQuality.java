package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

class MinQuality implements QualityCalculator {
    private final int minValue;
    private final QualityCalculator qualityCalculator;

    public MinQuality(int minValue, QualityCalculator rule) {
        this.minValue = minValue;
        this.qualityCalculator = rule;
    }

    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        var intermediateResult = qualityCalculator.calculateQuality(stockProperties);
        return Math.max(minValue, intermediateResult);
    }
}
