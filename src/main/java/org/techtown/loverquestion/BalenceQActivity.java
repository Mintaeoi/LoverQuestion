package org.techtown.loverquestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class BalenceQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout balence_card_layout;
    TextView balence_card_textView;
    Button balence_card_button;

    String[] balence_questions={"방귀쟁이 애인\n\nvs\n\n트름쟁이 애인","메신저\n\nvs\n\n전화 통화","친구 같은 연애\n\nvs\n\n사랑꾼 연애",
            "이성친구와 1박2일 여행\n\nvs\n\n전 애인과 무박여행","1달 동안 전화만\n\nvs\n\n1달 동안 문자만","하루에 1000번 연락하는 애인\n\nvs\n\n하루에 1번 연락하는 애인",
            "바다\n\nvs\n\n산","짜장면\n\nvs\n\n짬뽕","무조건 연락 먼저 하는 애인\n\nvs\n\n절대 연락 먼저 안하는 애인","이성친구는 절대 허락 안하는 애인\n\nvs\n\n아무 상관 안하는 애인",
            "애인 이상형이 내 친구\n\nvs\n\n내 친구 이상형이 애인","매번 애인과 더치페이\n\nvs\n\n번갈아가면서 사주기","리드 하기\n\nvs\n\n리드 당하기","알코올 중독자 애인\n\nvs\n\n담배 중독자 애인",
            "양념 치킨\n\nvs\n\n후라이드 치킨","탕수육 부먹\n\nvs\n\n탕수육 찍먹","여름\n\nvs\n\n겨울","하루 종일 사생활 노출\n\nvs\n\n하루 종일 노출로 생활",
            "태어나자마자 10억 받기\n\nvs\n\n40살에 30억 받기","평생 양치 안하기\n\nvs\n\n평생 머리 안 감기","모르는 사람 집에 애인 속옷\n\nvs\n\n애인 집에 모르는 사람 속옷",
            "월요일에 휴가 쓰기\n\nvs\n\n금요일에 휴가 쓰기","입 냄새 심한 애인\n\nvs\n\n발 냄새 심한 애인","1년 동안 뽀뽀금지\n\nvs\n\n1년 동안 손잡기 금지",
            "매워 죽을 것 같은 음식\n\nvs\n\n느끼해 죽을 것 같은 음식","전 애인과 갔던 여행 장소 데려가기\n\nvs\n\n전 애인에게 사줬던 선물 사주기","맞춤법 틀리는 애인\n\nvs\n\n띄어쓰기 안 하는 애인",
            "강아지상\n\nvs\n\n고양이상","현실적인 조언\n\nvs\n\n공감해 주는 위로","완전한 무계획 여행\n\nvs\n\n완전한 계획형 여행","이성친구 많은 애인\n\nvs\n\n친구 없어 매일 놀아줘야 하는 애인",
            "털북숭이 애인\n\nvs\n\n대머리 애인","1주일에 10만 원씩 평생 받기\n\nvs\n\n그냥 한 번에 3억 받기","클럽 좋아하는 애인\n\nvs\n\n헌팅 포차 좋아하는 애인",
            "주량 3잔인 애인\n\nvs\n\n주량 3병인 애인","1년간 10명과 사귀었던 애인\n\nvs\n\n10년간 1명과 사귀었던 애인","게임 중독 애인\n\nvs\n\nSNS 중독 애인",
            "나 포함 모두에게 스킨십 잘하는 애인\n\nvs\n\n나 포함 모두에게 스킨십 안 하는 애인","싸우면 시간을 조금 가져야 한다\n\nvs\n\n싸우면 바로 이야기해야 한다",
            "싸움 빈도는 많지만 강도가 약한 애인\n\nvs\n\n싸움 빈도는 적지만 강도가 강한 애인","5억 혼자 받기\n\nvs\n\n10억 받고 원수는 20억 받기",
            "오이 케이크 먹기\n\nvs\n\n김치 케이크 먹기","100원 동전으로 500만 원 받기\n\nvs\n\n5만 원 지폐로 400만 원 받기","계란 후라이\n\nvs\n\n계란 말이",
            "노래 부르기\n\nvs\n\n춤 추기","여가시간에 사람들과 놀기\n\nvs\n\n여가 시간에 혼자 시간 보내기","봄\n\nvs\n\n가을","집에서 영화 보기\n\nvs\n\n영화관에서 영화 보기",
            "물고기\n\nvs\n\n육고기","온라인 쇼핑\n\nvs\n\n오프라인 쇼핑"};


    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balence);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        balence_card_layout = findViewById(R.id.balence_card_layout);
        balence_card_textView = findViewById(R.id.balence_card_textView);
        balence_card_button = findViewById(R.id.balence_card_button);
        
        Toolbar future_Toolbar = findViewById(R.id.balence_toolbar);
        setSupportActionBar(future_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = balence_card_layout.getCameraDistance() * (scale + (scale / 3));

        Handler handler = new Handler();

        balence_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                balence_card_layout.setCameraDistance(distance);
                balence_card_button.setEnabled(false);
                balence_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                balence_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_balence));
                                int random = (int) (Math.random()*balence_questions.length);
                                balence_card_textView.setText("" + balence_questions[random]);
                                question_count++;
                                Log.d(TAG, "현재 question_count : "+question_count);

                                balence_card_layout.setRotationY(-90);
                                balence_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        balence_card_button.setEnabled(true);
                    }
                }, 500);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        //ca-app-pub-3940256099942544/1033173712 공용 앱 전면광고 ID
        //ca-app-pub-7754099565688705/3711257480 나의 앱 전면광고 ID
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    public void onAdImpression() {
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mInterstitialAd = null;
                    }
                });
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_back:{
                AlertDialog.Builder builder = new AlertDialog.Builder(BalenceQActivity.this);
                builder.setTitle("주의");
                builder.setMessage("홈으로 돌아가시겠습니까?");
                Log.d(TAG, "dialog 메세지 띄움");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("number_before",1);
                        startActivity(intent);
                        finish();
                        if(mInterstitialAd != null){
                            mInterstitialAd.show(BalenceQActivity.this);
                        }else {
                            Log.d(TAG, "광고가 실행되지 않음");
                        }
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Log.d(TAG, "dialog 메세지 닫음");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
