package com.gildedrose.core.rule;

import com.gildedrose.core.QualityCalculator;
import com.gildedrose.core.StockName;
import com.gildedrose.core.StockProperties;
import com.gildedrose.core.predicate.StockNamePredicate;

import java.util.function.Predicate;

public record Rule(Predicate<StockName> predicate, QualityCalculator calculator) {

    public Integer apply(StockProperties properties) {
        return calculator.calculateQuality(properties);
    }

    //public methods

    public static Rule rule(Predicate<StockName> predicate, QualityCalculator calculate) {
        return new Rule(predicate, calculate);
    }

    public static Rule otherwise(QualityCalculator calculate) {
        return new Rule(StockNamePredicate.ALWAYS_TRUE, calculate);
    }

}
