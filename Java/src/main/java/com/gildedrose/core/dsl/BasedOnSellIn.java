package com.gildedrose.core.dsl;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockProperties;

import java.util.Collections;
import java.util.List;

class BasedOnSellIn implements QualityCalculator {

    private final List<SellInRangeWithCalculator> pairs;

    public BasedOnSellIn(List<SellInRangeWithCalculator> pairs) {
        this.pairs = Collections.unmodifiableList(pairs);
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
