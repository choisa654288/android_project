package com.example.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ListActivity extends AppCompatActivity implements SensorEventListener {

    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 100;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepCount = 0; // 현재 걸음 수
    private int initialStepCount = -1; // 앱 실행 시 초기 걸음 수 저장
    private TextView stepCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // 사용자 이름 또는 이메일 처리
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");
        String userName = intent.getStringExtra("userName");

        TextView welcomeText = findViewById(R.id.list_text);
        if (userName != null && !userName.isEmpty()) {
            welcomeText.setText(userName + " 님 안녕하세요!");
        } else if (userEmail != null && !userEmail.isEmpty()) {
            String userNickName = userEmail.split("@")[0];
            welcomeText.setText(userNickName + " 님 안녕하세요!");
        }

        // 걸음 수를 표시할 TextView 연결
        stepCountTextView = findViewById(R.id.description_text);

        // 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        } else {
            // 권한이 이미 허용된 경우
            initializeStepCounter();
        }

        // 네비게이션 버튼 처리
        findViewById(R.id.home_nav_button).setOnClickListener(view -> {
            Intent homeIntent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(homeIntent);
        });

        findViewById(R.id.community_nav_button).setOnClickListener(view -> {
            Intent communityIntent = new Intent(ListActivity.this, CommunityActivity.class);
            startActivity(communityIntent);
        });

        findViewById(R.id.edit_info_button).setOnClickListener(view -> {
            Intent editIntent = new Intent(ListActivity.this, edit_information.class);
            startActivity(editIntent);
        });
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 허용됨
                initializeStepCounter();
            } else {
                // 권한 거부됨
                Toast.makeText(this, "만보기 기능을 사용하려면 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                stepCountTextView.setText("만보기 권한이 필요합니다.");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // 만보기 센서 초기화
    private void initializeStepCounter() {
        // SensorManager 초기화
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }

        // 센서 확인
        if (stepCounterSensor == null) {
            Toast.makeText(this, "만보기 센서를 지원하지 않는 기기입니다.", Toast.LENGTH_LONG).show();
            stepCountTextView.setText("만보기 기능을 사용할 수 없습니다.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (initialStepCount < 0) {
                initialStepCount = (int) event.values[0];
            }
            stepCount = (int) event.values[0] - initialStepCount;
            updateStepCountUI();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 필요하지 않으면 비워둡니다.
    }

    private void updateStepCountUI() {
        runOnUiThread(() -> stepCountTextView.setText("오늘의 걸음: " + stepCount + " 걸음"));
    }
}
