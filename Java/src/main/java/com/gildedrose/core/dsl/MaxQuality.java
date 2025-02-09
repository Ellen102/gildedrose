package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

class MaxQuality implements QualityCalculator {
    private final int maxValue;
    private final QualityCalculator calculator;

    public MaxQuality(int maxValue, QualityCalculator calculator) {
        this.maxValue = maxValue;
        this.calculator = calculator;
    }

    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        var intermediateResult = calculator.calculateQuality(stockProperties);
        return Math.min(maxValue, intermediateResult);
    }


}
