package com.resqlity.android_connector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.resqlity.orm.ResqlityContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ResqlityContext context=new ResqlityContext();
    }
}