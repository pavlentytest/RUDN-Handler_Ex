package ru.samsung.itschool.mdev.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn,btn2;
    private TextView tv;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        // Handler позволяет отправлять сообщения другие потоки
        // Looper - запускает некий цикл по обработке сообщений
        // getMainLooper() - метод запускаемый в главном потоке (поток UI)

        h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                tv.setText("Сделано операций: "+msg.what);
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=1;i<=10;i++) {
                            doSlow();
                            h.sendEmptyMessage(i);
                            // так делать не НАДО!
                            // плохо!!!
                            //tv.setText("Сделано операций: "+i);
                            Log.d("RRR","Сделано операций: "+i);
                        }
                    }
                });
                thread.start();


            }
        });
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RRR","Вторая кнопка работает!");
            }
        });
    }

    public void doSlow() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}