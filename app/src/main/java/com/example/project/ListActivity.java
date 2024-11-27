package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // 사용자 이메일과 이름을 받기
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail"); // 이메일을 받음
        String userName = intent.getStringExtra("userName");  // 사용자 이름을 받음

        TextView welcomeText = findViewById(R.id.list_text);

        // userName이 전달되었다면 그것을 표시하고, userEmail이 전달되었다면 이메일 앞부분을 사용자 이름으로 사용
        if (userName != null && !userName.isEmpty()) {
            welcomeText.setText(userName + " 님 안녕하세요!");
        } else if (userEmail != null && !userEmail.isEmpty()) {
            String userNickName = userEmail.split("@")[0];  // 이메일에서 사용자 이름 추출
            welcomeText.setText(userNickName + " 님 안녕하세요!");
        }

        // Home 버튼 클릭 시 MainActivity로 이동
        findViewById(R.id.home_nav_button).setOnClickListener(view -> {
            Intent intent1 = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent1);
        });

        // Community 버튼 클릭 시 CommunityActivity로 이동
        findViewById(R.id.community_nav_button).setOnClickListener(view -> {
            Intent intent2 = new Intent(ListActivity.this, CommunityActivity.class);
            startActivity(intent2);
        });

        // 회원정보 수정 버튼 클릭 시 이동
        findViewById(R.id.edit_info_button).setOnClickListener(view -> {
            Intent intent3 = new Intent(ListActivity.this, edit_information.class); // 클래스 이름도 EditInformationActivity로 수정
            startActivity(intent3);
        });
    }
}
