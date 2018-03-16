package us.manpergut.crashapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button c1;
    private Button c2;
    private Button c3;
    private Button nView;
    private TextView ciclos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        nView = findViewById(R.id.nview);
        ciclos = findViewById(R.id.ciclos);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<1000; i++){
                    n++;
                }
                ciclos.setText("Ciclos: "+n);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<10000; i++){
                    n++;
                }
                ciclos.setText("Ciclos: "+n);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<100000; i++){
                    n++;
                }
                ciclos.setText("Ciclos: "+n);
            }
        });
        nView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nova = new Intent(MainActivity.this, SecondActivity.class);
                MainActivity.this.startActivity(nova);
            }
        });
    }
}
