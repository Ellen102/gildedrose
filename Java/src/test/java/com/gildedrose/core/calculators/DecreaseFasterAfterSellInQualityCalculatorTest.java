package com.gildedrose.core.calculators;

import com.gildedrose.core.helpers.StockPropertiesBuilder;
import com.gildedrose.testextensions.SkipWhenStrategyIsDifferentFrom;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.gildedrose.FeatureFlags.ORIGINAL_BEHAVIOUR;
import static org.assertj.core.api.Assertions.assertThat;

class DecreaseFasterAfterSellInQualityCalculatorTest {

    private final DecreaseFasterAfterSellInQualityCalculator calculatorUnderTest = new DecreaseFasterAfterSellInQualityCalculator();

    @Nested
    class PositiveSellIn {

        int positiveSellIn = 5;

        @Test
        void givenAnItemWithQuality10AndSellInPositive_whenUpdateQuality_thenQualityIs9() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 1);
        }

        @Test
        void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs49() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(49);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithQuality51_whenUpdateQuality_thenQualityIs50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(51)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
            int negativeQuality = -1;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(negativeQuality)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(negativeQuality);
        }

    }

    @Nested
    class ZeroSellIn {

        int zeroSellIn = 0;

        @Test
        void givenAnItemWithSellIn0_whenUpdateQuality_thenQualityDecreasesBy2() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 2);
        }

        @Test
        void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs48() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(48);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithQuality51_whenUpdateQuality_thenQualityDecreasesBy2() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(51)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(49);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
            int negativeQuality = -1;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(negativeQuality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(negativeQuality);
        }
    }

    @Nested
    class NegativeSellIn {

        int negativeSellIn = -2;

        @Test
        void givenAnItemWithQuality10AndSellINegative_whenUpdateQuality_thenQualityIs8() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 2);
        }

        @Test
        void givenAnItemWithQuality2_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(2)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs48() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(48);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithQuality51_whenUpdateQuality_thenQualityIs49() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(51)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(49);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithQuality52_whenUpdateQuality_thenQualityIs50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(52)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @Test
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
            int negativeQuality = -1;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(negativeQuality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(negativeQuality);
        }

    }
}
