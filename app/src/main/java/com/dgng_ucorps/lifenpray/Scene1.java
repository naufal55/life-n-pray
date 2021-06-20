package com.dgng_ucorps.lifenpray;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

public class Scene1 extends AppCompatActivity {

    private MediaPlayer player;
    private TextView tampiltext;
    ImageButton cancel;
    MainActivity aksesvar = new MainActivity();
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scene1);
        //iklan mobile admob woyyy
        MobileAds.initialize(this,
                "ca-app-pub-5126976828085549~5503628456");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5126976828085549/6980361656");

        BoardList.ma.RefreshList();

        tampiltext = (TextView) findViewById(R.id.kabar);
        final ImageButton Bn = (ImageButton) findViewById(R.id.BtnNext);
        final ImageButton Bn2= (ImageButton) findViewById(R.id.BtnNext2);
        cancel = findViewById(R.id.cancel);

        Bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                String a = getIntent().getStringExtra("ok");
                tampiltext.setText(a);
                Bn.setVisibility(View.INVISIBLE);
                Bn2.setVisibility(View.VISIBLE);
            }
        });
        Bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Intent aha =new Intent(getApplicationContext(),Scene2.class);
                startActivity(aha);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                AlertDialog.Builder builder = new AlertDialog.Builder(Scene1.this);
                builder.setTitle("Udahan mainnya ?");
                builder.setMessage("apakah anda tidak ingin melanjutkan lagi");
                builder.setPositiveButton("yah udah keluar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        playSound(1);
                        Intent intent = new Intent(getApplicationContext(), LihatBiodata.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("ok",aksesvar.str );
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNegativeButton("gak jadi deh", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        playSound(1);
                    }
                });
                builder.show();
            }
        });
    }
    public void onBackPressed() {
        //ini cuman buat disable back button doang!!!!
    }
    private void playSound(int arg){
        try{
            if (player.isPlaying()) {
                player.stop();
                player.release();
            }
        }catch(Exception e){
        }
        if (arg == 1){
            player = MediaPlayer.create(this, R.raw.click);
        }else if (arg==2){
            player = MediaPlayer.create(this, R.raw.nilai);
        }
        else if (arg==3){
            player = MediaPlayer.create(this, R.raw.benar);
        }
        else if (arg==4){
            player = MediaPlayer.create(this, R.raw.salah);
        }
        else if (arg==5){
            player = MediaPlayer.create(this, R.raw.yee);
        }
        player.setLooping(false); // Set looping
        player.start();
    }

}
