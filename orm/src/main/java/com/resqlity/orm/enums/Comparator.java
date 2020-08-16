package com.resqlity.orm.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Value Comparator
 */
public enum Comparator {
    @SerializedName("1")
    Equal(1),
    @SerializedName("2")
    NotEqual(2),
    @SerializedName("3")
    GreatherThan(3),
    @SerializedName("4")
    LessThan(4),
    @SerializedName("5")
    IsNull(5),
    @SerializedName("6")
    IsNotNull(6);


    private int val;

    Comparator(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
