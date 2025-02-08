package com.gildedrose;

import static com.gildedrose.GildedRoseConstants.*;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            item.quality = calculateNextQuality(item);
            item.sellIn = calculateNextSellIn(item);
        }
    }

    private static int calculateNextQuality(Item item) {
        switch (item.name) {
            case SULFURAS -> {
                return item.quality;
            }

            case AGED_BRIE -> {
                if (item.sellIn > 0) {
                    return increasedItemQualityWithMax50(item, 1);
                } else {
                    return increasedItemQualityWithMax50(item, 2);
                }

            }

            case BACKSTAGE_PASSES -> {
                if (item.sellIn >= 11) {
                    return increasedItemQualityWithMax50(item, 1);
                } else if (6 <= item.sellIn) {
                    return increasedItemQualityWithMax50(item, 2);
                } else if (0 < item.sellIn) {
                    return increasedItemQualityWithMax50(item, 3);
                } else {
                    return 0;
                }
            }

            default -> {
                if (item.sellIn > 0) {
                    return decreasedItemQualityWithMin0(item, 1);
                } else {
                    return decreasedItemQualityWithMin0(item, 2);
                }
            }
        }
    }

    private static int decreasedItemQualityWithMin0(Item item, int decrement) {
        if (item.quality < 0) {
            //keep same behavior
            return item.quality;
        } else {
            return Math.max(0, item.quality - decrement);
        }
    }

    private static int increasedItemQualityWithMax50(Item item, int increment) {
        if (item.quality > 50) {
            //keep same behavior
            return item.quality;
        } else {
            return Math.min(50, item.quality + increment);
        }
    }

    private static int calculateNextSellIn(Item item) {
        if (SULFURAS.equals(item.name)) {
            return item.sellIn;
        } else {
            return item.sellIn - 1;
        }
    }
}
