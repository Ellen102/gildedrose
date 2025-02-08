package com.gildedrose;

import com.gildedrose.helpers.ItemBuilder;
import com.gildedrose.helpers.ItemListBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

        @ParameterizedTest
        @ValueSource(ints = {15, 11, 10, 5, 2, 1, 0, -1, -2})
        @DisplayName("Sell in with value {0} is decreased by 1")
        void givenAnItem_whenUpdateQuality_thenSellInDecreasesBy1(int sellIn) {
            var item = ItemBuilder.anItem()
                .withSellIn(sellIn)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(sellIn - 1);
        }

        @ParameterizedTest
        @ValueSource(strings = {GildedRoseConstants.AGED_BRIE, GildedRoseConstants.BACKSTAGE_PASSES})
        @DisplayName("Sell in is decreased by 1 for item with name {0}")
        void givenAnItemWithASpecificName_whenUpdateQuality_thenSellInDecreasesBy1(String itemName) {
            var item = ItemBuilder.anItem()
                .withName(itemName)
                .withSellIn(5)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(4);
        }

        @ParameterizedTest
        @ValueSource(ints = {15, 11, 10, 5, 2, 1, 0, -1, -2})
        @DisplayName("Sell in for Sulfuras remains the same for sell in with value {0}")
        void givenAnItemWithNameSulfuras_whenUpdateQuality_thenSellRemainsTheSame(int sellIn) {
            var item = ItemBuilder.anSulfurasItem()
                .withSellIn(sellIn)
                .build();

            updateQuality(item);

            assertThat(item.sellIn).isEqualTo(sellIn);
        }


    }

}
