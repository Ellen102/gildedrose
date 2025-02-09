package com.gildedrose.core;

import com.gildedrose.core.rule.OrderedRuleEngine;

public class RuleEngineCalculator implements Calculator {

    private final OrderedRuleEngine qualityCalculationRules;

    public RuleEngineCalculator(OrderedRuleEngine qualityCalculationRuleEngine) {
        this.qualityCalculationRules = qualityCalculationRuleEngine;
    }

    @Override
    public StockItem calculateNext(StockItem stockItem) {
        return stockItem.copy(
            stockItem.sellInType().next(),
            qualityCalculationRules.retrieveRuleFor(stockItem.stockName())
                .apply(stockItem.stockProperties())
        );
    }

}
