package me.jishuna.customentitylib;

import com.google.common.base.Supplier;

public class Utils {
    public static <T> T getOr(T value, Supplier<T> supplier) {
        return value != null ? value : supplier.get();
    }
}
