package com.gildedrose.core.calculators;

import com.gildedrose.GildedRoseConstants;
import com.gildedrose.core.helpers.StockPropertiesBuilder;
import com.gildedrose.testextensions.SkipWhenStrategyIsDifferentFrom;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.gildedrose.FeatureFlags.ORIGINAL_BEHAVIOUR;
import static org.assertj.core.api.Assertions.assertThat;

class BackstagePassRangedQualityCalculatorTest {
    private final BackstagePassRangedQualityCalculator calculatorUnderTest = new BackstagePassRangedQualityCalculator();

    @Nested
    class PositiveSellIn {

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5})
        void givenBackstagePassWithQuality10AndSellInLessThan5_whenUpdateQuality_thenQualityIncreasesBy3(int sellIn) {
            var quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(sellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 3);
        }


        @ParameterizedTest
        @ValueSource(ints = {6, 7, 8, 9, 10})
        void givenBackstagePassWithQuality10AndSellInLessThan10_whenUpdateQuality_thenQualityIncreasesBy2(int sellIn) {
            var quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(sellIn)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 2);
        }


        @Test
        void givenBackstagePassWithSellIn11_whenUpdateQuality_thenQualityIncreasesBy1() {
            var quality = 10;
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(11)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(quality + 1);
        }

        @Nested
        class SellInIsMax50 {

            @ParameterizedTest
            @ValueSource(ints = {1, 4, 5, 6, 9, 10, 11})
            void givenBackstagePassWithQuality50AndSellInPositive_whenUpdateQuality_thenQualityRemains50(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(50)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(50);
            }

            @ParameterizedTest
            @ValueSource(ints = {1, 4, 5, 6, 9, 10, 11})
            void givenBackstagePassWithQuality49AndSellInPositive_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(49)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(50);
            }

            @ParameterizedTest
            @ValueSource(ints = {1, 4, 5, 6, 9, 10})
            void givenBackstagePassWithQuality48AndSellInLessThan10_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(48)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(50);
            }


            @ParameterizedTest
            @ValueSource(ints = {1, 4, 5})
            void givenBackstagePassWithQuality49AndSellInLessThan5_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(47)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(50);
            }
        }

        @Nested
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        class NegativeQuality {

            final int quality = -9;

            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5})
            void givenBackstagePassWithQuality10AndSellInLessThan5_whenUpdateQuality_thenQualityIncreasesBy3(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(quality)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(quality + 3);
            }


            @ParameterizedTest
            @ValueSource(ints = {6, 7, 8, 9, 10})
            void givenBackstagePassWithQuality10AndSellInLessThan10_whenUpdateQuality_thenQualityIncreasesBy2(int sellIn) {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(quality)
                    .withSellIn(sellIn)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(quality + 2);
            }


            @Test
            void givenBackstagePassWithSellIn11_whenUpdateQuality_thenQualityIncreasesBy1() {
                var item = StockPropertiesBuilder.anItem()
                    .withQuality(quality)
                    .withSellIn(11)
                    .build();

                var nextQuality = calculatorUnderTest.calculateQuality(item);

                assertThat(nextQuality).isEqualTo(quality + 1);
            }
        }
    }


    @Nested
    class ZeroAndNegativeSellIn {

        @ParameterizedTest(name = "Backstage pass with quality {0} becomes 0 when the sell in is 0")
        @ValueSource(ints = {55, 50, 10, 1, 0, -9})
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBackstagePassWithSellIn0_whenUpdateQuality_thenQualityBecomes0(int quality) {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(0)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }


        @ParameterizedTest(name = "Backstage pass with quality {0} becomes 0 when the sell in is negative")
        @ValueSource(ints = {55, 50, 10, 1, 0, -9})
        @SkipWhenStrategyIsDifferentFrom(strategy = ORIGINAL_BEHAVIOUR)
        void givenBackstagePassWithSellInNegative_whenUpdateQuality_thenQualityBecomes0(int quality) {
            var item = StockPropertiesBuilder.anItem()
                .withQuality(quality)
                .withSellIn(-1)
                .build();

            var nextQuality = calculatorUnderTest.calculateQuality(item);

            assertThat(nextQuality).isEqualTo(0);
        }

    }


}
