package com.example.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    private EditText writePostText;  // 글 작성 필드
    private Button sendPostButton;  // 전송 버튼
    private LinearLayout postList;  // 글 리스트를 표시할 레이아웃

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // 뷰 초기화
        writePostText = findViewById(R.id.write_post_text);
        sendPostButton = findViewById(R.id.send_post_button);
        postList = findViewById(R.id.post_list);

        // 네비게이션 버튼 설정
        setupNavigationButtons();

        // "게시" 버튼 클릭 리스너
        sendPostButton.setOnClickListener(view -> {
            String newPostText = writePostText.getText().toString().trim(); // 입력한 텍스트 읽어오기
            if (!newPostText.isEmpty()) {
                addNewPost("Nickname", newPostText); // 닉네임과 글 내용 추가
                writePostText.setText(""); // 입력 필드 초기화
            }
        });
    }

    // 네비게이션 버튼 초기화 메서드
    private void setupNavigationButtons() {
        // List 버튼 클릭 시 ListActivity로 이동
        findViewById(R.id.list_nav_button).setOnClickListener(view -> {
            Intent intent = new Intent(CommunityActivity.this, ListActivity.class);
            startActivity(intent);
        });

        // Home 버튼 클릭 시 MainActivity로 이동
        findViewById(R.id.home_nav_button).setOnClickListener(view -> {
            Intent intent = new Intent(CommunityActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // 새로운 글을 추가하는 메서드
    private void addNewPost(String userName, String postContent) {
        // post_item.xml 레이아웃을 불러옴
        LayoutInflater inflater = LayoutInflater.from(this);
        View postView = inflater.inflate(R.layout.post_item, postList, false);

        // 뷰 초기화
        TextView userNameView = postView.findViewById(R.id.user_name);
        TextView postContentView = postView.findViewById(R.id.post_content);
        ImageButton likeButton = postView.findViewById(R.id.like_button);

        // 값 설정
        userNameView.setText(userName);
        postContentView.setText(postContent);

        // 좋아요 버튼 클릭 리스너
        likeButton.setOnClickListener(view -> {
            Drawable currentDrawable = likeButton.getDrawable();
            // 현재 Drawable을 비교하여 상태 토글
            if (currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.like).getConstantState())) {
                likeButton.setImageResource(R.drawable.liked); // liked.png로 변경
            } else {
                likeButton.setImageResource(R.drawable.like); // like.png로 변경
            }
        });

        // 새로운 게시물을 리스트의 맨 위에 추가
        postList.addView(postView, 0);
    }
}
