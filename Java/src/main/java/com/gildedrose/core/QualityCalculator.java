package com.gildedrose.core;

@FunctionalInterface
public interface QualityCalculator {
    Integer calculateQuality(StockProperties stockProperties);
}
