package com.rcs.enums;

public enum Status {
    ACTIVE("ACTIVE", "A"),
    DELETED("DELETED", "D"),
    TERMINATION("TERMINATION", "T"),
    RESIGNATION("RESIGNATION", "RES"),
    RETIREMENT("RETIREMENT", "RET");

    private final String label;
    private final String value;

    private Status(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static Status getEnum(String s) {
        Status[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Status item = var1[var3];
            if (item.value.equals(s)) {
                return item;
            }
        }

        return null;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
