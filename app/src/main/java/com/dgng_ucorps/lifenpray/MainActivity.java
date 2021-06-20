package com.dgng_ucorps.lifenpray;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity {

    private MediaPlayer player;
    protected Cursor cursor;
    public static String str;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PERHATIAN...");
        builder.setMessage("Untuk pertama kalinya agar melihat boardlist terlebih dahulu di pojok kanan atas agar tidak force close");
        builder.setPositiveButton("Mengerti", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                playSound(1);
            }
        });
        builder.show();


        dbHelper = new DataHelper(this);
        playSound(5);

        ImageButton button1=(ImageButton) findViewById(R.id.ButtonPlay);
        final EditText itext =(EditText) findViewById(R.id.nama);
        ImageButton lb = (ImageButton) findViewById(R.id.LeaderBoard);
        ImageButton star = findViewById(R.id.star);
        ImageButton help = findViewById(R.id.help);
        ImageButton about = findViewById(R.id.ab);


        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                str=itext.getText().toString();
                playSound(1);

                if (str.equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Pliss, masukkin nama kamu yah",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    cursor=db.rawQuery("SELECT nama FROM biodata WHERE nama = '" + str + "'",null);
                    // CEK DULU ADA NAMA NYA DI DATABASE ATAU GK ?
                    if (cursor.getCount()==0)
                    {

                        db.execSQL("INSERT INTO biodata (nama, benar, salah, poin) VALUES ('"+str+"', 0, 0, 0);");
                        cursor = db.rawQuery("SELECT * FROM biodata",null);

                        Intent aha =new Intent(getApplicationContext(),Scene1.class);
                        aha.putExtra("ok","Ayo bangun shubuh "+str);
                        startActivity(aha);
                        finish();
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"yah, nama kamu sudah ada. masukkin yang lainnya yah :)",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Intent aha =new Intent(getApplicationContext(),BoardList.class);
                startActivity(aha);
                finish();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Intent aha =new Intent(getApplicationContext(),version.class);
                startActivity(aha);
                finish();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Toast.makeText(getApplicationContext(),"Untuk memulai kamu perlu menginputkan nama saja " +
                        "kemudian tekan tombol 'play' untuk memulai permainan. Untuk melihat hasil nilai " +
                        "permaianan kamu cukup tekan tombol pojok kanan atas di tampilan ini :)",Toast.LENGTH_LONG).show();
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Toast.makeText(getApplicationContext(),"Silahkan beri bintang untuk aplikasi kami di playstore yah. :)",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPause() {
        try{
            super.onPause();
            player.pause();
        }catch (Exception e){

        }
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


    @Override
    public void onBackPressed() {
        playSound(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mau Keluar ?");
        builder.setMessage("apakah anda tidak ingin mencobanya lagi");
        builder.setPositiveButton("yah udah keluar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                playSound(1);
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            }
        });
        builder.setNegativeButton("coba lagi deh", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                playSound(1);
            }
        });
        builder.show();
    }



}