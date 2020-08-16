package com.resqlity.orm.inapp;

import com.google.gson.annotations.SerializedName;

/**
 * Resqlity WebSocket Message Type
 */
public enum MessageType {
    @SerializedName("1")
    PushNotification(1),
    @SerializedName("2")
    DbChanged(2);
    private int val;

    MessageType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
