package com.gildedrose.core;

import com.gildedrose.core.calculators.*;
import com.gildedrose.core.rule.OrderedRuleEngine;
import com.gildedrose.core.rule.Rule;

import java.util.List;

import static com.gildedrose.GildedRoseConstants.*;
import static com.gildedrose.core.predicate.StockNamePredicate.isExactly;
import static com.gildedrose.core.rule.Rule.otherwise;
import static com.gildedrose.core.rule.Rule.rule;

public class SimpleRuleEngine extends OrderedRuleEngine {
    public SimpleRuleEngine() {
        super(createOrderedRules());
    }

    private static List<Rule> createOrderedRules() {
        return
            List.of(
                rule(isExactly(SULFURAS), new FixedAmountQualityCalculator()),
                rule(isExactly(AGED_BRIE), new IncreaseFasterAfterSellInQualityCalculator()),
                rule(isExactly(BACKSTAGE_PASSES), new BackstagePassRangedQualityCalculator()),
                rule(isExactly(CONJURED), new DecreaseTwiceAsFastQualityCalculator()),
                otherwise(new DecreaseFasterAfterSellInQualityCalculator())
            );
    }
}
