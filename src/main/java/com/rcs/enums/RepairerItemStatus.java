package com.rcs.enums;

public enum RepairerItemStatus {
    DONE("DONE", "DONE");

    private final String label;
    private final String value;

    private RepairerItemStatus(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static RepairerItemStatus getEnum(String s) {
        RepairerItemStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RepairerItemStatus item = var1[var3];
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
