package com.gildedrose.core;

import com.gildedrose.core.rule.OrderedRuleEngine;

public class SimpleCalculator implements Calculator {

    private final OrderedRuleEngine qualityCalculationRuleEngine;

    public SimpleCalculator() {
        qualityCalculationRuleEngine = new SimpleRuleEngine();
    }

    @Override
    public StockItem calculateNext(StockItem stockItem) {
        return stockItem.copy(
            stockItem.sellInType().next(),
            calculateNextQuality(stockItem)
        );
    }

    private int calculateNextQuality(StockItem item) {
        return qualityCalculationRuleEngine.retrieveFirstMatchingRule(item.stockName())
            .apply(item.stockProperties());
    }

}
