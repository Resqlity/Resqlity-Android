package com.resqlity.orm.enums;

public enum Decision {
    AND(1),
    OR(2);
    private int val;

    Decision(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
