package com.resqlity.orm;

import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.queries.select.SelectQuery;
import com.resqlity.orm.queryobjects.select.SelectColumn;

public class ResqlityContext {

    public void Foo() {
        SelectQuery query = new SelectQuery();
        query
                .Select(new SelectColumn("", "", "", ""))
                .Where("", "", "", "", Comparator.IsNull)
                .And("", "", "", "", Comparator.IsNull)
                .And("", "", "", "", Comparator.IsNull)
                .And("", "", "", "", Comparator.IsNull)
                .And("", "", "", "", Comparator.IsNull)
                .Or("", "", "", "", Comparator.IsNull)
                .Where("", "", "", "", Comparator.IsNull)
                .Select(new SelectColumn("", "", "", ""))
        .Execute();

    }
}
