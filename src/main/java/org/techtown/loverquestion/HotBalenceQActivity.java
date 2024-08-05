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

public class HotBalenceQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout hotbalence_card_layout;
    TextView hotbalence_card_textView;
    Button hotbalence_card_button;

    String[] hotbalence_questions = {"내가 리드하기\n\nvs\n\n내가 리드 당하기","불 켜고 하기\n\nvs\n\n불 끄고 하기",
            "매일 3번씩 하기\n\nvs\n\n1달에 1번 하기","내 거기에 애인 손\n\nvs\n\n애인 거기에 내 손","도구\n\nvs\n\n손",
            "손\n\nvs\n\n입","두께1cm길이15cm\n\nvs\n\n두께4cm길이5cm","가슴이 절벽\n\nvs\n\n엉덩이가 절벽","내가 묶이기\n\nvs\n\n애인을 묶기",
            "경험이 완전 많은 애인\n\nvs\n\n경험이 한 번도 없는 애인","3분\n\nvs\n\n3시간","한 자세로만 하기\n\nvs\n\n여러 자세로 하기",
            "할 때 아무 소리 안내는 애인\n\nvs\n\n할 때 소음 내는 애인","하고 사귀기\n\nvs\n\n사귀고 하기","살짝 입고 하기\n\nvs\n\n아예 안 입고 하기",
            "부드럽게 하기\n\nvs\n\n거칠게 하기","맨정신에 하기\n\nvs\n\n취한 정신에 하기","상체\n\nvs\n\n하체","섹시한 애인\n\nvs\n\n귀여운 애인",
            "서양\n\nvs\n\n동양","야한 얼굴\n\nvs\n\n야한 몸매","낮져밤이\n\nvs\n\n낮이밤져","남이 하는 거 보기\n\nvs\n\n내가 하는 거 남이 보기",
            "깊숙히\n\nvs\n\n빠르게","입에 마무리\n\nvs\n\n안에 마무리","앞으로 하기\n\nvs\n\n뒤로 하기","대화하면서 하기\n\nvs\n\n몸으로만 대화하기",
            "시각\n\nvs\n\n청각","내가 애인 씻겨주기\n\nvs\n\n애인이 나를 씻겨주기","실내\n\nvs\n\n실외","사각팬티\n\nvs\n\n삼각팬티",
            "검은색 스타킹\n\nvs\n\n살색 스타킹","오피스룩\n\nvs\n\n교복","30초 동안 안아서 마음대로 하기","10초 동안 마음대로 만지기","귓속말로 야한 말하기",
            "10초 동안 키스하기","애인 옷 1개 벗기기","눈 가리기\n\nvs\n\n손 묶기","반말\n\nvs\n\n존댓말","거기에 털 엄청 많기\n\nvs\n\n거기에 털 아예 없기",
            "동영상 보다가 애인한테 걸리기\n\nvs\n\n동영상 보다가 엄마한테 걸리기","상의 탈의 하기\n\nvs\n\n하의 탈의 하기","노 브라 외출\n\nvs\n\n노 팬티 외출",
            "숨겨진 자식 있는 애인\n\nvs\n\n이혼 3번 한 애인","손가락 깨물기\n\nvs\n\n발가락 깨물기","하고 씻기\n\nvs\n\n씻고 하기",
            "목에 키스마크 남기기\n\nvs\n\n가슴에 키스마크 남기기","때리기\n\nvs\n\n맞기","내가 만지기\n\nvs\n\n내가 만져지기"};

    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotbalence);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        hotbalence_card_layout = findViewById(R.id.hotbalence_card_layout);
        hotbalence_card_textView = findViewById(R.id.hotbalence_card_textView);
        hotbalence_card_button = findViewById(R.id.hotbalence_card_button);


        Toolbar hotbalence_Toolbar = findViewById(R.id.hotbalence_toolbar);
        setSupportActionBar(hotbalence_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = hotbalence_card_layout.getCameraDistance() * (scale + (scale / 3));

        Handler handler = new Handler();
        hotbalence_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hotbalence_card_layout.setCameraDistance(distance);
                hotbalence_card_button.setEnabled(false);
                hotbalence_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                hotbalence_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_hotbalence));
                                int random = (int) (Math.random()*hotbalence_questions.length);
                                hotbalence_card_textView.setText("" + hotbalence_questions[random]);
                                question_count++;
                                Log.d(TAG, "현재 question_count : "+question_count);

                                hotbalence_card_layout.setRotationY(-90);
                                hotbalence_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hotbalence_card_button.setEnabled(true);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HotBalenceQActivity.this);
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
                            mInterstitialAd.show(HotBalenceQActivity.this);
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