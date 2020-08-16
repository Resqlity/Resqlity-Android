package com.resqlity.orm;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.resqlity.orm.dummyModels.Customers;
import com.resqlity.orm.dummyModels.Orders;
import com.resqlity.orm.dummyModels.Staffs;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.exceptions.ResqlityDbException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.resqlity.orm.test", appContext.getPackageName());
    }

    @Before
    public void grantPhonePermission() {

    }

    @Test
    public void useSelectJoinFunction() throws ResqlityDbException {
        Context appContext = getInstrumentation().getTargetContext();

        ResqlityContext context = new ResqlityContext("trImnXg/L5zHICePLhkak1AFd3XKy6uur7fiCuCA/yc=",
                appContext,
                700020);
        context.Select(Orders.class)
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
                .Execute(true, true);
    }
}