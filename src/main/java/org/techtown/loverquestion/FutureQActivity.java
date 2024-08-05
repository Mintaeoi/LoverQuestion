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

public class FutureQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout future_card_layout;
    TextView future_card_textView;
    Button future_card_button;


    String[] future_questions={"나는 어떠한 모습으로 죽음을 맞이할 것 같은지?","나의 버킷리스트 3가지 말해주기","내 수명을 조절할 수 있다면 몇 살까지 살고 싶은지?"
            ,"나랑 가고 싶은 국내 여행 장소는?","나랑 가고 싶은 해외 여행 장소는?","결혼을 하면 신혼여행은 어디로 갈지?","아이를 낳는다면 딸과 아들 각각 몇 명씩?"
            ,"이번 주 데이트 장소 정하기!","내가 생각할 때 미래의 애인의 모습은?","나중에 내가 살고 싶은 지역은?","5년 뒤 우리의 모습 생각해 보기"
            ,"갖고 싶은 선물 2가지 말하기"," 나랑 도전해 보고 싶은 일은?","만약 애인 컴퓨터에 야구 동영상 폴더를 발견한다면?"
            ,"앞으로 다툼을 대비하여 우리만의 화해 방법 만들기","만약 애인의 서프라이즈를 미리 알게 됐을 때 나의 대처는?","나랑 먹어보고 싶은 음식은?"
            ,"만약 애인이 나보다 성욕이 강하다면?"," 애인이 나 몰래 우는 것을 목격했을 때 나는 어떡할지?","아직 애인과 안 해본 데이트 중 해보고 싶은 것이 있다면?"
            ,"애인이 단둘이 이성친구를 만나러 간다고 말을 한다면?","애인이 이성과 실수를 했다면?"
            ,"10년 뒤 애인과 나 둘 중에 누가 더 부자일 것 같은지?","잘 된다는 가정하에 커플 유튜브의 기회가 온다면 할 것인지?","애인이 무조건 돈을 빌려달라고 할 때 얼마까지 가능한지?"
            ,"앞으로 미래를 그려나갈 때 가장 중요한 것은 무엇일지?","애인과 1년 동안 5평짜리 방에서 살아본다면?","애인과 1년 동안 500평짜리 방에서 살아본다면?"
            ,"한 문장으로 우리의 미래를 표현해본다면?","지금 바로 애인과 해외에 가서 1년 동안 살아야 한다면 어느 나라로?","앞으로 더 잘난 나를 위해 앞으로의 다짐 어필"
            ,"앞으로 10년 동안 나의 계획은?","애인과 한 번 더 하고 싶은 활동이 있다면?","애인과 함께 키우고 싶은 반려동물은?","우리에게 1주일 동안 스킵쉽이 금지된다면?"
            ,"우리 사이가 소원해진다면 어떻게 해결해 나아갈지?","세상이 무너지고 애인과 떨어졌을 때를\n대비해 다시 만날 장소를 정한다면?"
            ,"내가 이것(?)을 할 때 애인이 이것(?)을 해줬으면 좋겠다 싶은 것은?","방귀를 뀐다면 애인의 반응은?","나는 애인의 이것(?)까지 봐줄 수 있다."
            ,"애인이 다음 생에 다시 태어난다면 무엇으로 환생할 것 같은지?","1년 뒤 우리의 모습 생각해 보기","애인이 몰래 클럽 간 것을 목격한다면?"
            ,"만약 애인 부모님이 우리의 결혼을 반대한다면?","무인도에 떨어진다면 가지고 가야 할 물품 (2가지씩)","애인과 동거를 하게 된다면 가사분담을 어떻게?"
            ,"만약 애인이 해외로 유학이나 출장을 길게 가게 된다면?","애인과 해보고 싶은 게임은?","애인과 해보고 싶은 운동은?","애인에게 바라는 점"};


    int check = 0; // 뒷면
    int question_count = 1; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        future_card_layout = findViewById(R.id.future_card_layout);
        future_card_textView = findViewById(R.id.future_card_textView);
        future_card_button = findViewById(R.id.future_card_button);

        Toolbar future_Toolbar = findViewById(R.id.future_toolbar);
        setSupportActionBar(future_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = future_card_layout.getCameraDistance() * (scale + (scale / 3));

        Handler handler = new Handler();

        future_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                future_card_layout.setCameraDistance(distance);
                future_card_button.setEnabled(false);
                future_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                future_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_future));
                                int random = (int) (Math.random()*future_questions.length);
                                future_card_textView.setText("Q.  " + future_questions[random]);
                                question_count++;
                                Log.d(TAG, "현재 question_count : "+question_count);

                                future_card_layout.setRotationY(-90);
                                future_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        future_card_button.setEnabled(true);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(FutureQActivity.this);
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
                            mInterstitialAd.show(FutureQActivity.this);
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
