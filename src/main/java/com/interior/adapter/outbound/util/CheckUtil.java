package com.interior.adapter.outbound.util;

import com.interior.adapter.common.exception.ErrorType;
import com.interior.adapter.common.exception.InvalidInputException;
import java.util.function.Predicate;

public class CheckUtil {
    public static <T> void require(final Predicate<T> predicate, final T target, final ErrorType errorType) {
        if (predicate.test(target)) {
            throw new InvalidInputException(errorType);
        }
    }

    public static void check(final boolean condition, final ErrorType errorType) {
        if (condition) {
            throw new InvalidInputException(errorType);
        }
    }
}
