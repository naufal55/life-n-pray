package com.dgng_ucorps.lifenpray;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.View;
import android.widget.ImageView;
import android.view.animation.Animation;   //import library animasi
import android.view.animation.AnimationUtils;  //import library animasi
import com.google.android.gms.ads.MobileAds;

import static com.dgng_ucorps.lifenpray.MainActivity.str;


public class SplashScreen extends Activity{
    private ImageView lg;
    DataHelper dbHelper = new DataHelper(this);
    protected Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-5126976828085549~5503628456");

        //memanggil resource animasi di folder res anim fade in
        //Animation EndAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);


        ImageView Logo = (ImageView) findViewById(R.id.logo);
        //menjalankanya pada imageview
        Logo.startAnimation(startAnimation);
        //Logo.startAnimation(EndAnimation);


        /*menjalankan splash screen dan menu menggunakan delayed thread*/
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent mainMenu= new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(mainMenu);
                SplashScreen.  this.finish();
                overridePendingTransition(R.anim.fadein ,R.anim.fadeout);
            }
        }, 6000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_main, menu);
        return true;
    }
}
