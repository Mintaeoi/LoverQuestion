package org.techtown.loverquestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    Button button_past_question;
    Button button_present_question;
    Button button_future_question;
    Button button_balence_question;
    Button button_hotbalence_question;
    Button button_hot_question;

    private long backKeyPressedTime = 0;
    private Toast toast;

    private AdView adView_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        button_past_question = findViewById(R.id.button_past_question);
        button_present_question = findViewById(R.id.button_present_question);
        button_future_question = findViewById(R.id.button_future_question);
        button_balence_question = findViewById(R.id.button_balence_question);
        button_hotbalence_question = findViewById(R.id.button_hotbalence_question);
        button_hot_question = findViewById(R.id.button_hot_question);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        button_past_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PastQActivity.class);
                startActivity(intent);

                finish();
            }
        });
        button_present_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PresentQActivity.class);
                startActivity(intent);

                finish();
            }
        });
        button_future_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FutureQActivity.class);
                startActivity(intent);

                finish();
            }
        });
        button_balence_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BalenceQActivity.class);
                startActivity(intent);

                finish();
            }
        });
        button_hotbalence_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HotBalenceQActivity.class);
                startActivity(intent);

                finish();
            }
        });
        button_hot_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HotQActivity.class);
                startActivity(intent);

                finish();
            }
        });


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        adView_main = findViewById(R.id.adView_main);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_main.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("\n" + "ca-app-pub-3940256099942544/6300978111");

        //ca-app-pub-3940256099942544/6300978111 공용 앱 하단 배너 ID
        //ca-app-pub-7754099565688705/5870516964 내 앱 하단 배너 ID
    }










    @Override
    public void onBackPressed() {

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "앱을 종료하시려면 한 번 더 누르세요!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
            toast.cancel();
        }
    }
}