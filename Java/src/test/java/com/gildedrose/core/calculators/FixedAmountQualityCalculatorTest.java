package com.gildedrose.core.calculators;

import com.gildedrose.core.helpers.StockPropertiesBuilder;
import com.gildedrose.testextensions.SkipWhenStrategyIsDifferentFrom;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.gildedrose.FeatureFlags.ORIGINAL_BEHAVIOUR;
import static org.assertj.core.api.Assertions.assertThat;

class FixedAmountQualityCalculatorTest {

    private final FixedAmountQualityCalculator calculatorUnderTest = new FixedAmountQualityCalculator();


    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void givenSulfurasWithQuality80AndAnySellIn_whenUpdateQuality_thenQualityRemains80(int sellIn) {
        int quality = 80;
        var item = StockPropertiesBuilder.anItem()
            .withQuality(quality)
            .withSellIn(sellIn)
            .build();

        var nextQuality = calculatorUnderTest.calculateQuality(item);

        assertThat(nextQuality).isEqualTo(quality);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
    void givenSulfurasWithQuality14AndAnySellIn_whenUpdateQuality_thenQualityRemains14(int sellIn) {
        int invalidQuality = 14;
        var item = StockPropertiesBuilder.anItem()
            .withQuality(invalidQuality)
            .withSellIn(sellIn)
            .build();

        var nextQuality = calculatorUnderTest.calculateQuality(item);

        assertThat(nextQuality).isEqualTo(invalidQuality);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
    void givenSulfurasWithQuality0AndAnySellIn_whenUpdateQuality_thenQualityRemains0(int sellIn) {
        int zeroQuality = 0;
        var item = StockPropertiesBuilder.anItem()
            .withQuality(zeroQuality)
            .withSellIn(sellIn)
            .build();

        var nextQuality = calculatorUnderTest.calculateQuality(item);

        assertThat(nextQuality).isEqualTo(zeroQuality);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
    void givenSulfurasWithQualityNegativeAndAnySellIn_whenUpdateQuality_thenQualityRemains0(int sellIn) {
        int negativeQuality = -5;
        var item = StockPropertiesBuilder.anItem()
            .withQuality(negativeQuality)
            .withSellIn(sellIn)
            .build();

        var nextQuality = calculatorUnderTest.calculateQuality(item);

        assertThat(nextQuality).isEqualTo(negativeQuality);
    }


}
