package com.gildedrose.core.predicate;

import com.gildedrose.core.valueobjects.StockName;

public class IsExactlyStockNamePredicate implements StockNamePredicate {
    private final String exactName;

    public IsExactlyStockNamePredicate(StockName exactName) {
        this.exactName = exactName.value();
    }
    public IsExactlyStockNamePredicate(String exactName) {
        this.exactName = exactName;
    }

    @Override
    public boolean test(StockName stockItem) {
        return stockItem.value().equals(exactName);
    }

}
