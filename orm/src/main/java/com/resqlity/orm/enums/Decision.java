package com.resqlity.orm.enums;

import com.google.gson.annotations.SerializedName;

public enum Decision {
    @SerializedName("1")
    AND(1),
    @SerializedName("2")
    OR(2);
    private int val;

    Decision(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
