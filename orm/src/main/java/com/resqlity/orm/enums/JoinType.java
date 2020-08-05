package com.resqlity.orm.enums;

public enum JoinType {
    INNER(1),
    LEFT(2),
    RIGHT(3),
    LEFT_OUTER(4),
    RIGHT_OUTER(5);
    private int val;

    JoinType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
