package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase 인증 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance(); // FirebaseAuth 인스턴스 가져오기

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button loginButton = findViewById(R.id.button); // 로그인 버튼 객체 생성

        loginButton.setOnClickListener(view -> loginUser()); // 버튼 클릭 시 loginUser 함수 호출

        // 회원가입 버튼 설정
        Button registerButton = findViewById(R.id.button2); // 회원가입 버튼 객체 생성
        registerButton.setOnClickListener(view -> {
            // 회원가입 화면으로 이동
            Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
            startActivity(intent); // RegisterActivity로 이동
        });


    }

    private void loginUser() {
        String email = ((EditText) findViewById(R.id.EmailAddress)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password)).getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(loginActivity.this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // 로그인 성공 시 MainActivity로 이동
                        String userEmail = auth.getCurrentUser().getEmail();
                        Toast.makeText(loginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(loginActivity.this, ListActivity.class);
                        Intent intent1 = new Intent(loginActivity.this, CommunityActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        intent1.putExtra("userEmail", userEmail);
                        startActivity(intent);
                        startActivity(intent1);


                        finish(); // 현재 액티비티 종료 (뒤로가기 방지)

                    } else {
                        // 로그인 실패 시
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "알 수 없는 오류";
                        Toast.makeText(loginActivity.this, "로그인 실패: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
