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

public class PresentQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout present_card_layout;
    TextView present_card_textView;
    Button present_card_button;


    String[] present_questions={"애인의 장점 3가지","애인이 귀여워 보이는 순간은?","평상시 이것만큼은 안 했으면 하는 것은?"
            ,"지금 나의 달라진 모습이나 행동은?","가장 좋아하는 동물은?","가장 좋아하는 음식은?","가장 좋아하는 과일은?"
            ,"가장 좋아하는 색깔은??","나의 가족 구성원 소개하기","가장 좋아하는 영화 장르는?","우리 기념일을 어떻게 챙기면 좋을까?"
            ,"남사친, 여사친 어느 정도까지 허용가능?","애인의 단점 1가지","나의 주량과 주사에 대해 말해주기","우리가 다툴 때 절대 안 했으면 하는 것"
            ,"네가 싫어하는 행동이나 말들은?","네가 좋아하는 말이나 행동들은?","나만 알고 있는 애인의 특이사항","지금 갖고 있는 나만의 고민은?"
            ,"지금 애인에게 가장 해주고 싶은 스킨십은?","나의 신체 비밀 알려주기","나만의 인생 가치관은?","못 먹거나 싫어하는 음식"
            ,"남들과 다르게 내가 가장 자신 있는 강점은?","취미 혹은 특기는?","현재 하고 있는 나의 일에 대해 자세히 알려주기","애인의 닮은꼴 찾아주기"
            ,"내가 지금 갖고 있는 꿈은?","내 애인에게 이것만큼은\n절대 허락해 줄 수 없다?","내가 가장 잘 부르는 노래는?","내가 애인에게 만들어주고 싶은 음식은?"
            ,"초자연적인 현상을 믿는지\n(ex 유령 귀신)","가장 좋아하는 음료는?","우리 관계에서 가장 중요하게 생각하는 것은?","하루 동안 서로의 몸이 바뀐다면?"
            ,"가장 친한 친구는 누구야?","주말이나 휴일에 시간을 보내는 방법","나의 스트레스 해소법은?","내가 가장 아끼는 물건은 무엇인지?"
            ,"알레르기와 같은 신체적으로\n예민하게 반응하는 것이 있어?","로또 1등이 당첨된다면 무엇을 할 꺼야?","경제활동으로 생긴 돈을\n어떻게 관리하고 사용해?"
            ,"지금까지 해 본 아르바이트는 뭐가 있어?","애인의 매력적인 신체 부분 1가지","닮고 싶은 롤 모델은 누구?","내가 가장 좋아하는 스킨십 1~3등까지 말해주기"
            ,"애인에게 지금 가장 필요한\n선물을 하나 추천해준다면?","한 타임 쉬어가기!\n\n(서로 볼 뽀뽀)","돌발 퀴즈\n\n오늘은 사귄 지 몇 일째 되는 날일까?"
            };

    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        present_card_layout = findViewById(R.id.present_card_layout);
        present_card_textView = findViewById(R.id.present_card_textVIew);
        present_card_button = findViewById(R.id.present_card_button);

        Toolbar persent_Toolbar = findViewById(R.id.present_toolbar);
        setSupportActionBar(persent_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = present_card_layout.getCameraDistance() * (scale + (scale / 3));

        Handler handler = new Handler();

        present_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present_card_layout.setCameraDistance(distance);
                present_card_button.setEnabled(false);
                present_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                present_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_present));
                                int random = (int) (Math.random()*present_questions.length);
                                present_card_textView.setText("Q.  " + present_questions[random]);
                                question_count++;
                                Log.d(TAG, "현재 question_count : "+question_count);

                                present_card_layout.setRotationY(-90);
                                present_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        present_card_button.setEnabled(true);
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

                AlertDialog.Builder builder = new AlertDialog.Builder(PresentQActivity.this);
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
                            mInterstitialAd.show(PresentQActivity.this);
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

}
