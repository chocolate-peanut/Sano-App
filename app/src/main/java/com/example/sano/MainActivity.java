package com.example.sano;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    //initialize variable
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        //add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable));
        //add 3 more...
    }
}