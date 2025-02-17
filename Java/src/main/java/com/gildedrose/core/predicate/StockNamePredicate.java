package com.gildedrose.core.predicate;


import com.gildedrose.core.StockName;

import java.util.function.Predicate;

import static com.gildedrose.GildedRoseConstants.SULFURAS;

public interface StockNamePredicate extends Predicate<StockName> {
    StockNamePredicate ALWAYS_TRUE = (item) -> true;

    StockNamePredicate IS_EXACTLY_SULFURAS = isExactly(SULFURAS);

    public static StockNamePredicate isExactly(String name) {
        return new IsExactlyStockNamePredicate(name);
    }

}
