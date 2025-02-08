package com.gildedrose.core.predicate;

import com.gildedrose.core.StockItem;

public class IsExactlyPredicate implements StockItemPredicate {
    private final String exactName;

    public IsExactlyPredicate(String exactName) {
        this.exactName = exactName;
    }

    @Override
    public boolean test(StockItem stockItem) {
        return stockItem.name().equals(exactName);
    }
}
