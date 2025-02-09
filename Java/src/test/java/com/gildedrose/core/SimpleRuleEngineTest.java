package com.gildedrose.core;

import com.gildedrose.core.calculators.BackstagePassRangedQualityCalculator;
import com.gildedrose.core.calculators.DecreaseFasterAfterSellInQualityCalculator;
import com.gildedrose.core.calculators.FixedAmountQualityCalculator;
import com.gildedrose.core.calculators.IncreaseFasterAfterSellInQualityCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.gildedrose.GildedRoseConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleRuleEngineTest {

    private final SimpleRuleEngine simpleRuleEngine = new SimpleRuleEngine();

    @Test
    void givenSulfuras_whenRetrieveFirstMatchingRule_thenItHasFixedAmountQualityCalculator() {
        StockName stockName = StockName.of(SULFURAS);

        QualityCalculator calculator = simpleRuleEngine.retrieveFirstMatchingRule(stockName).calculator();

        assertThat(calculator).isExactlyInstanceOf(FixedAmountQualityCalculator.class);
    }

    @Test
    void givenAgedBrie_whenRetrieveFirstMatchingRule_thenItHasIncreaseFasterAfterSellInQualityCalculator() {
        StockName stockName = StockName.of(AGED_BRIE);

        QualityCalculator calculator = simpleRuleEngine.retrieveFirstMatchingRule(stockName).calculator();

        assertThat(calculator).isExactlyInstanceOf(IncreaseFasterAfterSellInQualityCalculator.class);
    }

    @Test
    void givenBackstagePasses_whenRetrieveFirstMatchingRule_thenItHasBackstagePassRangedQualityCalculator() {
        StockName stockName = StockName.of(BACKSTAGE_PASSES);

        QualityCalculator calculator = simpleRuleEngine.retrieveFirstMatchingRule(stockName).calculator();

        assertThat(calculator).isExactlyInstanceOf(BackstagePassRangedQualityCalculator.class);
    }

    @ParameterizedTest(name = "For stockname {0} the default calculator is returned")
    @ValueSource(strings = {"foo", "Not sulfuras", "other backstage passes", "Normal cheese"})
    void givenAnotherName_whenRetrieveFirstMatchingRule_thenItHasDecreaseFasterAfterSellInQualityCalculator(String name) {
        StockName stockName = StockName.of(name);

        QualityCalculator calculator = simpleRuleEngine.retrieveFirstMatchingRule(stockName).calculator();

        assertThat(calculator).isExactlyInstanceOf(DecreaseFasterAfterSellInQualityCalculator.class);
    }

}
