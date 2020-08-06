package com.resqlity.orm.examples;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableSchema = "dbo", TableName = "ResqlityRelation")
public class SampleRelation {
    @ResqlityProperty(ColumnName = "id")
    private int id;
    @ResqlityProperty(ColumnName = "parentId")
    private int parentId;
    @ResqlityProperty(ColumnName = "Name")
    private String firstName;
}
