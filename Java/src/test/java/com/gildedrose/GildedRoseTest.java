package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item item = new Item("foo", 0, 0);
        Item[] items = new Item[] {item};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertThat(item.name).isEqualTo("foo");
    }

}
