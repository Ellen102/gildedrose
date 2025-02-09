package com.gildedrose.core.valueobjects;

public record StockName(String value) {
    public boolean isExactly(String name){
        return name.equals(value);
    }
}
