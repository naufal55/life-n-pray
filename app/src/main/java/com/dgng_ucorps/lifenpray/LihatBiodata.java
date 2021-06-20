package com.dgng_ucorps.lifenpray;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class LihatBiodata extends AppCompatActivity {

    private MediaPlayer player;
    protected Cursor cursor;
    DataHelper dbHelper;
    TextView nama,benar,salah,poin ;
    ImageView star1,star2,star3,star4,star5;
    private ImageButton ut;
    MainActivity aksesvar = new MainActivity();
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_biodata);
        MobileAds.initialize(this,
                "ca-app-pub-5126976828085549~5503628456");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5126976828085549/6980361656");

        playSound(2);

        nama = findViewById(R.id.nama);
        benar = findViewById(R.id.benar);
        salah = findViewById(R.id.salah);
        poin = findViewById(R.id.poin);

        ut = findViewById(R.id.utama);


        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        star1.startAnimation(startAnimation);
        star2.startAnimation(startAnimation);
        star3.startAnimation(startAnimation);
        star4.startAnimation(startAnimation);
        star5.startAnimation(startAnimation);

        dbHelper = new DataHelper(this);

       //d = getIntent().getStringExtra("ok");
        //tampiltext.setText(a);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("ok") + "'",null);
        cursor.moveToFirst();

        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(1).toString());
            benar.setText(cursor.getString(2).toString());
            salah.setText(cursor.getString(3).toString());
            poin.setText(cursor.getString(4).toString());
        }
        ut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



    }
    @Override
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
