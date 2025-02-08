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
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }

                if (item.sellIn <= 0) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;
                    }
                }
            }
            case BACKSTAGE_PASSES -> {
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

                if (item.sellIn <= 0) {
                    item.quality = 0;
                }
            }
            default -> {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
                if (item.sellIn <= 0) {
                    if (item.quality > 0) {
                        item.quality = item.quality - 1;

                    }
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
