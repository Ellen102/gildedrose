package com.gildedrose;

import com.gildedrose.core.*;

public record FeatureFlags(
    CalculationStrategy calculationStrategy
) {

    enum CalculationStrategy {

        ORIGINAL_BEHAVIOUR(FeatureFlags.ORIGINAL_BEHAVIOUR), FAIL_ON_EXCEPTIONS_BEHAVIOUR(FeatureFlags.FAIL_ON_EXCEPTIONS_BEHAVIOUR);

        public String name;

        CalculationStrategy(String name) {
            this.name = name;
        }
    }

    public final static String ORIGINAL_BEHAVIOUR = "ORIGINAL_BEHAVIOUR";
    public final static String FAIL_ON_EXCEPTIONS_BEHAVIOUR = "FAIL_ON_EXCEPTIONS_BEHAVIOUR";

    public Calculator calculator() {
        return switch (calculationStrategy) {
            case ORIGINAL_BEHAVIOUR -> new ValidatingCalculator(new RuleEngineCalculator(new SimpleRuleEngine()), false);
            case FAIL_ON_EXCEPTIONS_BEHAVIOUR -> new ValidatingCalculator(new RuleEngineCalculator(new DslRuleEngine()), true);
        };
    }
}
