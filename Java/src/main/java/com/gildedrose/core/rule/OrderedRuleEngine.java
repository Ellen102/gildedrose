package com.gildedrose.core.rule;

import com.gildedrose.core.valueobjects.StockName;

import java.util.Collection;

public class OrderedRuleEngine implements RuleEngine{

    protected final Collection<Rule> orderedRules;

    public OrderedRuleEngine(Collection<Rule> orderedRules) {
        this.orderedRules = orderedRules;
    }

    @Override
    public Rule retrieveFirstMatchingRule(StockName stockName) {
        return orderedRules.stream()
            .filter(it -> it.predicate().test(stockName))
            .findFirst()
            .orElseThrow(()-> new IllegalStateException("No rule found for " + stockName));
    }



}
