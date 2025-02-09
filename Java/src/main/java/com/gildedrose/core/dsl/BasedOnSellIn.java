package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

import java.util.List;

class BasedOnSellIn implements QualityCalculator {

    private final List<SellInRangePair> pairs;

    public BasedOnSellIn(List<SellInRangePair> pairs) {
        this.pairs = pairs;
    }

    @Override
    public Integer calculateQuality(StockProperties stockProperties) {
        return pairs.stream().filter(it -> it.range().contains(stockProperties.sellIn()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No range found for " + stockProperties.sellIn()))
            .calculator()
            .calculateQuality(stockProperties);
    }


}
