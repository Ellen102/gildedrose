package com.gildedrose.core.calculators;

import com.gildedrose.core.helpers.StockPropertiesBuilder;
import com.gildedrose.testextensions.SkipWhenStrategyIsDifferentFrom;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.gildedrose.FeatureFlags.ORIGINAL_BEHAVIOUR;
import static org.assertj.core.api.Assertions.assertThat;

class IncreaseFasterAfterSellInQualityCalculatorTest {

    private final IncreaseFasterAfterSellInQualityCalculator calculatorUnderTest = new IncreaseFasterAfterSellInQualityCalculator();


    @Nested
    class PositiveSellIn {

        int positiveSellIn = 5;

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBrieWithQualityNegativeAndPositiveSellIn_whenUpdateQuality_thenQualityIncreasesBy1() {
            int quality = -5;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(1)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 1);
        }

        @Test
        void givenBrieWithQuality50AndPositiveSellIn_whenUpdateQuality_thenQualityRemains50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        void givenBrieWithQuality0AndPositiveSellIn_whenUpdateQuality_thenQualityBecomes1() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(1)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(1);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBrieWithQuality55AndSellInPositive_whenUpdateQuality_thenQualityRemains55() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(55)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(55);
        }
    }

    @Nested
    class ZeroSellIn {

        int zeroSellIn = 0;

        @Test
        void givenBrieWithQuality5AndSellIn0_whenUpdateQuality_thenQualityIncreasesBy2() {
            int quality = 5;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 2);
        }

        @Test
        void givenBrieWithQuality0AndSellIn0_whenUpdateQuality_thenQualityBecomes2() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(2);
        }

        @Test
        void givenBrieWithQuality50AndSellIn0_whenUpdateQuality_thenQualityRemains50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBrieWithQualityNegativeAndSellIn0_whenUpdateQuality_thenQualityIncreasesBy2() {
            int quality = -5;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 2);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBrieWithQuality55AndSellIn0_whenUpdateQuality_thenQualityRemains55() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(55)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(55);
        }
    }

    @Nested
    class NegativeSellIn {
        int negativeSellIn = -5;

        @Test
        void givenBrieWithQuality5AndSellInNegative_whenUpdateQuality_thenQualityIncreasesBy2() {
            int quality = 5;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 2);
        }

        @Test
        void givenBrieWithQuality50AndSellInNegative_whenUpdateQuality_thenQualityRemains50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        void givenBrieWithQuality49AndSellInNegative_whenUpdateQuality_thenQualityBecomes50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(49)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        void givenBrieWithQuality48AndSellInNegative_whenUpdateQuality_thenQualityBecomes50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(49)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        void givenBrieWithQuality0AndSellInMinus1_whenUpdateQuality_thenQualityBecomes1() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(2);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBrieWithQuality55AndSellInNegative_whenUpdateQuality_thenQualityRemains55() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(55)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(55);
        }

    }
}
