package com.gildedrose.core.rule;

import com.gildedrose.core.valueobjects.StockName;

public interface RuleEngine<T> {
    Rule<T> retrieveFirstMatchingRule(StockName stockName);
}
