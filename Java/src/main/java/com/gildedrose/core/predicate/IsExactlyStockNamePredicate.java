package com.gildedrose.core.predicate;


import com.gildedrose.core.StockName;

public class IsExactlyStockNamePredicate implements StockNamePredicate {
    private final String exactName;

    public IsExactlyStockNamePredicate(String exactName) {
        this.exactName = exactName;
    }

    @Override
    public boolean test(StockName stockItem) {
        return stockItem.value().equals(exactName);
    }

}
