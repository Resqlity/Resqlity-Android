package com.resqlity.android_connector.models;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableName = "customers", TableSchema = "sales")
public class Customers {
    @ResqlityProperty(ColumnName = "first_name")
    private String firstName;
    @ResqlityProperty(ColumnName = "last_name")
    private String lastName;
    @ResqlityProperty(ColumnName = "phone")
    private String phone;
    @ResqlityProperty(ColumnName = "email")
    private String email;
    @ResqlityProperty(ColumnName = "street")
    private String street;
    @ResqlityProperty(ColumnName = "city")
    private String city;
    @ResqlityProperty(ColumnName = "state")
    private String state;
    @ResqlityProperty(ColumnName = "zip_code")
    private String zipCode;

    public Customers(String firstName, String lastName, String phone, String email, String street, String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
