package com.gildedrose.core.rule;

import com.gildedrose.core.StockProperties;
import com.gildedrose.core.predicate.IsExactlyStockNamePredicate;
import com.gildedrose.core.predicate.StockNamePredicate;
import com.gildedrose.core.valueobjects.StockName;

import java.util.function.Function;
import java.util.function.Predicate;

public record Rule<T>(Predicate<StockName> predicate, Function<StockProperties, T> calculateFunction) {

    public T apply(StockProperties properties) {
        return calculateFunction.apply(properties);
    }

    //public methods
    public static <T> Rule<T> rule(String name, Function<StockProperties, T> calculate) {
        return new Rule<T>(new IsExactlyStockNamePredicate(name), calculate);
    }

    public static <T> Rule<T> otherwise(Function<StockProperties, T> calculate) {
        return new Rule<>(StockNamePredicate.ALWAYS_TRUE, calculate);
    }

}
