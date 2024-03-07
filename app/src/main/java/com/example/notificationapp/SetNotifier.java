package com.example.notificationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notificationapp.databinding.ActivitySetNotifierBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SetNotifier extends AppCompatActivity {
    ActivitySetNotifierBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetNotifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Long time = getTime();

        String username = getIntent().getStringExtra("username");
        String uid = getIntent().getStringExtra("uid");
        binding.username.setText(username);

        binding.notificationButton.setOnClickListener(view -> {
            binding.progress.setVisibility(View.VISIBLE);
            FirebaseDatabase.getInstance().getReference().child(uid).child("time").setValue(time);
            FirebaseDatabase.getInstance().getReference().child(uid).child("title").setValue(
                    binding.title.getText().toString()
            );
            FirebaseDatabase.getInstance().getReference().child(uid).child("message").setValue(
                    binding.message.getText().toString()
            ).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    binding.progress.setVisibility(View.GONE);
                    Toast.makeText(this, "Notification set", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SetNotifier.this , ViewPatients.class));
                    finish();
                }
            });
        });
    }
    private Long getTime() {
        int minute = binding.timePicker.getMinute();
        int hour = binding.timePicker.getHour();
        int day = binding.datePicker.getDayOfMonth();
        int month = binding.datePicker.getMonth();
        int year = binding.datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year ,month , day , hour , minute);
        return calendar.getTimeInMillis();
    }
}