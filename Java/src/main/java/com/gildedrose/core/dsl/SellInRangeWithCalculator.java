package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;

public record SellInRangeWithCalculator(
    Range range,
    QualityCalculator calculator
) {
}
