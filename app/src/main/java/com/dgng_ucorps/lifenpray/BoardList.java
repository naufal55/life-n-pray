package com.dgng_ucorps.lifenpray;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class BoardList extends AppCompatActivity {

    private MediaPlayer player;
    String[] daftar;
    ListView ListView01;
    protected Cursor cursor;

    DataHelper dbcenter;

    @SuppressLint("StaticFieldLeak")
    public static BoardList ma;

    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList();
        back = findViewById(R.id.back);




        back.setOnClickListener(new View.OnClickListener() {
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

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata",null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1);
        }
        ListView01 = (ListView)findViewById(R.id.ListNama);


        ListView01.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);

        ListView01.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                playSound(1);
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();
                final CharSequence[] dialogitem = {"Lihat Data diri", "Hapus Data diri"};
                AlertDialog.Builder builder = new AlertDialog.Builder(BoardList.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                playSound(1);
                                Intent i = new Intent(getApplicationContext(), LihatBiodata.class);
                                i.putExtra("ok", selection);
                                startActivity(i);
                                finish();
                                break;
                            case 1 :
                                playSound(1);
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from biodata where nama = '"+selection+"'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }});
        ((ArrayAdapter)ListView01.getAdapter()).notifyDataSetInvalidated();
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



