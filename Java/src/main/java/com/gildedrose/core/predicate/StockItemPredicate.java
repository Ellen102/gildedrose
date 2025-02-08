package com.gildedrose.core.predicate;

import com.gildedrose.core.StockItem;

import java.util.function.Predicate;

import static com.gildedrose.GildedRoseConstants.SULFURAS;

public interface StockItemPredicate extends Predicate<StockItem> {
    StockItemPredicate ALWAYS_TRUE = (item) -> true;
    StockItemPredicate IS_EXACTLY_SULFURAS = new IsExactlyPredicate(SULFURAS);

}
