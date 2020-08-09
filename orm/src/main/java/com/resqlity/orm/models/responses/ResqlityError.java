package com.resqlity.orm.models.responses;

public class ResqlityError {
    private String propertyName;
    private String errorMessage;
    private Object[] formattedMessageArguments;

    public ResqlityError(String propertyName, String errorMessage) {
        this.propertyName = propertyName;
        this.errorMessage = errorMessage;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object[] getFormattedMessageArguments() {
        return formattedMessageArguments;
    }

    public void setFormattedMessageArguments(Object[] formattedMessageArguments) {
        this.formattedMessageArguments = formattedMessageArguments;
    }
}
