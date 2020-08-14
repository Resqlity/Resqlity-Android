package com.resqlity.android_connector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.resqlity.android_connector.models.Customers;
import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.exceptions.ResqlityDbException;
import com.resqlity.orm.models.responses.ResqlityResponse;
import com.resqlity.orm.models.responses.ResqlitySimpleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResqlityContext context = new ResqlityContext("trImnXg/L5zHICePLhkak1AFd3XKy6uur7fiCuCA/yc=", this, R.drawable.ic_launcher_background);
//        try {
////            long start = System.nanoTime();
//            context.Select(Customers.class)
//                    .Select("firstName")
//                    .Select("lastName")
//                    .Where("firstName", "berkay", Comparator.Equal)
//                    .Where("firstName", null, Comparator.IsNull)
//                    .Query()
//                    .OrderBy("firstName", false)
//                    .Query()
//                    .PageBy(1, 10)
//                    .Execute(true, true);
//            ResqlityResponse<List<Customers>> selectResponse = context.Select(Customers.class)
////                    .Select("firstName")
////                    .Select("lastName")
////                    .Where("firstName", "Berkay", Comparator.Equal)
////                    .And("lastName", "YALÇIN", Comparator.Equal)
////                    .Query()
//                    .PageBy(1, 100000)
//                    .OrderBy("firstName", true)
//                    .Query()
//                    .Execute(true, false);
////            long finish = System.nanoTime();
////            long timeElapsed = finish - start;
////            double seconds = (double) timeElapsed / 1_000_000_000.0;
//            List<Customers> customers = new ArrayList<>();
//            Customers customer1 = new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");
//            Customers customer2 = new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");
//            Customers customer3 = new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");
//            Customers customer4 = new Customers("Jone", "Doe", "90xxxxxxxx", "jone.doe@gmail.com", "Wolf Street", "NY", "NY", "x");
//            customers.add(customer1);
//            customers.add(customer2);
//            customers.add(customer3);
//            customers.add(customer4);
//
//
//            ResqlitySimpleResponse insertResponse = context.Insert(Customers.class)
//                    .Insert(new Customers("Berkay",
//                                    "YALÇIN",
//                                    "905534787057",
//                                    "berkay.yalcin20hotmail.com",
//                                    "Saka",
//                                    "İstanbul",
//                                    "İstanbul",
//                                    "34000"),
//                            new Customers("Latif",
//                                    "ATÇI",
//                                    "905534787057",
//                                    "latif800gmailcom",
//                                    "Sefa",
//                                    "İstanbul",
//                                    "İstanbul",
//                                    "34000"))
//                    .Execute();
//
//            ResqlityResponse<Integer> deleteResponse = context
//                    .Delete(Customers.class)
//                    .Where("lastName", "Alvarez", Comparator.Equal)
//                    .And("state", "NY", Comparator.Equal)
//                    .Query().Execute(true);
//
//            ResqlityResponse<Integer> updateResponse = context
//                    .Update(Customers.class)
//                    .Update("state", "NY")
//                    .Where("lastName", "Acevedo", Comparator.Equal)
//                    .Or("lastName", "YALÇIN", Comparator.Equal)
//                    .Query()
//                    .Execute(true);
//
//        } catch (ResqlityDbException e) {
//            e.printStackTrace();
//        }
    }
}