package com.resqlity.orm.inapp;

import androidx.annotation.Nullable;

import java.util.Date;

/**
 * Resqlity WebSocket Base Message
 */
public class BaseMessage {
    /**
     * Json Message
     */
    private String message;
    /**
     * Message Sent On Date
     */
    private Date sendDate;
    /**
     * Message Type
     */
    private MessageType type;
    /**
     * Is Specified On Table
     */
    private @Nullable
    String tableName;

    @Nullable
    public String getTableName() {
        return tableName;
    }

    public void setTableName(@Nullable String tableName) {
        this.tableName = tableName;
    }

    @Nullable
    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(@Nullable String tableSchema) {
        this.tableSchema = tableSchema;
    }

    private @Nullable
    String tableSchema;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
