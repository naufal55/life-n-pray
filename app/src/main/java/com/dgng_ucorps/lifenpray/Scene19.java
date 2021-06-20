package com.dgng_ucorps.lifenpray;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Scene19 extends AppCompatActivity {

    private MediaPlayer player;
    private ImageButton jwb,cancel;
    private RadioButton pil1,pil2,pil3;
    private ImageView slh,bnr;
    public int benar,salah,poin;
    String tr,fls;
    protected Cursor cursor;
    MainActivity aksesvar = new MainActivity();

    DataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene19);

        pil1 = findViewById(R.id.pil1);
        pil2 = findViewById(R.id.pil2);
        pil3 = findViewById(R.id.pil3);
        jwb = findViewById(R.id.jawab);
        slh = findViewById(R.id.salah);
        bnr = findViewById(R.id.benar);
        dbHelper = new DataHelper(this);

        pilihan();
    }
    public void pilihan()
    {
        jwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pil1.isChecked()){
                    playSound(3);
                    benar++;
                    SQLiteDatabase rb = dbHelper.getReadableDatabase();
                    cursor = rb.rawQuery("SELECT * FROM biodata WHERE nama = '" + aksesvar.str+ "'",null);
                    cursor.moveToFirst();

                    if (cursor.getCount()>0)
                    {
                        cursor.moveToPosition(0);
                        tr = cursor.getString(2).toString();
                        fls = (cursor.getString(3).toString());
                    }


                    salah = Integer.parseInt(fls)+salah;
                    benar = Integer.parseInt(tr)+benar;
                    poin = benar - salah;



                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL("UPDATE biodata set benar ='"+benar+"',salah='"+salah+"',poin='"+poin+"' where nama = '"+aksesvar.str+"' ;");
                    BoardList.ma.RefreshList();

                    ShowToast("Jawaban kamu benar");
                    bnr.setVisibility(View.VISIBLE);
                    jwb.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Thread(){
                        public void run(){

                            bnr.setVisibility(View.INVISIBLE);
                            Intent aha =new Intent(getApplicationContext(),Scene20.class);
                            startActivity(aha);
                            finish();
                        }
                    },1000);

                }else if (pil2.isChecked()){
                    playSound(4);
                    ShowToast("Jawaban anda salah");
                    slh.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Thread(){
                        public void run(){

                            salah++;
                            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
                            slh.startAnimation(startAnimation);
                            slh.setVisibility(View.INVISIBLE);
                        }
                    },500);

                }else if (pil3.isChecked()){
                    playSound(4);
                    ShowToast("Jawaban anda salah");
                    slh.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Thread(){
                        public void run(){

                            salah++;
                            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
                            slh.startAnimation(startAnimation);
                            slh.setVisibility(View.INVISIBLE);
                        }
                    },500);

                }else {
                    ShowToast("sepertinya anda belum memilih apapun");
                }
            }

            private void ShowToast(String s) {
                Toast.makeText(getApplication(),s,Toast.LENGTH_SHORT).show();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                AlertDialog.Builder builder = new AlertDialog.Builder(Scene19.this);
                builder.setTitle("Udahan mainnya ?");
                builder.setMessage("apakah anda tidak ingin melanjutkan lagi");
                builder.setPositiveButton("yah udah keluar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        playSound(1);
                        Intent intent = new Intent(getApplicationContext(), LihatBiodata.class);
                        intent.putExtra("ok",aksesvar.str );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    @Override
    public void onBackPressed() {

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
