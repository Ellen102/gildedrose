package com.gildedrose.core.rule;


import com.gildedrose.core.StockName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OrderedRuleEngine{

    protected final Collection<Rule> orderedRules;

    public OrderedRuleEngine(List<Rule> orderedRules) {
        this.orderedRules = Collections.unmodifiableList(orderedRules);
    }

    public Rule retrieveRuleFor(StockName stockName) {
        return orderedRules.stream()
            .filter(it -> it.predicate().test(stockName))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No rule found for `%s`".formatted( stockName.value())));
    }
}
