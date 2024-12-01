package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class edit_information extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information);

        // 버튼 설정
        findViewById(R.id.edit_name_button).setOnClickListener(view -> {
            // 이름 수정 로직 추가
        });

        findViewById(R.id.edit_profile_button).setOnClickListener(view -> {
            // 프로필 사진 수정 로직 추가
        });

        findViewById(R.id.edit_dob_button).setOnClickListener(view -> {
            // 생년월일 수정 로직 추가
        });

        // 뒤로가기 버튼
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(edit_information.this, ListActivity.class);
            startActivity(intent);
        });
    }
}