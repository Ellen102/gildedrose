package com.gildedrose.core.validator;

import com.gildedrose.core.StockItem;

import java.util.function.Function;

import static com.gildedrose.core.validator.ValidationResult.SUCCESS;
import static com.gildedrose.core.validator.ValidationResult.failure;

public interface Validator {
    ValidationResult validate(StockItem item);

    static Validator validate(String msg, Function<StockItem, Boolean> checkForFailure) {
        return item -> {
            if (checkForFailure.apply(item)) {
                return failure("%s: %s (%s)".formatted(item.name(), msg, item));
            } else {
                return SUCCESS;
            }
        };
    }
}
