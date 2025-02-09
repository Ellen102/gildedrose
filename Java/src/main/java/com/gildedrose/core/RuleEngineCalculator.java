package com.gildedrose.core;

import com.gildedrose.core.rule.OrderedRuleEngine;

public class RuleEngineCalculator implements Calculator {

    private final OrderedRuleEngine qualityCalculationRuleEngine;

    public RuleEngineCalculator(OrderedRuleEngine qualityCalculationRuleEngine) {
        this.qualityCalculationRuleEngine = qualityCalculationRuleEngine;
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
