package com.resqlity.orm.dummyModels;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableName = "customers", TableSchema = "sales")
public class Customers {
    @ResqlityProperty(ColumnName = "first_name")
    private String customerFirstName;
    @ResqlityProperty(ColumnName = "last_name")
    private String customerLastName;
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
    @ResqlityProperty(ColumnName = "customer_id")
    private int customerId;

    public Customers(String firstName, String lastName, String phone, String email, String street, String city, String state, String zipCode) {
        this.customerFirstName = firstName;
        this.customerLastName = lastName;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
