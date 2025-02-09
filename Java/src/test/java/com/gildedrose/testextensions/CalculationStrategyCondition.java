package com.gildedrose.testextensions;

import com.gildedrose.GildedRoseConstants;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.platform.commons.util.AnnotationUtils.findAnnotation;

public class CalculationStrategyCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        final var optional = findAnnotation(context.getElement(), SkipWhenStrategyIsDifferentFrom.class);
        if (optional.isPresent()) {
            final SkipWhenStrategyIsDifferentFrom annotation = optional.get();
            final String strategy = annotation.strategy();

            if (
                strategy.equals(GildedRoseConstants.FEATURE_FLAGS.calculationStrategy())
            ) {
                return ConditionEvaluationResult.enabled("Calculation strategy is " + strategy);
            } else {
                return ConditionEvaluationResult.disabled("Calculation strategy is not " + strategy);
            }
        }
        return ConditionEvaluationResult.enabled("Calculation strategy is not specified");
    }

}
