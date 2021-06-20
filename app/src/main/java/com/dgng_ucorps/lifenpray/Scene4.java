package com.dgng_ucorps.lifenpray;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class Scene4 extends AppCompatActivity {

    private MediaPlayer player;
    MainActivity aksesvar = new MainActivity();
    private ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scene4);


        ImageButton Bn = (ImageButton) findViewById(R.id.next);
        cancel = findViewById(R.id.cancel);
        Bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                Intent aha =new Intent(getApplicationContext(),Scene5.class);
                aha.putExtra("ok",aksesvar.str );
                startActivity(aha);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(1);
                AlertDialog.Builder builder = new AlertDialog.Builder(Scene4.this);
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
