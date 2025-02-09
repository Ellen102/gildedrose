package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;

public record SellInRangePair(
    Range range,
    QualityCalculator calculator
) {
}
