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

public class PastQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout past_card_layout;
    TextView past_card_textView;
    Button past_card_button;



    String[] past_questions = {"연애 경험 몇 번 해봤어?","가장 긴 연애는 언제야?","과거에 내가 가장 바꾸고 싶은 순간은?","10년 전 나로 돌아간다면?",
            "5년 전 나로 돌아간다면?","1년 전 나로 돌아간다면?","과거에 내가 가장 창피했던 순간은?","애인과 사귀기 직전으로 돌아간다면?",
            "연애하면서 가장 서운했던 점 2가지","연애하면서 가장 행복했던 점 2가지","고백하다가 실패한 적은 있는지?","고백받아본 적은 있는지?","살면서 가장 인상 깊었던 순간은?",
            "학창 시절 나의 성적은?","친구랑 주먹다짐하면서 싸워본 적 있는지?","과거로 1번 돌아갈 수 있다면?","인생 터닝포인트는 언제인가?","가장 기억에 남는 여행은?",
            "연애 중 가장 기억에 남는 순간은?","과거에 묻어놓은 나의 비밀은?","가장 좋았던 애인의 선물은?","어렸을 적 나의 꿈이 무엇이었는지 말해주기",
            "다시 고백할 수 있다면\n어떻게 고백할 것인지?","내가 애인에게 감동받았던 순간은?","썸탈 때 나에게 반했던 점은?","데이트 장소 중 가장 흥미로웠던 장소는?",
            "데이트 장소 중 가장 별로였던 장소는?","돌발 퀴즈\n\n애인이 가장 좋아하는 음식은?","돌발 퀴즈\n\n애인이 가장 싫어하는 음식은?","돌발 퀴즈\n\n우리가 처음 만난 장소는?",
            "돌발 퀴즈\n\n애인의 생일은?","연애 초반 애인 vs 현재 애인","나의 첫 연애는 언제인지?","썸 타기 전 마음에 들었던 나의 모습은?","썸 타는 중 썸이 끝날 뻔했던 적은?",
            "한 타임 쉬어가기!\n\n(서로 안아주기)","나의 첫인상은?","내가 가장 귀여웠던 적은?","내가 가장 미웠던 적은?","내가 애인을 꼬시기 위해\n가장 노력했던 점?",
            "내가 애인에게 했던 사소한 거짓말은?","이 사람에게 고백하기로 다짐한 이유는?","이 기회를 빌려 사과하고 싶은 일은?","내가 사귀기 전 애인에게\n보냈던 시그널은?",
            "지금까지 사귀면서 당황했던 적은?","내가 사귀자 했을 때\n당시 기분은?","우리가 서로 알기 전\n나의 모습은 어땠을 것 같아?","내가 꿈속에서 나온 적 있어?",
            "나의 학창 시절은 어땠을 것\n같을지 애인에게 물어보기","나의 학창 시절 말해주기"};


    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        past_card_layout = findViewById(R.id.past_card_layout);
        past_card_textView = findViewById(R.id.past_card_textVIew);
        past_card_button = findViewById(R.id.past_card_button);
        Toolbar past_Toolbar = findViewById(R.id.past_toolbar);
        setSupportActionBar(past_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = past_card_layout.getCameraDistance() * (scale + (scale / 3));


        Handler handler = new Handler();

        past_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                past_card_layout.setCameraDistance(distance);
                past_card_button.setEnabled(false);
                past_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                int random = (int) (Math.random()*past_questions.length);
                                past_card_textView.setText("Q.  " + past_questions[random]);

                                Log.d(TAG, "카드 회전 정상 작동");

                                past_card_layout.setRotationY(-90);
                                past_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();
                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        past_card_button.setEnabled(true);
                    }
                }, 500);
            }
        });

/*
-- 이 코드는 question_count가 10일 때 카드가 90도 회전모션이 나온 후 광고가 뜨는 코드임
회전모션 후 광고가 나오므로 불필요한 90도의 회전모션이 존재하며 광고가 끝난후 90도 회전모션이 있었음으로 새로운 카드 질문이
나올 것이라고 생각하지만 그렇지도 않음

그래서 question_count 와 10 숫자 비교후 카드를 rotation 하는 코드를 새로 만듦

past_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                past_card_layout.setCameraDistance(distance);
                past_card_button.setEnabled(false);
                past_card_layout.animate().withLayer()
                        .rotationY(90)
                        .setDuration(150)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {

                                if(question_count < 10){
                                    past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                    int random = (int) (Math.random()*past_questions.length);
                                    past_card_textView.setText("Q.  " + past_questions[random]);
                                    question_count++;
                                    Log.d(TAG, "현재 question_count : "+question_count);
                                }else if(question_count == 10){
                                    if(mInterstitialAd != null){
                                        mInterstitialAd.show(PastQActivity.this);
                                    }else{
                                        past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                        int random = (int) (Math.random()*past_questions.length);
                                        past_card_textView.setText("Q.  " + past_questions[random]);
                                        question_count = 1;
                                        Log.d(TAG, "현재 question_count : "+question_count);
                                    }
                                }else if(question_count > 10){
                                    question_count = 1;
                                    past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                    int random = (int) (Math.random()*past_questions.length);
                                    past_card_textView.setText("Q.  " + past_questions[random]);
                                    question_count++;
                                }
                                past_card_layout.setRotationY(-90);
                                past_card_layout.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250)
                                        .start();


                            }
                        }).start();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        past_card_button.setEnabled(true);
                    }
                },500);

            }
        });
 */


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

                AlertDialog.Builder builder = new AlertDialog.Builder(PastQActivity.this);
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
                            mInterstitialAd.show(PastQActivity.this);
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

/*
이거는 뒤로가기 버튼이 하단에 있는 버전

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

public class PastQActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    LinearLayout past_card_layout;
    TextView past_card_textView;
    Button past_card_button;



    String[] past_questions = {"연애 경험 몇 번 해봤어?","가장 긴 연애는 언제야?","과거에 내가 가장 바꾸고 싶은 순간은?","10년 전 나로 돌아간다면?",
            "5년 전 나로 돌아간다면?","1년 전 나로 돌아간다면?","과거에 내가 가장 창피했던 순간은?","애인과 사귀기 직전으로 돌아간다면?",
            "연애하면서 가장 서운했던 점 2가지","연애하면서 가장 행복했던 점 2가지","고백하다가 실패한 적은 있는지?","고백받아본 적은 있는지?","살면서 가장 인상 깊었던 순간은?",
            "학창 시절 나의 성적은?","친구랑 주먹다짐하면서 싸워본 적 있는지?","과거로 1번 돌아갈 수 있다면?","인생 터닝포인트는 언제인가?","가장 기억에 남는 여행은?",
            "연애 중 가장 기억에 남는 순간은?","과거에 묻어놓은 나의 비밀은?","가장 좋았던 애인의 선물은?","어렸을 적 나의 꿈이 무엇이었는지 말해주기",
            "다시 고백할 수 있다면\n어떻게 고백할 것인지?","내가 애인에게 감동받았던 순간은?","썸탈 때 나에게 반했던 점은?","데이트 장소 중 가장 흥미로웠던 장소는?",
            "데이트 장소 중 가장 별로였던 장소는?","돌발 퀴즈\n\n애인이 가장 좋아하는 음식은?","돌발 퀴즈\n\n애인이 가장 싫어하는 음식은?","돌발 퀴즈\n\n우리가 처음 만난 장소는?",
            "돌발 퀴즈\n\n애인의 생일은?","연애 초반 애인 vs 현재 애인","나의 첫 연애는 언제인지?","썸 타기 전 마음에 들었던 나의 모습은?","썸 타는 중 썸이 끝날 뻔했던 적은?",
            "한 타임 쉬어가기!\n\n(서로 안아주기)","나의 첫인상은?","내가 가장 귀여웠던 적은?","내가 가장 미웠던 적은?","내가 애인을 꼬시기 위해\n가장 노력했던 점?",
            "내가 애인에게 했던 사소한 거짓말은?","이 사람에게 고백하기로 다짐한 이유는?","이 기회를 빌려 사과하고 싶은 일은?","내가 사귀기 전 애인에게\n보냈던 시그널은?",
            "지금까지 사귀면서 당황했던 적은?","내가 사귀자 했을 때\n당시 기분은?","우리가 서로 알기 전\n나의 모습은 어땠을 것 같아?","내가 꿈속에서 나온 적 있어?",
            "나의 학창 시절은 어땠을 것\n같을지 애인에게 물어보기","나의 학창 시절 말해주기"};


    int check = 0; // 뒷면
    int question_count = 0; //현재 열어본 질문 개수

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //전체화면으로 작업표시줄 없애기
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //가로화면 전환 방지
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //다크모드 방지

        past_card_layout = findViewById(R.id.past_card_layout);
        past_card_textView = findViewById(R.id.past_card_textVIew);
        past_card_button = findViewById(R.id.past_card_button);
        Toolbar past_Toolbar = findViewById(R.id.past_toolbar);
        setSupportActionBar(past_Toolbar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        final float distance = past_card_layout.getCameraDistance() * (scale + (scale / 3));


        Handler handler = new Handler();

        past_card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question_count<10) {
                    past_card_layout.setCameraDistance(distance);
                    past_card_button.setEnabled(false);
                    past_card_layout.animate().withLayer()
                            .rotationY(90)
                            .setDuration(150)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                    int random = (int) (Math.random()*past_questions.length);
                                    past_card_textView.setText("Q.  " + past_questions[random]);
                                    question_count++;
                                    Log.d(TAG, "현재 question_count : "+question_count);

                                    past_card_layout.setRotationY(-90);
                                    past_card_layout.animate().withLayer()
                                            .rotationY(0)
                                            .setDuration(250)
                                            .start();
                                }
                            }).start();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            past_card_button.setEnabled(true);
                        }
                    }, 500);
                }else if(question_count == 10){
                    if(mInterstitialAd != null){
                        mInterstitialAd.show(PastQActivity.this);
                        question_count = 1;
                    }else{
                        past_card_layout.setCameraDistance(distance);
                        past_card_button.setEnabled(false);
                        past_card_layout.animate().withLayer()
                                .rotationY(90)
                                .setDuration(150)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                        int random = (int) (Math.random()*past_questions.length);
                                        past_card_textView.setText("Q.  " + past_questions[random]);
                                        question_count = 1;
                                        Log.d(TAG, "현재 question_count : "+question_count);

                                        past_card_layout.setRotationY(-90);
                                        past_card_layout.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250)
                                                .start();
                                    }
                                }).start();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                past_card_button.setEnabled(true);
                            }
                        }, 500);
                    }
                }else if(question_count > 10){
                    past_card_layout.setCameraDistance(distance);
                    past_card_button.setEnabled(false);
                    past_card_layout.animate().withLayer()
                            .rotationY(90)
                            .setDuration(150)
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    question_count = 1;
                                    past_card_layout.setBackground(getResources().getDrawable(R.drawable.card_front_past));
                                    int random = (int) (Math.random()*past_questions.length);
                                    past_card_textView.setText("Q.  " + past_questions[random]);
                                    question_count++;

                                    past_card_layout.setRotationY(-90);
                                    past_card_layout.animate().withLayer()
                                            .rotationY(0)
                                            .setDuration(250)
                                            .start();
                                }
                            }).start();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            past_card_button.setEnabled(true);
                        }
                    }, 500);
                }
            }
        });

        past_card_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PastQActivity.this);
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
                            mInterstitialAd.show(PastQActivity.this);
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
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        //ca-app-pub-3940256099942544/1033173712 공용 앱 전면광고 ID
        //ca-app-pub-7754099565688705/3711257480 나의 앱 전면광고 ID
        InterstitialAd.load(this, "ca-app-pub-7754099565688705/3711257480", adRequest, new InterstitialAdLoadCallback() {
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
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}

 */