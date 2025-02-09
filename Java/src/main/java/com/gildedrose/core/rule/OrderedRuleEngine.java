package com.gildedrose.core.rule;

import com.gildedrose.core.valueobjects.StockName;

import java.util.Collection;

public class OrderedRuleEngine<T> implements RuleEngine<T> {

    protected final Collection<Rule<T>> orderedRules;

    public OrderedRuleEngine(Collection<Rule<T>> orderedRules) {
        this.orderedRules = orderedRules;
    }

    @Override
    public Rule<T> retrieveFirstMatchingRule(StockName stockName) {
        return orderedRules.stream()
            .filter(it -> it.predicate().test(stockName))
            .findFirst()
            .orElseThrow(()-> new IllegalStateException("No rule found for " + stockName));
    }



}
