package com.leapwise.evalexp.evaluator.rdp.expressions;

public record Literal<T>(T value) implements Expression {
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final Literal<Object> compareTo = (Literal<Object>) obj;

        if (value == null || compareTo.value == null) {
            return value == compareTo.value;
        } else if (value instanceof Number leftNum && compareTo.value instanceof Number rightNum) {
            return leftNum.doubleValue() == rightNum.doubleValue();
        } else {
            return value.equals(compareTo.value);
        }
    }

    @Override
    public String toString() {
        return value != null ? value + ", type of " + value.getClass().getName() : "null";
    }
}
