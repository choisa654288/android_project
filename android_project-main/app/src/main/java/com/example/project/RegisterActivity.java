package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth; // Firebase 인증 객체
    private FirebaseFirestore firestore; // Firebase Firestore 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance(); // FirebaseAuth 인스턴스 가져오기
        firestore = FirebaseFirestore.getInstance(); // FirebaseFirestore 인스턴스 가져오기

        Button registerButton = findViewById(R.id.buttonRegister); // 회원가입 버튼 객체 생성

        registerButton.setOnClickListener(view -> registerUser()); // 버튼 클릭 시 registerUser 함수 호출
    }

    private void registerUser() {
        String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Firestore에 사용자 세부 정보 저장
                        saveUserData(username, email);

                        // 회원가입 성공 메시지 표시
                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                        // 메인 액티비티로 이동
                        navigateToMainActivity();
                    } else {
                        // 회원가입 실패 시 사용자에게 메시지 표시
                        Toast.makeText(RegisterActivity.this,
                                "회원가입 실패: " + (task.getException() != null ? task.getException().getMessage() : ""),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserData(String username, String email) {
        HashMap<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);

        firestore.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference ->
                        Log.d("RegisterActivity", "DocumentSnapshot added with ID: " + documentReference.getId())
                )
                .addOnFailureListener(e ->
                        Log.e("RegisterActivity", "문서 추가 오류", e)
                );
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // 현재 액티비티를 종료하여 뒤로가기 버튼으로 돌아가지 않도록 설정
    }
}