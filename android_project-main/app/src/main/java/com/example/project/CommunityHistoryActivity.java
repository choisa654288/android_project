package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommunityHistoryActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private LinearLayout historyPostList; // 게시물을 표시할 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_history);  // activity_community_history.xml 설정

        firestore = FirebaseFirestore.getInstance();
        historyPostList = findViewById(R.id.history_post_list);

        // 뒤로가기 버튼 처리
        Button backButton = findViewById(R.id.back_button);  // XML에서 버튼 ID 설정
        backButton.setOnClickListener(view -> {
            // 뒤로가기 버튼을 누르면 ListActivity로 돌아갑니다.
            Intent intent = new Intent(CommunityHistoryActivity.this, ListActivity.class);
            startActivity(intent);
            finish();  // 현재 액티비티 종료
        });

        // Firebase Firestore에서 게시물 데이터 가져오기
        fetchCommunityHistory();
    }

    private void fetchCommunityHistory() {
        firestore.collection("posts")
                .orderBy("timestamp") // 최신 게시물이 아래로 표시되도록 정렬
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            displayPost(post); // 게시물을 화면에 표시
                        }
                    } else {
                        Toast.makeText(this, "기록을 불러오지 못했습니다: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayPost(Post post) {
        // 게시물을 동적으로 추가
        LinearLayout postLayout = new LinearLayout(this);
        postLayout.setOrientation(LinearLayout.HORIZONTAL);  // 수평으로 배치

        TextView postView = new TextView(this);
        postView.setText(post.getUserName() + ": " + post.getPostContent());
        postView.setTextSize(16);
        postView.setPadding(16, 16, 16, 16);

        // 날짜를 포맷하여 추가
        String formattedDate = formatTimestamp(post.getTimestamp());
        TextView dateView = new TextView(this);
        dateView.setText(formattedDate);
        dateView.setTextSize(14);
        dateView.setPadding(16, 16, 16, 16);
        dateView.setTextColor(getResources().getColor(android.R.color.darker_gray));  // 날짜 색상 설정

        // 레이아웃에 게시물 내용과 날짜를 추가
        postLayout.addView(postView);
        postLayout.addView(dateView);

        // 수평 레이아웃을 historyPostList에 추가
        historyPostList.addView(postLayout);
    }

    private String formatTimestamp(long timestamp) {
        // timestamp를 Date로 변환하고, 포맷을 지정
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    // 뒤로가기 버튼이 눌렸을 때 ListActivity로 돌아가도록 오버라이드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CommunityHistoryActivity.this, ListActivity.class);
        startActivity(intent);
        finish();  // 현재 액티비티 종료
    }
}
