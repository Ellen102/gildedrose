package com.gildedrose.core.rule;


import com.gildedrose.core.StockName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OrderedRuleEngine implements RuleEngine {

    protected final Collection<Rule> orderedRules;

    public OrderedRuleEngine(List<Rule> orderedRules) {
        this.orderedRules = Collections.unmodifiableList(orderedRules);
    }

    @Override
    public Rule retrieveFirstMatchingRule(StockName stockName) {
        return orderedRules.stream()
            .filter(it -> it.predicate().test(stockName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No rule found for " + stockName));
    }
}
