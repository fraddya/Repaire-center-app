package com.rcs.enums;

public enum UserType {
    ADMIN("ADMIN", "ADMIN"),
    EMPLOYEE("EMPLOYEE", "EMPLOYEE"),
    USER("USER", "USER");

    private final String label;
    private final String value;

    private UserType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static UserType getEnum(String s) {
        UserType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            UserType item = var1[var3];
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
