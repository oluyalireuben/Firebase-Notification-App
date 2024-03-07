package com.example.notificationapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notificationapp.databinding.ActivityNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    String title, message;
    Long time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        long currentTimeInMillis = System.currentTimeMillis();
        createChannel();
        binding.allowNotification.setOnClickListener(view -> {
            scheduleNotification();
            startActivity(new Intent(NotificationActivity.this , MainActivity.class));
            finish();
        });

        FirebaseDatabase.getInstance().getReference().child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("title").getValue(String.class);
                message = snapshot.child("message").getValue(String.class);
                time = snapshot.child("time").getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification() {
        Intent intent = new Intent(NotificationActivity.this, Notification.class);
        intent.putExtra("titleExtra", title);
        intent.putExtra("messageExtra", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                NotificationActivity.this,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        String name = "Vaccination";
        String desc = "Channel description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notificationChannel", name, importance);
        channel.setDescription(desc);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}