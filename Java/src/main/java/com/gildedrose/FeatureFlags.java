package com.gildedrose;

import com.gildedrose.core.Calculator;
import com.gildedrose.core.SimpleCalculator;
import com.gildedrose.core.ValidatingCalculator;

public record FeatureFlags(
    String calculationStrategy
) {
    public final static String ORIGINAL_BEHAVIOUR = "ORIGINAL_BEHAVIOUR";
    public final static String FAIL_ON_EXCEPTIONS_BEHAVIOUR = "FAIL_ON_EXCEPTIONS_BEHAVIOUR";

    public Calculator calculator() {
        return switch (calculationStrategy) {
            case ORIGINAL_BEHAVIOUR -> new ValidatingCalculator(new SimpleCalculator(), false);
            case FAIL_ON_EXCEPTIONS_BEHAVIOUR -> new ValidatingCalculator(new SimpleCalculator(), true);
            default -> throw new IllegalStateException("Unknown calculation strategy: " + calculationStrategy);
        };
    }
}
