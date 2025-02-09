package com.gildedrose.core.validator;

import java.util.List;
import java.util.stream.Stream;

public sealed interface ValidationResult {

    List<String> errors();

    ValidationResult and(ValidationResult validationResult);
    boolean hasErrors();

    void throwOnError();

    /// simplified public methods
    static Failure failure(String message) {
        return new Failure(message);
    }

    Success SUCCESS = new Success();

    static Success success() {
        return ValidationResult.SUCCESS;
    }

    ///  implementation

    final class Success implements ValidationResult {
        private Success() {
        }

        @Override
        public List<String> errors() {
            return List.of();
        }

        @Override
        public ValidationResult and(ValidationResult validationResult) {
            return validationResult;
        }

        @Override
        public void throwOnError() {

        }

        @Override
        public boolean hasErrors() {
            return false;
        }
    }

    final class Failure implements ValidationResult {

        public final List<String> errors;

        public Failure() {
            errors = List.of();
        }

        public Failure(List<String> list) {
            errors = List.copyOf(list);
        }

        public Failure(String error) {
            errors = List.of(error);
        }

        @Override
        public List<String> errors() {
            return errors;
        }

        @Override
        public ValidationResult and(ValidationResult validationResult) {
            return new Failure(union(errors, validationResult.errors()));
        }

        @Override
        public void throwOnError() {
            throw new ValidationException("Validation failure: " + String.join(", ", errors));
        }

        @Override
        public boolean hasErrors() {
            return true;
        }

        private List<String> union(List<String> a, List<String> b) {
            return Stream.concat(a.stream(), b.stream()).toList();
        }
    }
}
