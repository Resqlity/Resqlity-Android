package com.resqlity.orm.dummyModels;

import com.resqlity.orm.annotations.ResqlityProperty;
import com.resqlity.orm.annotations.ResqlityTable;

@ResqlityTable(TableSchema = "sales", TableName = "orders")
public class Orders {
    @ResqlityProperty(ColumnName = "order_id")
    private int orderId;
    @ResqlityProperty(ColumnName = "staff_id")
    private int staffId;
    @ResqlityProperty(ColumnName = "customer_id")
    private int customerId;
}
