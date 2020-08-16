package com.resqlity.orm.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Join Type
 */
public enum JoinType {
    @SerializedName("1")
    INNER(1),
    @SerializedName("2")
    LEFT(2),
    @SerializedName("3")
    RIGHT(3),
    @SerializedName("4")
    LEFT_OUTER(4),
    @SerializedName("5")
    RIGHT_OUTER(5);
    private int val;

    JoinType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
