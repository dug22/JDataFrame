package io.github.dug22.jdataframe.operations.filter;

import java.util.Map;

@FunctionalInterface
public interface FilterPredicate<T> {

    /**
     * Evaluates the predicate on the given input.
     *
     * @param t the input element
     * @return true if the element satisfies the condition, false otherwise
     */
    boolean test(T t);
}
