package us.manpergut.crashapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by manpergut on 06/03/2018.
 */

public class SecondActivity extends AppCompatActivity {

    private Button c12;
    private Button c22;
    private Button c32;
    private Button crash;
    private TextView ciclos2;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        c12 = findViewById(R.id.c12);
        c22 = findViewById(R.id.c22);
        c32= findViewById(R.id.c32);
        crash = findViewById(R.id.crash);
        ciclos2 = findViewById(R.id.ciclos2);

        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<2000; i++){
                    n++;
                }
                ciclos2.setText("Ciclos: "+n);
            }
        });
        c22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<20000; i++){
                    n++;
                }
                ciclos2.setText("Ciclos: "+n);
            }
        });
        c32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<200000; i++){
                    n++;
                }
                ciclos2.setText("Ciclos: "+n);
            }
        });
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                int a = 3/n;
                ciclos2.setText("Ciclos: "+a);
            }

        });

    }
}
