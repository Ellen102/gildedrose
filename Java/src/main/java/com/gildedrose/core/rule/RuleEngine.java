package com.gildedrose.core.rule;

import com.gildedrose.core.valueobjects.StockName;

public interface RuleEngine {
    Rule retrieveFirstMatchingRule(StockName stockName);
}
