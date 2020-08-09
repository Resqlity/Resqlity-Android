package com.resqlity.android_connector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.resqlity.android_connector.models.Customers;
import com.resqlity.orm.ResqlityContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResqlityContext context = new ResqlityContext("3XZMPJf8wYqOYPpk+lufyqaSwh9M+BitbvJazAoTp3M=");
        try {
            context.Insert(Customers.class)
                    .Insert(new Customers("Berkay",
                            "YALÇIN",
                            "905534787057",
                            "berkay.yalcin20hotmail.com",
                            "Saka",
                            "İstanbul",
                            "İstanbul",
                            "34000"))
                    .Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}