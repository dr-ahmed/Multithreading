package com.dev.multithreading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        textView = findViewById(R.id.textview);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Log.e("TAG", "Thread : " + Thread.currentThread().getName());
            //prepareRunnable();

            //runnable.run();

            prepareCounterRunnable();
            Thread thread = new Thread(runnable);
            thread.start();
            //thread.run();
        }
    }

    private void prepareRunnable() {
        final Handler handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Thread : " + Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.getStackTraceString(e);
                }
                //Log.e("TAG", "Vous avez commencé ...");
                //textView.setText("Vous avez cliqué sur Commencer !");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "Vous avez commencé ...");
                        textView.setText("Vous avez cliqué sur Commencer !");
                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "Après 3 secondes ...");
                        textView.setText("Message après 3 secondes !");
                    }
                }, 3000);
            }
        };
    }

    private void prepareCounterRunnable() {
        final Handler handler = new Handler();

        runnable = new Runnable() {
            int counter = 5;

            @Override
            public void run() {
                while (counter != 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", "Veuillez patienter " + counter + " secondes...");
                            textView.setText("Veuillez patienter " + counter + " secondes...");
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.getStackTraceString(e);
                    }

                    counter--;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG", "Merci. Bienvenue !");
                        textView.setText("Merci. Bienvenue !");
                    }
                });
            }
        };
    }
}
