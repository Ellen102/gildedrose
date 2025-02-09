package com.gildedrose.core.validator;

import com.gildedrose.core.StockItem;

import java.util.List;

import static com.gildedrose.core.validator.ValidationResult.SUCCESS;

public class AllValidator implements Validator {
    private final List<Validator> validators;

    public AllValidator(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public ValidationResult validate(StockItem item) {
        return validators.stream()
            .map(validator -> validator.validate(item))
            .reduce(SUCCESS, ValidationResult::and);
    }
}
