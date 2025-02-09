package com.gildedrose;


import static com.gildedrose.FeatureFlags.CalculationStrategy.FAIL_ON_EXCEPTIONS_BEHAVIOUR;
import static com.gildedrose.FeatureFlags.CalculationStrategy.ORIGINAL_BEHAVIOUR;

public class GildedRoseConstants {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured";
    public static final int SULFURAS_QUALITY = 80;
    public static final int MAX_QUALITY = 50;
    public static final int MIN_QUALITY = 0;
    public static final FeatureFlags FEATURE_FLAGS = new FeatureFlags(
        FAIL_ON_EXCEPTIONS_BEHAVIOUR
//        ORIGINAL_BEHAVIOUR
    );

}
