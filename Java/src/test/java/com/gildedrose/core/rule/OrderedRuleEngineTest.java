package com.gildedrose.core.rule;

import com.gildedrose.core.StockName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.gildedrose.core.predicate.StockNamePredicate.ALWAYS_TRUE;
import static com.gildedrose.core.predicate.StockNamePredicate.isExactly;
import static com.gildedrose.core.rule.Rule.rule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderedRuleEngineTest {

    private static OrderedRuleEngine createOrderedRuleEngine(Rule... rules) {
        return new OrderedRuleEngine(
            List.of(
                rules
            )
        );
    }

    private final Rule rule1 = rule(isExactly("1"), stockProperties -> 1);
    private final Rule rule2 = rule(isExactly("2"), stockProperties -> 2);
    private final Rule alwaysTrueRule = rule(ALWAYS_TRUE, stockProperties -> 3);


    @Test
    void givenNoRules_whenRetrieveRule_thenItThrowsException() {
        StockName stockName = StockName.of("no match");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine();
        assertThatThrownBy(() ->
            simpleRuleEngine.retrieveRuleFor(stockName).calculator()
        ).hasMessage("No rule found for `no match`");
    }

    @Test
    void givenSingleMatchingRules_whenRetrieveRule1_thenItReturnsTheMatchingRule() {
        StockName stockName = StockName.of("1");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine(
            rule1,
            rule2
        );

        Rule rule = simpleRuleEngine.retrieveRuleFor(stockName);

        assertThat(rule).isEqualTo(rule1);
    }

    @Test
    void givenSingleMatchingRules_whenRetrieveRule2_thenItReturnsTheMatchingRule() {
        StockName stockName = StockName.of("2");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine(
            rule1,
            rule2
        );

        Rule rule = simpleRuleEngine.retrieveRuleFor(stockName);

        assertThat(rule).isEqualTo(rule2);
    }

    @Test
    void givenMultipleMatchingRules_whenRetrieveRule1_thenItReturnsTheFirstMatchingRule() {
        StockName stockName = StockName.of("1");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine(
            rule1,
            rule2,
            alwaysTrueRule
        );

        Rule rule = simpleRuleEngine.retrieveRuleFor(stockName);

        assertThat(rule).isEqualTo(rule1);
    }

    @Test
    void givenMultipleMatchingRules_whenRetrieveRule2_thenItReturnsTheFirstMatchingRule() {
        StockName stockName = StockName.of("2");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine(
            rule1,
            rule2,
            alwaysTrueRule
        );

        Rule rule = simpleRuleEngine.retrieveRuleFor(stockName);

        assertThat(rule).isEqualTo(rule2);
    }

    @Test
    void givenMultipleRules_whenRetrieveRule3_thenItReturnsTheMatchingRule() {
        StockName stockName = StockName.of("3");

        OrderedRuleEngine simpleRuleEngine = createOrderedRuleEngine(
            rule1,
            rule2,
            alwaysTrueRule
        );

        Rule rule = simpleRuleEngine.retrieveRuleFor(stockName);

        assertThat(rule).isEqualTo(alwaysTrueRule);
    }
}
