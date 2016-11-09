package com.example.frology.tutu_android_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sheduleRun(View view) { //При нажатии на кнопку Расписание
        intent = new Intent(this, SheduleActivity.class);
        startActivity(intent);
    }

    public void aboutRun(View view) {  //При нажатии на кнопку О приложении
        intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
