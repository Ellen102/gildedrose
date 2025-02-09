package com.gildedrose.core.dsl;

import com.gildedrose.GildedRoseConstants;
import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.predicate.StockNamePredicate;
import com.gildedrose.core.rule.OrderedRuleEngine;
import com.gildedrose.core.rule.Rule;
import com.gildedrose.core.rule.RuleEngine;

import java.util.List;

public class QualityCalculationDsl {
    private QualityCalculationDsl() {

    }

    // Rule creation
    public static RuleEngine orderedRules(Rule... rules) {
        return new OrderedRuleEngine(List.of(rules));
    }

    public record RuleToCalculate(StockNamePredicate predicate) {
        public Rule calculateQualityAs(QualityCalculator calculator) {
            return new Rule(predicate, calculator);
        }
    }

    public static RuleToCalculate whenName(StockNamePredicate namePredicate) {
        return new RuleToCalculate(namePredicate);
    }

    public static Rule otherwise(QualityCalculator qualityCalculator) {
        return new Rule(StockNamePredicate.ALWAYS_TRUE, qualityCalculator);
    }

    /// Calculators

    public static QualityCalculator always(int constantValue) {
        return new Always(constantValue);
    }

    public static QualityCalculator clampedBetween0And50(QualityCalculator calculator) {
        return new MaxQuality(GildedRoseConstants.MAX_QUALITY, new MinQuality(GildedRoseConstants.MIN_QUALITY, calculator));
    }

    public static QualityCalculator increaseBy(int increment) {
        return new IncreasingQuality(increment);
    }

    public static QualityCalculator decreaseBy(int increment) {
        return new DecreasingQuality(increment);
    }

    public record SellInRange(
        Range range
    ) {
        public SellInRangeWithCalculator then(QualityCalculator calculator) {
            return new SellInRangeWithCalculator(range, calculator);
        }
    }

    public static SellInRange is(Range range) {
        return new SellInRange(range);
    }

    public static QualityCalculator whenSellIn(SellInRangeWithCalculator... pairs) {
        return new BasedOnSellIn(List.of(pairs));
    }
}
