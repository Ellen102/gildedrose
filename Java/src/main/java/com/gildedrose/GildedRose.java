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
        switch (item.name) {
            case SULFURAS -> {

            }

            case AGED_BRIE -> {
                if (item.sellIn > 0) {
                    increaseItemQualityWithMax50(item, 1);
                } else {
                    increaseItemQualityWithMax50(item, 2);
                }
            }

            case BACKSTAGE_PASSES -> {
                if (item.sellIn >= 11) {
                    increaseItemQualityWithMax50(item, 1);
                } else if (6 <= item.sellIn) {
                    increaseItemQualityWithMax50(item, 2);
                } else if (0 < item.sellIn) {
                    increaseItemQualityWithMax50(item, 3);
                } else {
                    item.quality = 0;
                }
            }

            default -> {
                if (item.sellIn > 0) {
                    decreaseItemQualityWithMin0(item, 1);
                } else {
                    decreaseItemQualityWithMin0(item, 2);
                }
            }
        }


    }

    private static void decreaseItemQualityWithMin0(Item item, int decrement) {
        if (item.quality < 0) {
            //keep same behavior
        } else {
            item.quality = Math.max(0, item.quality - decrement);
        }
    }

    private static void increaseItemQualityWithMax50(Item item, int increment) {
        if (item.quality > 50) {
            //keep same behavior
        } else {
            item.quality = Math.min(50, item.quality + increment);
        }
    }

    private static void updateSellIn(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn--;
        }
    }
}
