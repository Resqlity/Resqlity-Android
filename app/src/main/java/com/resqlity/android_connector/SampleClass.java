package com.resqlity.android_connector;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableName = "ResqlityTest",TableSchema = "dbo")
public class SampleClass {
    @ResqlityProperty(ColumnName = "Firstname")
    private String name;
}
