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
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    private EditText writePostText;  // 글 작성 필드
    private Button sendPostButton;  // 전송 버튼
    private LinearLayout postList;  // 글 리스트를 표시할 레이아웃
    private String userEmail;  // 사용자 이메일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // userEmail을 loginActivity에서 받은 값으로 초기화
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        // 이메일에서 "@" 이전의 부분만 추출 (사용자 이름처럼 사용)
        String userName = extractUserNameFromEmail(userEmail);

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
                addNewPost(userName, newPostText); // 이메일에서 추출한 userName을 사용
                writePostText.setText(""); // 입력 필드 초기화
            }
        });
    }

    // 이메일에서 "@" 이전 부분만 추출하는 메서드
    private String extractUserNameFromEmail(String email) {
        if (email != null && email.contains("@")) {
            return email.substring(0, email.indexOf("@"));  // "@" 앞부분을 사용자 이름처럼 사용
        }
        return email; // 만약 "@"이 없다면 이메일 전체를 사용
    }

    // 네비게이션 버튼 초기화 메서드
    private void setupNavigationButtons() {
        // List 버튼 클릭 시 ListActivity로 이동

        findViewById(R.id.list_nav_button).setOnClickListener(view -> {
            Intent intent = new Intent(CommunityActivity.this, ListActivity.class);
            intent.putExtra("userEmail", userEmail);  // 사용자 이름을 전달
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
        TextView postTimeView = postView.findViewById(R.id.post_time); // 게시 시간
        ImageButton likeButton = postView.findViewById(R.id.like_button);
        EditText commentInput = postView.findViewById(R.id.comment_input);
        Button commentButton = postView.findViewById(R.id.comment_button);
        LinearLayout commentList = postView.findViewById(R.id.comment_list);

        // 값 설정
        userNameView.setText(userName);  // 이메일에서 추출한 userName을 사용
        postContentView.setText(postContent);
        postTimeView.setText("방금 전"); // 게시 시간은 간단히 초기 값으로 설정

        // 좋아요 버튼 클릭 리스너
        likeButton.setOnClickListener(view -> {
            Drawable currentDrawable = likeButton.getDrawable();
            if (currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.like).getConstantState())) {
                likeButton.setImageResource(R.drawable.liked); // liked.png로 변경
            } else {
                likeButton.setImageResource(R.drawable.like); // like.png로 변경
            }
        });

        // 댓글 작성 버튼 클릭 리스너
        commentButton.setOnClickListener(view -> {
            String commentText = commentInput.getText().toString().trim();
            if (!commentText.isEmpty()) {
                // 새 댓글을 추가
                addNewComment(commentList, userName, commentText);  // userEmail을 사용
                commentInput.setText(""); // 입력 필드 초기화
            }
        });

        // 새로운 게시물을 리스트의 맨 위에 추가
        postList.addView(postView, 0);
    }

    // 댓글을 추가하는 메서드
    private void addNewComment(LinearLayout commentList, String userName, String commentContent) {
        // 댓글의 뷰 생성
        LinearLayout commentView = new LinearLayout(this);
        commentView.setOrientation(LinearLayout.HORIZONTAL);
        commentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // 프로필 이미지
        ImageView profileImage = new ImageView(this);
        profileImage.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
        profileImage.setImageResource(R.drawable.profile); // 프로필 이미지
        commentView.addView(profileImage);

        // 댓글 내용
        TextView commentTextView = new TextView(this);
        commentTextView.setText(userName + ": " + commentContent);  // userEmail을 사용
        commentTextView.setTextSize(14);
        commentTextView.setTextColor(getResources().getColor(android.R.color.black));
        commentView.addView(commentTextView);

        // 좋아요 버튼
        ImageButton likeButton = new ImageButton(this);
        likeButton.setLayoutParams(new LinearLayout.LayoutParams(48, 48));
        likeButton.setImageResource(R.drawable.like);
        likeButton.setBackgroundResource(android.R.color.transparent);
        likeButton.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
        likeButton.setOnClickListener(v -> {
            Drawable currentDrawable = likeButton.getDrawable();
            if (currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.like).getConstantState())) {
                likeButton.setImageResource(R.drawable.liked); // liked.png로 변경
            } else {
                likeButton.setImageResource(R.drawable.like); // like.png로 변경
            }
        });
        commentView.addView(likeButton);

        // 댓글을 댓글 리스트에 추가
        commentList.addView(commentView);
    }
}
