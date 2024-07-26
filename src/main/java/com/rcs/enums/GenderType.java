package com.rcs.enums;

public enum GenderType {
    MALE("Male", "M"),
    FEMALE("Female", "F"),
    OTHER("Other", "OT");

    private String label;

    private String value;

    GenderType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static GenderType getEnum(String s) {
        for (GenderType item : GenderType.values()) {
            if (item.value.equals(s)) {
                return item;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
    public String getLabel() {
        return label;
    }
}
