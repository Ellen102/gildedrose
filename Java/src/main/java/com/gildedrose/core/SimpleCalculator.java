package com.gildedrose.core;

import com.gildedrose.core.calculators.BackstagePassRangedQualityCalculator;
import com.gildedrose.core.calculators.DecreaseFasterAfterSellInQualityCalculator;
import com.gildedrose.core.calculators.FixedAmountQualityCalculator;
import com.gildedrose.core.calculators.IncreaseFasterAfterSellInQualityCalculator;
import com.gildedrose.core.rule.OrderedRuleEngine;

import java.util.List;

import static com.gildedrose.GildedRoseConstants.*;
import static com.gildedrose.core.predicate.StockNamePredicate.isExactly;
import static com.gildedrose.core.rule.Rule.otherwise;
import static com.gildedrose.core.rule.Rule.rule;

public class SimpleCalculator implements Calculator {

    private final OrderedRuleEngine qualityCalculationRuleEngine;

    public SimpleCalculator() {
        qualityCalculationRuleEngine = new OrderedRuleEngine(
            List.of(
                rule(isExactly(SULFURAS), new FixedAmountQualityCalculator()),
                rule(isExactly(AGED_BRIE), new IncreaseFasterAfterSellInQualityCalculator()),
                rule(isExactly(BACKSTAGE_PASSES), new BackstagePassRangedQualityCalculator()),
                otherwise(new DecreaseFasterAfterSellInQualityCalculator())
            )
        );
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
