package com.gildedrose.core.rule;


import com.gildedrose.core.StockName;

public interface RuleEngine {
    Rule retrieveFirstMatchingRule(StockName stockName);
}
