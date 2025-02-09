package com.gildedrose.core;

import com.gildedrose.core.rule.RuleEngine;

import static com.gildedrose.GildedRoseConstants.*;
import static com.gildedrose.core.dsl.QualityCalculationDsl.*;
import static com.gildedrose.core.dsl.Range.*;
import static com.gildedrose.core.predicate.StockNamePredicate.IS_EXACTLY_SULFURAS;
import static com.gildedrose.core.predicate.StockNamePredicate.isExactly;

public class DslCalculator implements Calculator {

    private final RuleEngine qualityCalculationRuleEngine;

    public DslCalculator() {
        qualityCalculationRuleEngine = orderedRules(
            whenName(IS_EXACTLY_SULFURAS).calculateQualityAs(always(SULFURAS_QUALITY)),
            whenName(isExactly(AGED_BRIE)).calculateQualityAs(clampedBetween0And50(
                whenSellIn(
                    is(strictPositive()).then(increaseBy(1)),
                    is(negative()).then(increaseBy(2))
                )
            )),
            whenName(isExactly(BACKSTAGE_PASSES)).calculateQualityAs(clampedBetween0And50(
                whenSellIn(
                    is(higherIncluding(11)).then(increaseBy(1)),
                    is(between(6, 10)).then(increaseBy(2)),
                    is(between(1, 5)).then(increaseBy(3)),
                    is(negative()).then(always(0))
                )
            )),
            otherwise(clampedBetween0And50(
                    whenSellIn(
                        is(strictPositive()).then(decreaseBy(1)),
                        is(negative()).then(decreaseBy(2))
                    )
                )
            )

        );
    }


    @Override
    public StockItem calculateNext(StockItem stockItem) {
        return stockItem.copy(
            stockItem.sellInType().next(),
            qualityCalculationRuleEngine.retrieveFirstMatchingRule(stockItem.stockName())
                .apply(stockItem.stockProperties())
        );
    }


}
