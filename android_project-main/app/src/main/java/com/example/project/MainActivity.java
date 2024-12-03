package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Additional setup for buttons if needed.
        // For example, setting click listeners on navigation buttons
        findViewById(R.id.list_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            // Home button can be set as active, so no click listener needed
        });

        findViewById(R.id.community_button).setOnClickListener(view->{
            Intent intent=new Intent(MainActivity.this, CommunityActivity.class);
            startActivity(intent);
        });

    }
}