package com.gildedrose.core;

import com.gildedrose.core.helpers.StockItemBuilder;
import com.gildedrose.core.validator.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ValidatingCalculatorTest {


    @Nested
    class DelegateIsCalled {
        private static class SpyCalculator implements Calculator {
            StockItem argument = null;

            @Override
            public StockItem calculateNext(StockItem stockItem) {
                this.argument = stockItem;
                return stockItem;
            }

        }

        @ParameterizedTest
        @ValueSource(booleans = {true, false})
        void givenAnValidItem_whenCalculateNext_thenItCallsTheDelegateWithoutChangingTheItem(boolean onErrorThrow) {
            var spyCalculator = new SpyCalculator();
            var item = StockItemBuilder.anItem()
                .build();

            new ValidatingCalculator(spyCalculator, onErrorThrow).calculateNext(item);

            assertThat(spyCalculator.argument).isEqualTo(item);
        }

        @Test
        void givenErrorThrowingDisabledAndAnInValidItem_whenCalculateNext_thenItCallsTheDelegateWithoutChangingTheItem() {
            var spyCalculator = new SpyCalculator();
            var item = StockItemBuilder.anItem()
                .build();

            new ValidatingCalculator(spyCalculator).calculateNext(item);

            assertThat(spyCalculator.argument).isEqualTo(item);
        }
    }

    @Nested
    class ValidationRules {

        Calculator delegateCalculator;
        Calculator underTest;

        @BeforeEach
        void setUp() {
            delegateCalculator = stockItem -> stockItem;
            underTest = new ValidatingCalculator(delegateCalculator, true);
        }


        @Test
        void givenAnValidITem_whenCalculateNext_thenItDoesNotThrow() {
            var item = StockItemBuilder.anItem()
                .build();

            Assertions.assertDoesNotThrow(() -> underTest.calculateNext(item));
        }


        @ParameterizedTest
        @ValueSource(ints = {0, 1, 5, 10, 50})
        void givenAnValidItemWithValidQuality_whenCalculateNext_thenItDoesNotThrow() {
            var item = StockItemBuilder.anItem()
                .withQuality(0)
                .build();

            Assertions.assertDoesNotThrow(() -> underTest.calculateNext(item));
        }

        @Test
        void givenAnValidItemWithNegativeQuality_whenCalculateNext_thenItDoesThrowValidationException() {
            var item = StockItemBuilder.anItem()
                .withQuality(-6)
                .build();

            Assertions.assertThrows(ValidationException.class, () -> underTest.calculateNext(item));
        }


        @Test
        void givenAnValidItemWithQualityHigherThan50_whenCalculateNext_thenItDoesThrowValidationException() {
            var item = StockItemBuilder.anItem()
                .withQuality(51)
                .build();

            Assertions.assertThrows(ValidationException.class, () -> underTest.calculateNext(item));
        }


        @Nested
        class Sulfuras {

            @ParameterizedTest(name = "given an sulfuras item with quality {0}, it throws a validation exception")
            @ValueSource(ints = {0, 1, 5, 10, 50})
            void givenAnSulfurasItemWithInvalidQuality_whenCalculateNext_thenItDoesThrowValidationException(int quality) {
                var item = StockItemBuilder.anSulfurasItem()
                    .withQuality(quality)
                    .build();

                Assertions.assertThrows(ValidationException.class, () -> underTest.calculateNext(item));
            }

            @Test
            void givenAnSulfurasItemWithQuality80_whenCalculateNext_thenItDoesNotThrow() {
                var item = StockItemBuilder.anSulfurasItem()
                    .withQuality(80)
                    .build();

                Assertions.assertDoesNotThrow(() -> underTest.calculateNext(item));
            }
        }
    }
}
