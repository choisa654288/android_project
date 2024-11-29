package com.example.project;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepCount = 0; // 걸음 수 저장 변수
    private TextView stepCountTextView; // 걸음 수를 표시할 TextView

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

        // 걸음 수 표시 TextView 연결
        stepCountTextView = findViewById(R.id.description_text);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI);
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
            stepCount = (int) event.values[0];
            updateStepCountUI();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 정확도 변경 이벤트 처리 (필요 없으면 비워둡니다)
    }

    private void updateStepCountUI() {
        if (stepCountTextView != null) {
            stepCountTextView.setText("오늘의 걸음: " + stepCount + " 걸음");
        }
    }
}
