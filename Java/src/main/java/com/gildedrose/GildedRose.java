package com.gildedrose;

import static com.gildedrose.GildedRoseConstants.*;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateQuality(item);
            updateSellIn(item);
        }
    }

    private static void updateQuality(Item item) {
        if (item.name.equals(AGED_BRIE)) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        } else {
            if (item.name.equals(BACKSTAGE_PASSES)) {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;

                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                }
            } else {
                if (item.quality > 0) {
                    if (!item.name.equals(SULFURAS)) {
                        item.quality = item.quality - 1;
                    }
                }
            }
        }

        if (item.sellIn <= 0) {
            if (item.name.equals(AGED_BRIE)) {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            } else {
                if (!item.name.equals(BACKSTAGE_PASSES)) {
                    if (item.quality > 0) {
                        if (!item.name.equals(SULFURAS)) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = item.quality - item.quality;
                }
            }
        }




    }

    private static void updateSellIn(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn--;
        }
    }
}
