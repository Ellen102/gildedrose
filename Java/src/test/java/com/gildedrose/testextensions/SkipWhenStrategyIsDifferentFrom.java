package com.gildedrose.testextensions;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CalculationStrategyCondition.class)
public @interface SkipWhenStrategyIsDifferentFrom {

    String strategy() ;

}
