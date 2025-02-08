package com.gildedrose;

import com.gildedrose.helpers.ItemBuilder;
import com.gildedrose.helpers.ItemListBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GildedRoseTest {

    private static void updateQuality(Item[] items) {
        //Items are updated inplace
        new GildedRose(items).updateQuality();
    }

    private static void updateQuality(Item item) {
        var items = new Item[]{item};
        new GildedRose(items).updateQuality();
    }

    @Nested
    class ItemsListAndNames {

        @Test
        void givenThereAreNoItems_whenUpdateQuality_thenThereShouldBeNoItems() {
            var noItems = ItemListBuilder.anEmptyItemList()
                .build();

            updateQuality(noItems);

            Assertions.assertThat(noItems).hasSize(0);
        }

        @Test
        void givenMultipleItems_whenUpdateQuality_thenTheSameAmountOfItemsShouldBePresent() {
            var multipleItems = ItemListBuilder.anEmptyItemList()
                .withItem("foo")
                .withItem(GildedRoseConstants.BACKSTAGE_PASSES)
                .withItem(GildedRoseConstants.AGED_BRIE)
                .withItem(GildedRoseConstants.SULFURAS)
                .withItem("Rainbow powder")
                .build();

            updateQuality(multipleItems);

            Assertions.assertThat(multipleItems).hasSize(5);
        }


        @Test
        void givenDuplicateItems_whenUpdateQuality_thenTheDuplicatesRemainPresent() {
            var multipleItems = ItemListBuilder.anEmptyItemList()
                .withItem("foo")
                .withItem("foo")
                .build();

            updateQuality(multipleItems);

            Assertions.assertThat(multipleItems).hasSize(2);
        }

        @Test
        void givenOneItem_whenUpdateQuality_thenItemShouldStillBePresent() {
            var singleItem = ItemListBuilder.anEmptyItemList()
                .withItem("foo")
                .build();

            updateQuality(singleItem);

            Assertions.assertThat(singleItem[0].name).isEqualTo("foo");
        }

        @Test
        void givenOneItemWithNullName_thenUpdateQuality_throwsNullPointerException() {
            var singleItem = ItemListBuilder.anEmptyItemList()
                .withItem(null)
                .build();

            assertThatThrownBy(() ->
                updateQuality(singleItem)
            ).isInstanceOf(NullPointerException.class);
        }

    }


    @Nested
    class SellInUpdate {

        @ParameterizedTest(name = "Sell in with value {0} is decreased by 1")
        @ValueSource(ints = {15, 11, 10, 5, 2, 1, 0, -1, -2})
        void givenAnItem_whenUpdateQuality_thenSellInDecreasesBy1(int sellIn) {
            var item = ItemBuilder.anItem()
                .withSellIn(sellIn)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(sellIn - 1);
        }

        @ParameterizedTest(name = "Sell in is decreased by 1 for item with name {0}")
        @ValueSource(strings = {GildedRoseConstants.AGED_BRIE, GildedRoseConstants.BACKSTAGE_PASSES})
        void givenAnItemWithASpecificName_whenUpdateQuality_thenSellInDecreasesBy1(String itemName) {
            var item = ItemBuilder.anItem()
                .withName(itemName)
                .withSellIn(5)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(4);
        }

        @ParameterizedTest(name = "Sell in for Sulfuras remains the same for sell in with value {0}")
        @ValueSource(ints = {15, 11, 10, 5, 2, 1, 0, -1, -2})
        void givenAnItemWithNameSulfuras_whenUpdateQuality_thenSellRemainsTheSame(int sellIn) {
            var item = ItemBuilder.anSulfurasItem()
                .withSellIn(sellIn)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(sellIn);
        }


    }

    @Nested
    class Quality {

        @Nested
        class Default {

            @Nested
            class PositiveSellIn {

                int positiveSellIn = 5;

                @Test
                void givenAnItemWithQuality10AndSellInPositive_whenUpdateQuality_thenQualityIs9() {
                    int quality = 10;
                    var item = ItemBuilder.anItem()
                        .withQuality(quality)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality - 1);
                }

                @Test
                void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(1)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(0)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs49() {
                    var item = ItemBuilder.anItem()
                        .withQuality(50)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(49);
                }

                @Test
                void givenAnItemWithQuality51_whenUpdateQuality_thenQualityIs50() {
                    var item = ItemBuilder.anItem()
                        .withQuality(51)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
                    int negativeQuality = -1;
                    var item = ItemBuilder.anItem()
                        .withQuality(negativeQuality)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(negativeQuality);
                }

            }

            @Nested
            class ZeroSellIn {

                int zeroSellIn = 0;

                @Test
                void givenAnItemWithSellIn0_whenUpdateQuality_thenQualityDecreasesBy2() {
                    int quality = 10;
                    var item = ItemBuilder.anItem()
                        .withQuality(quality)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality - 2);
                }

                @Test
                void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(1)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(0)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs48() {
                    var item = ItemBuilder.anItem()
                        .withQuality(50)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(48);
                }

                @Test
                void givenAnItemWithQuality51_whenUpdateQuality_thenQualityDecreasesBy2() {
                    var item = ItemBuilder.anItem()
                        .withQuality(51)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(49);
                }

                @Test
                void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
                    int negativeQuality = -1;
                    var item = ItemBuilder.anItem()
                        .withQuality(negativeQuality)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(negativeQuality);
                }
            }

            @Nested
            class NegativeSellIn {

                int negativeSellIn = -2;

                @Test
                void givenAnItemWithQuality10AndSellINegative_whenUpdateQuality_thenQualityIs8() {
                    int quality = 10;
                    var item = ItemBuilder.anItem()
                        .withQuality(quality)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality - 2);
                }

                @Test
                void givenAnItemWithQuality2_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(2)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality1_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(1)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality0_whenUpdateQuality_thenQualityIs0() {
                    var item = ItemBuilder.anItem()
                        .withQuality(0)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

                @Test
                void givenAnItemWithQuality50_whenUpdateQuality_thenQualityIs48() {
                    var item = ItemBuilder.anItem()
                        .withQuality(50)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(48);
                }

                @Test
                void givenAnItemWithQuality51_whenUpdateQuality_thenQualityIs49() {
                    var item = ItemBuilder.anItem()
                        .withQuality(51)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(49);
                }

                @Test
                void givenAnItemWithQuality52_whenUpdateQuality_thenQualityIs50() {
                    var item = ItemBuilder.anItem()
                        .withQuality(52)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenAnItemWithNegativeQuality_whenUpdateQuality_thenQualityRemainsTheSame() {
                    int negativeQuality = -1;
                    var item = ItemBuilder.anItem()
                        .withQuality(negativeQuality)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(negativeQuality);
                }

            }
        }

        @Nested
        class Sulfuras {

            @ParameterizedTest
            @ValueSource(ints = {-1, 0, 1})
            void givenSulfurasWithQuality80AndAnySellIn_whenUpdateQuality_thenQualityRemains80(int sellIn) {
                int quality = 80;
                var item = ItemBuilder.anItem()
                    .withName(GildedRoseConstants.SULFURAS)
                    .withQuality(quality)
                    .withSellIn(sellIn)
                    .build();

                updateQuality(item);

                assertThat(item.quality).isEqualTo(quality);
            }

            @ParameterizedTest
            @ValueSource(ints = {-1, 0, 1})
            void givenSulfurasWithQuality14AndAnySellIn_whenUpdateQuality_thenQualityRemains14(int sellIn) {
                int invalidQuality = 14;
                var item = ItemBuilder.anItem()
                    .withName(GildedRoseConstants.SULFURAS)
                    .withQuality(invalidQuality)
                    .withSellIn(sellIn)
                    .build();

                updateQuality(item);

                assertThat(item.quality).isEqualTo(invalidQuality);
            }

            @ParameterizedTest
            @ValueSource(ints = {-1, 0, 1})
            void givenSulfurasWithQuality0AndAnySellIn_whenUpdateQuality_thenQualityRemains0(int sellIn) {
                int zeroQuality = 0;
                var item = ItemBuilder.anItem()
                    .withName(GildedRoseConstants.SULFURAS)
                    .withQuality(zeroQuality)
                    .withSellIn(sellIn)
                    .build();

                updateQuality(item);

                assertThat(item.quality).isEqualTo(zeroQuality);
            }

            @ParameterizedTest
            @ValueSource(ints = {-1, 0, 1})
            void givenSulfurasWithQualityNegativeAndAnySellIn_whenUpdateQuality_thenQualityRemains0(int sellIn) {
                int negativeQuality = -5;
                var item = ItemBuilder.anItem()
                    .withName(GildedRoseConstants.SULFURAS)
                    .withQuality(negativeQuality)
                    .withSellIn(sellIn)
                    .build();

                updateQuality(item);

                assertThat(item.quality).isEqualTo(negativeQuality);
            }

        }

        @Nested
        class Brie {

            @Nested
            class PositiveSellIn {

                int positiveSellIn = 5;

                @Test
                void givenBrieWithQualityNegativeAndPositiveSellIn_whenUpdateQuality_thenQualityIncreasesBy1() {
                    int quality = -5;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(quality)
                        .withSellIn(1)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 1);
                }

                @Test
                void givenBrieWithQuality50AndPositiveSellIn_whenUpdateQuality_thenQualityRemains50() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(50)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenBrieWithQuality0AndPositiveSellIn_whenUpdateQuality_thenQualityBecomes1() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(0)
                        .withSellIn(1)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(1);
                }

                @Test
                void givenBrieWithQuality55AndSellInPositive_whenUpdateQuality_thenQualityRemains55() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(55)
                        .withSellIn(positiveSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(55);
                }
            }

            @Nested
            class ZeroSellIn {

                int zeroSellIn = 0;

                @Test
                void givenBrieWithQuality5AndSellIn0_whenUpdateQuality_thenQualityIncreasesBy2() {
                    int quality = 5;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(quality)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 2);
                }

                @Test
                void givenBrieWithQuality0AndSellIn0_whenUpdateQuality_thenQualityBecomes2() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(0)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(2);
                }

                @Test
                void givenBrieWithQuality50AndSellIn0_whenUpdateQuality_thenQualityRemains50() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(50)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenBrieWithQualityNegativeAndSellIn0_whenUpdateQuality_thenQualityIncreasesBy2() {
                    int quality = -5;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(quality)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 2);
                }

                @Test
                void givenBrieWithQuality55AndSellIn0_whenUpdateQuality_thenQualityRemains55() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(55)
                        .withSellIn(zeroSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(55);
                }
            }

            @Nested
            class NegativeSellIn {
                int negativeSellIn = -5;

                @Test
                void givenBrieWithQuality5AndSellInNegative_whenUpdateQuality_thenQualityIncreasesBy2() {
                    int quality = 5;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(quality)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 2);
                }

                @Test
                void givenBrieWithQuality50AndSellInNegative_whenUpdateQuality_thenQualityRemains50() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(50)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenBrieWithQuality49AndSellInNegative_whenUpdateQuality_thenQualityBecomes50() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(49)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenBrieWithQuality48AndSellInNegative_whenUpdateQuality_thenQualityBecomes50() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(49)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(50);
                }

                @Test
                void givenBrieWithQuality0AndSellInMinus1_whenUpdateQuality_thenQualityBecomes1() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(0)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(2);
                }

                @Test
                void givenBrieWithQuality55AndSellInNegative_whenUpdateQuality_thenQualityRemains55() {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.AGED_BRIE)
                        .withQuality(55)
                        .withSellIn(negativeSellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(55);
                }

            }
        }

        @Nested
        class BackstagePass {

            @Nested
            class PositiveSellIn {

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5})
                void givenBackstagePassWithQuality10AndSellInLessThan5_whenUpdateQuality_thenQualityIncreasesBy3(int sellIn) {
                    var quality = 10;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                        .withQuality(quality)
                        .withSellIn(sellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 3);
                }


                @ParameterizedTest
                @ValueSource(ints = {6, 7, 8, 9, 10})
                void givenBackstagePassWithQuality10AndSellInLessThan10_whenUpdateQuality_thenQualityIncreasesBy2(int sellIn) {
                    var quality = 10;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                        .withQuality(quality)
                        .withSellIn(sellIn)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 2);
                }


                @Test
                void givenBackstagePassWithSellIn11_whenUpdateQuality_thenQualityIncreasesBy1() {
                    var quality = 10;
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                        .withQuality(quality)
                        .withSellIn(11)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(quality + 1);
                }

                @Nested
                class SellInIsMax50 {

                    @ParameterizedTest
                    @ValueSource(ints = {1, 4, 5, 6, 9, 10, 11})
                    void givenBackstagePassWithQuality50AndSellInPositive_whenUpdateQuality_thenQualityRemains50(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(50)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(50);
                    }

                    @ParameterizedTest
                    @ValueSource(ints = {1, 4, 5, 6, 9, 10, 11})
                    void givenBackstagePassWithQuality49AndSellInPositive_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(49)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(50);
                    }

                    @ParameterizedTest
                    @ValueSource(ints = {1, 4, 5, 6, 9, 10})
                    void givenBackstagePassWithQuality48AndSellInLessThan10_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(48)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(50);
                    }


                    @ParameterizedTest
                    @ValueSource(ints = {1, 4, 5})
                    void givenBackstagePassWithQuality49AndSellInLessThan5_whenUpdateQuality_thenQualityBecomes50(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(47)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(50);
                    }
                }

                @Nested
                class NegativeQuality {

                    final int quality = -9;

                    @ParameterizedTest
                    @ValueSource(ints = {1, 2, 3, 4, 5})
                    void givenBackstagePassWithQuality10AndSellInLessThan5_whenUpdateQuality_thenQualityIncreasesBy3(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(quality)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(quality + 3);
                    }


                    @ParameterizedTest
                    @ValueSource(ints = {6, 7, 8, 9, 10})
                    void givenBackstagePassWithQuality10AndSellInLessThan10_whenUpdateQuality_thenQualityIncreasesBy2(int sellIn) {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(quality)
                            .withSellIn(sellIn)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(quality + 2);
                    }


                    @Test
                    void givenBackstagePassWithSellIn11_whenUpdateQuality_thenQualityIncreasesBy1() {
                        var item = ItemBuilder.anItem()
                            .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                            .withQuality(quality)
                            .withSellIn(11)
                            .build();

                        updateQuality(item);

                        assertThat(item.quality).isEqualTo(quality + 1);
                    }
                }
            }


            @Nested
            class ZeroAndNegativeSellIn {

                @ParameterizedTest(name = "Backstage pass with quality {0} becomes 0 when the sell in is 0")
                @ValueSource(ints = {55, 50, 10, 1, 0, -9})
                void givenBackstagePassWithSellIn0_whenUpdateQuality_thenQualityBecomes0(int quality) {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                        .withQuality(quality)
                        .withSellIn(0)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }


                @ParameterizedTest(name = "Backstage pass with quality {0} becomes 0 when the sell in is negative")
                @ValueSource(ints = {55, 50, 10, 1, 0, -9})
                void givenBackstagePassWithSellInNegative_whenUpdateQuality_thenQualityBecomes0(int quality) {
                    var item = ItemBuilder.anItem()
                        .withName(GildedRoseConstants.BACKSTAGE_PASSES)
                        .withQuality(quality)
                        .withSellIn(-1)
                        .build();

                    updateQuality(item);

                    assertThat(item.quality).isEqualTo(0);
                }

            }


        }

    }


}
