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

public class HotQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout hot_card_layout;
    TextView hot_card_textView;
    Button hot_card_button;

    String[] hot_questions = {"애인 소중이와 첫 만남 소감은?","가장 좋았던 자세는?","나의 최애 판타지는?","가장 좋았던 장소는?","애인과 첫 경험 순간을 묘사해 본다면?",
            "나의 가장 예민한 신체 부위는?","나를 흥분시킬 수 있는 최고 방법은?","만약 애인이 스스로 하는 것을 목격한다면?","내가 가장 좋아하는 코스프레는?",
            "내가 하고 싶을 때 보내는 시그널은?","내가 애인과 해보고 싶은 판타지는?","내가 애인과 해보고 싶은 장소는?","지금 내가 하고 싶은 스킨십을 손으로 하기",
            "내가 가장 잘한다고 생각하는 것은?","최근에 본 야(구) 동(영상)은?","내가 가장 중요하게 생각하는 신체 부위는?","가장 강렬했던 관계는?",
            "우리 관계에 대해 1~10점 점수를 매겨보자면?","내가 생각하는 이상적인 관계 시간은?","내가 가장 자신 있는 신체 부위는?","내가 애인이 매력적이라고 생각하는 신체 부위는?",
            "우리의 첫 관계와 지금 관계에서 달라진 점은?","언제 상대가 가장 매력적이게 보이는지?","가장 좋은 관계 시간대는?\n(ex 아침)","가장 싫은 자세는?",
            "가장 기억에 남는 장소는?","만약 야외에서 애인이 하고 싶다고 말한다면?","내가 스킨십 받았던 부위 중 가장 좋았던 곳은?","내가 생각하는 애인의 가장 엉큼한 행동은?",
            "1분 동안 서로 몸을 밀착한 상태로 움직이지 않기","지금 내가 하고 싶은 스킨십 입으로 하기","첫 만남에 진도는 어디까지 가능한지?","사랑스러운 부분에 뽀뽀해 주기",
            "애인 손 묶고 30초간 괴롭히기","자고 만남 추구에 대해 어떻게 생각하는지?","내가 이성을 볼 때 가장 많이 보는 부위는?","애인 옷 1개 벗기기",
            "언제 가장 하고 싶어지는지?","만약 신이 나에게 신체 부위 중 한 곳을 바꿀 수 있다고 한다면?","관계 중 애인이 하지 않았으면 하는 행동은?",
            "관계 중 도구를 이용하면 어떨 것 같은지?","내가 애인에게 시그널 보내다가 실패했던 적은?","나와 애인 중에 누가 더 변태인지?","요즘 누가 더 원하는지?",
            "한 번에 몇 번까지 할 수 있는지?","관계 후 내가 가장 좋아하는 상황은?\n(ex 샤워하기)","가장 처음 본 야(구) 동(영상)은 언제인지?","나의 신체 비밀 하나 말해주기",
            "주로 누가 먼저 하자고 하는지?","우리가 주로 관계를 나누기 시작하는 상황은 언제인지?"};

    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        hot_card_layout = findViewById(R.id.hot_card_layout);
        hot_card_textView = findViewById(R.id.hot_card_textView);
        hot_card_button = findViewById(R.id.hot_card_button);


        Toolbar hot_Toolbar = findViewById(R.id.hot_toolbar);
        setSupportActionBar(hot_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = hot_card_layout.getCameraDistance() * (scale + (scale / 3));

        Handler handler = new Handler();
        hot_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hot_card_layout.setCameraDistance(distance);
                hot_card_button.setEnabled(false);
                hot_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                hot_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_hot));
                                int random = (int) (Math.random()*hot_questions.length);
                                hot_card_textView.setText("Q.  " + hot_questions[random]);
                                question_count++;
                                Log.d(TAG, "현재 question_count : "+question_count);

                                hot_card_layout.setRotationY(-90);
                                hot_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hot_card_button.setEnabled(true);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HotQActivity.this);
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
                            mInterstitialAd.show(HotQActivity.this);
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
