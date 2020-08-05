package com.resqlity.orm.enums;

public enum Comparator {
    Equal(1),
    NotEqual(2),
    GreatherThan(3),
    LessThan(4),
    IsNull(5),
    IsNotNull(6);

    private int val;

    Comparator(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
