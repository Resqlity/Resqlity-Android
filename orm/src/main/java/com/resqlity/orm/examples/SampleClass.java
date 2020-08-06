package com.resqlity.orm.examples;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableName = "ResqlityTest",TableSchema = "dbo")
public class SampleClass {
    @ResqlityProperty(ColumnName = "Firstname")
    private String name;
    @ResqlityProperty(ColumnName = "LastName")
    private String lastName;

    public SampleClass(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public SampleClass() {
    }
}
