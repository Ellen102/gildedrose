package com.gildedrose;

import com.gildedrose.helpers.ItemListBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GildedRoseTest {

    private static void updateQuality(Item[] items) {
        //Items are updated inplace
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
                .withItem(ItemNames.BACKSTAGE_PASS)
                .withItem(ItemNames.AGED_BRIE)
                .withItem(ItemNames.SULFURAS)
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

}
