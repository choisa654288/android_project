package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_history);  // activity_post.xml을 설정

        // 뒤로가기 버튼 처리
        Button backButton = findViewById(R.id.back_button);  // XML에서 버튼 ID 설정
        backButton.setOnClickListener(view -> {
            // 뒤로가기 버튼을 누르면 ListActivity로 돌아갑니다.
            Intent intent = new Intent(PostActivity.this, ListActivity.class);
            startActivity(intent);
            finish();  // 현재 액티비티 종료
        });
    }

    // 뒤로가기 버튼이 눌렸을 때 ListActivity로 돌아가도록 오버라이드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PostActivity.this, ListActivity.class);
        startActivity(intent);
        finish();  // 현재 액티비티 종료
    }
}
