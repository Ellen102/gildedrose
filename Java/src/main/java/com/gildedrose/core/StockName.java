package com.gildedrose.core;

public record StockName(String value) {
    public static StockName of(String value) {
        return new StockName(value);
    }
}
