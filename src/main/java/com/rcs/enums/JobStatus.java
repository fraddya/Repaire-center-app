package com.rcs.enums;

public enum JobStatus {
    NEWREQUEST("New Request", "NEWREQUEST"),
    WATINGFORCONFIRM("Waiting For Confirm", "WATINGFORCONFIRM"),
    CONFIRM("Confirm", "CONFIRM"),

    ASSIGN("Assign Employee", "ASSIGN"),

    START("START Repairer", "START"),

    COMPLETE("Complete", "COMPLETE"),

    DONE("Done", "DONE"),

    OTHER("Other", "OT");

    private String label;

    private String value;

    JobStatus(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static JobStatus getEnum(String s) {
        for (JobStatus item : JobStatus.values()) {
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
