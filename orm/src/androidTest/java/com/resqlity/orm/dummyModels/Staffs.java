package com.resqlity.orm.dummyModels;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableName = "staffs", TableSchema = "sales")
public class Staffs {
    @ResqlityProperty(ColumnName = "staff_id")
    private int staffId;
    @ResqlityProperty(ColumnName = "first_name")
    private String firstName;
    @ResqlityProperty(ColumnName = "last_name")
    private String lastName;
    @ResqlityProperty(ColumnName = "phone")
    private String phoneNumber;
    @ResqlityProperty(ColumnName = "store_id")
    private int storeId;

}
