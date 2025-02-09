package com.gildedrose.core.calculators;

import com.gildedrose.core.helpers.StockPropertiesBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DecreaseTwiceAsFastQualityCalculatorTest {
    private final DecreaseTwiceAsFastQualityCalculator calculatorUnderTest = new DecreaseTwiceAsFastQualityCalculator();

    @Nested
    class PositiveSellIn {

        int positiveSellIn = 5;

        @Test
        void givenAnItemWithQuality10AndSellInPositive_whenCalculateQuality_thenQualityIs9() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 2);
        }


        @Test
        void givenAnItemWithQuality2_whenCalculateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(2)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality1_whenCalculateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality0_whenCalculateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(0)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

        @Test
        void givenAnItemWithQuality50_whenCalculateQuality_thenQualityIs49() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(50)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(48);
        }

        @Test
        void givenAnItemWithQuality51_whenCalculateQuality_thenQualityIs49() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(51)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(49);
        }

        @Test
        void givenAnItemWithNegativeQuality_whenCalculateQuality_thenQualityRemainsTheSame() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(-1)
                .withSellIn(positiveSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

    }

    @Nested
    class ZeroSellIn {

        int zeroSellIn = 0;

        @Test
        void givenAnItemWithSellIn0_whenCalculateQuality_thenQualityDecreasesBy4() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 4);
        }

        @ParameterizedTest(name = "given an item with quality {0} then the calculated quality is 0")
        @ValueSource(ints = {0, 1, 2, 3, 4})
        void givenAnItemWithQualityCloseToZero_whenCalculateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }


        @Test
        void givenAnItemWithQuality55_whenCalculateQuality_thenQualityIs50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(55)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @ParameterizedTest
        @ValueSource(ints = {50, 51, 52, 53, 54})
        void givenAnItemWithQuality51_whenCalculateQuality_thenQualityDecreasesBy4(int quality) {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 4);
        }

        @Test
        void givenAnItemWithNegativeQuality_whenCalculateQuality_thenQualityRemainsTheSame() {
            int negativeQuality = -1;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(negativeQuality)
                .withSellIn(zeroSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }
    }

    @Nested
    class NegativeSellIn {

        int negativeSellIn = -2;

        @Test
        void givenAnItemWithSellIn0Negateive_whenCalculateQuality_thenQualityDecreasesBy4() {
            int quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 4);
        }

        @ParameterizedTest(name = "given an item with quality {0} then the calculated quality is 0")
        @ValueSource(ints = {0, 1, 2, 3, 4})
        void givenAnItemWithQualityCloseToZero_whenCalculateQuality_thenQualityIs0() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(1)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }


        @Test
        void givenAnItemWithQuality55_whenCalculateQuality_thenQualityIs50() {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(55)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(50);
        }

        @ParameterizedTest
        @ValueSource(ints = {50, 51, 52, 53, 54})
        void givenAnItemWithQuality51_whenCalculateQuality_thenQualityDecreasesBy4(int quality) {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality - 4);
        }

        @Test
        void givenAnItemWithNegativeQuality_whenCalculateQuality_thenQualityRemainsTheSame() {
            int negativeQuality = -1;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(negativeQuality)
                .withSellIn(negativeSellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }
    }
}
