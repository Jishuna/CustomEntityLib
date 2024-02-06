package me.jishuna.customentitylib.test;

public enum Priority {
    LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4);

    protected final int value;

    private Priority(int value) {
        this.value = value;
    }
}
