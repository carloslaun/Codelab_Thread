package com.example.codelab_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView crono;
    Boolean tiempo = false;
    Boolean visible = true;
    int mili,seg,minutos;
    Button comienzo, detener, pausar;
    Thread cronos;
    Handler h =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        cronos = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if (visible){
                        if(tiempo){
                            try{
                                Thread.sleep(1);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }
                            if(mili == 999){
                                seg++;
                                mili = 0;
                            }
                            mili++;
                            if(seg == 59){
                                minutos++;
                                seg = 0;
                            }
                            h.post(new Runnable() {
                                @Override
                                public void run() {
                                    String m = "", s = "", mi = "";
                                    if (mili < 10) {
                                        mi = "00" + mili;
                                    } else if (mili < 100) {
                                        mi = "0" + mili;
                                    } else {
                                        mi = "" + mili;
                                    }
                                    if (seg < 10){
                                        s = "0"+seg;
                                    }else{
                                        s = ""+seg;
                                    }
                                    m = "0"+minutos;
                                    crono.setText(m+":"+s+":"+mi);

                                }
                            });

                        }

                    }

                }


            }
        });
        cronos.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        Toast.makeText(this, "Cancelling on Pause",Toast.LENGTH_SHORT).show();
        visible = false;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Cancelling on Destroy",Toast.LENGTH_SHORT).show();
    }
    public void onResume(){
        super.onResume();
        visible = true;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.start:
                tiempo = true;
                break;
            case R.id.pause:
                tiempo = false;
                break;

            case R.id.stop:
                crono.setText("00:00:000");
                tiempo = false;
                minutos = 0;
                seg = 0;
                mili = 0;
                break;


        }

    }


    public void init(){

        crono = (TextView) findViewById(R.id.tx_hora);
        comienzo = (Button) findViewById(R.id.start);
        pausar = (Button) findViewById(R.id.pause);
        detener = (Button) findViewById(R.id.stop);
        comienzo.setOnClickListener(this);
        pausar.setOnClickListener(this);
        detener.setOnClickListener(this);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("MILI", mili);
        outState.putInt("SEG", seg);
        outState.putInt("MINUTOS", minutos);
        outState.putBoolean("TIEMPO", tiempo);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mili = savedInstanceState.getInt("MILI");
        seg = savedInstanceState.getInt("SEG");
        minutos = savedInstanceState.getInt("MINUTOS");
        tiempo = savedInstanceState.getBoolean("TIEMPO");
        String m = "", s = "", mi = "";
        if (mili < 10) {
            mi = "00" + mili;
        } else if (mili < 100) {
            mi = "0" + mili;
        } else {
            mi = "" + mili;
        }
        if (seg < 10){
            s = "0"+seg;
        }else{
            s = ""+seg;
        }
        m = "0"+minutos;
        crono.setText(m+":"+s+":"+mi);
        cronos.start();

    }

}