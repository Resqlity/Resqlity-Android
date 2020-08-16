package com.resqlity.orm;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.resqlity.orm.consts.Pagination;
import com.resqlity.orm.dummyModels.Customers;
import com.resqlity.orm.dummyModels.Orders;
import com.resqlity.orm.dummyModels.Staffs;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.enums.JoinType;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.clausemodels.JoinClauseModel;
import com.resqlity.orm.models.querymodels.SelectModel;
import com.resqlity.orm.queries.SelectQuery;
import com.resqlity.orm.queryobjects.select.SelectColumn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ResqlityContextTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void select() throws ResqlityDbException {
        Context appContext = getInstrumentation().getTargetContext();
        ResqlityContext context = new ResqlityContext("trImnXg/L5zHICePLhkak1AFd3XKy6uur7fiCuCA/yc=",
                appContext,
                700020);
        SelectQuery query = context.Select(Orders.class)
                .Select("orderId")
                .PageBy(1, 10)
                .InnerJoin(Staffs.class, "staffId", "staffId", Comparator.Equal)
                .Select("firstName")
                .Select("storeId")
                .Query()
                .InnerJoin(Customers.class, "customerId", "customerId", Comparator.Equal)
                .Select("customerFirstName")
                .Select("customerLastName")
                .Where("customerFirstName", "berkay", Comparator.Equal)
                .And("customerLastName", "yalçın", Comparator.Equal)
                .Parent()
                .Query()
                .PageBy(10)
                .OrderBy("orderId", true)
                .ThenBy("staffId", false)
                .Query();

        assertNotNull(query);

        SelectModel model = query.getSelectModel();
        List<SelectColumn> selectColumnList = model.getSelectedColumns();
        assertEquals(selectColumnList.get(0).getColumnAlias() + " Not Equals orderId", selectColumnList.get(0).getColumnAlias(), "orderId");
        assertNotNull("Joins Null", model.getJoins());
        List<JoinClauseModel> joinClauseModels = model.getJoins();


        assertNotNull("", joinClauseModels.get(0));
        assertNotNull("", joinClauseModels.get(0).getColumns());
        assertEquals("", joinClauseModels.get(0).getParentColumnName(), "staff_id");
        assertEquals("", joinClauseModels.get(0).getColumnName(), "staff_id");
        assertEquals("", joinClauseModels.get(0).getJoinType(), JoinType.INNER);
        assertNotNull("", joinClauseModels.get(0).getColumns().get(0));
        assertNotNull("", joinClauseModels.get(0).getColumns().get(1));
        assertEquals("", joinClauseModels.get(0).getColumns().get(0).getColumnAlias(), "firstName");
        assertEquals("", joinClauseModels.get(0).getColumns().get(1).getColumnAlias(), "storeId");


        assertNotNull("", joinClauseModels.get(1));
        assertNotNull("", joinClauseModels.get(1).getColumns());
        assertEquals("", joinClauseModels.get(1).getParentColumnName(), "customer_id");
        assertEquals("", joinClauseModels.get(1).getColumnName(), "customer_id");
        assertEquals("", joinClauseModels.get(1).getJoinType(), JoinType.INNER);
        assertEquals("", joinClauseModels.get(1).getComparator(), Comparator.Equal);

        assertNotNull("", joinClauseModels.get(1).getColumns().get(0));
        assertEquals("", joinClauseModels.get(1).getColumns().get(0).getColumnAlias(), "customerFirstName");

        assertNotNull("", joinClauseModels.get(1).getColumns().get(1));
        assertEquals("", joinClauseModels.get(1).getColumns().get(1).getColumnAlias(), "customerLastName");


        assertEquals(model.getSkipCount(), (10 - 1) * Pagination.PageSize);
        assertEquals(model.getMaxResultCount(), Pagination.PageSize);

        assertNotNull("", model.getOrderBy());
        assertNotNull("", model.getOrderBy().getThenBy());

        assertEquals("", model.getOrderBy().getColumnName(), "order_id");
        assertTrue("", model.getOrderBy().isAsc());

        assertEquals("", model.getOrderBy().getThenBy().getColumnName(), "staff_id");
        assertFalse("", model.getOrderBy().getThenBy().isAsc());

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void insert() {
    }
}