package com.gildedrose.core.dsl;

public record Range(
    int fromInclusive, int toInclusive
) {
    public static Range between(int fromInclusive, int toInclusive) {
        return new Range(fromInclusive, toInclusive);
    }

    public static Range higherIncluding(int fromInclusive) {
        return new Range(fromInclusive, Integer.MAX_VALUE);
    }

    public static Range lowerIncluding(int toInclusive) {
        return new Range(Integer.MIN_VALUE, toInclusive);
    }

    public static Range strictPositive() {
        return higherIncluding(1);
    }

    public static Range negative() {
        return lowerIncluding(0);
    }

    /// Implementation
    public boolean contains(int value) {
        return fromInclusive <= value && value <= toInclusive;
    }
}
