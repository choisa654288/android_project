package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Home 버튼 클릭 시 MainActivity로 이동
        findViewById(R.id.home_nav_button).setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Community 버튼 클릭 시 CommunityActivity로 이동
        findViewById(R.id.community_nav_button).setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, CommunityActivity.class);
            startActivity(intent);
        });

        //회원정보 수정 버튼 클릭시 이동
        findViewById(R.id.edit_info_button).setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, edit_information.class);
            startActivity(intent);
        });

    }

}
