package com.resqlity.android_connector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.resqlity.android_connector.models.Customers;
import com.resqlity.orm.ResqlityContext;
import com.resqlity.orm.enums.Comparator;
import com.resqlity.orm.models.responses.ResqlityResponse;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResqlityContext context = new ResqlityContext("3XZMPJf8wYqOYPpk+lufyqaSwh9M+BitbvJazAoTp3M=");
        try {
//            context.Insert(Customers.class)
//                    .Insert(new Customers("Berkay",
//                            "YALÇIN",
//                            "905534787057",
//                            "berkay.yalcin20hotmail.com",
//                            "Saka",
//                            "İstanbul",
//                            "İstanbul",
//                            "34000"))
//                    .Execute();
            ResqlityResponse<List<Customers>> response = context.Select(Customers.class)
                    .PageBy()
                    .Select("firstName")
                    .Where("firstName","aaron", Comparator.Equal)
                    .Query()
                    .<List<Customers>>Execute();
            System.out.println("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}