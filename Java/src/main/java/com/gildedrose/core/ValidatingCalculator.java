package com.gildedrose.core;

import com.gildedrose.core.validator.AllValidator;
import com.gildedrose.core.validator.ValidationResult;
import com.gildedrose.core.validator.Validator;

import java.util.List;

import static com.gildedrose.GildedRoseConstants.MAX_QUALITY;
import static com.gildedrose.GildedRoseConstants.SULFURAS_QUALITY;
import static com.gildedrose.core.predicate.StockNamePredicate.IS_EXACTLY_SULFURAS;
import static com.gildedrose.core.validator.Validator.validate;

public class ValidatingCalculator implements Calculator {
    private static final Validator validator = new AllValidator(
        List.of(
            validate("Quality should be 0 or higher", item -> item.quality() < 0),
            validate("Quality should be " + SULFURAS_QUALITY, item -> IS_EXACTLY_SULFURAS.test(item.asStockName()) && item.quality() != SULFURAS_QUALITY),
            validate("Quality should be less than " + MAX_QUALITY, item -> IS_EXACTLY_SULFURAS.negate().test(item.asStockName()) && item.quality() > MAX_QUALITY)
        )
    );

    private final Calculator calculator;
    private final boolean onErrorFail;

    public ValidatingCalculator(Calculator calculator, boolean onErrorThrow) {
        this.calculator = calculator;
        this.onErrorFail = onErrorThrow;
    }

    public ValidatingCalculator(Calculator calculator) {
        this.calculator = calculator;
        this.onErrorFail = false;
    }

    @Override
    public StockItem calculateNext(StockItem stockItem) {
        var result = validator.validate(stockItem);
        if (onErrorFail) {
            result.throwOnError();
        }
        if (result.hasErrors()) {
            logErrors(result);
        }
        return calculator.calculateNext(stockItem);
    }

    private static void logErrors(ValidationResult result) {
        System.err.println(String.join(",", result.errors()));
    }
}
