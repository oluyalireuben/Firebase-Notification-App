package com.example.notificationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navHome:
                            // Start HomeActivity or perform relevant action
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.navNotification:
                            // Start ChangePasswordActivity or perform relevant action
                            startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                            Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.navLogout:
                            // Start LoginActivity or perform relevant action
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            Toast.makeText(MainActivity.this, "You have logged out", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                    finish();
                    return false;


                }
            };


}