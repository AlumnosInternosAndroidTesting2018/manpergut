package manpergut.us.crashapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Este primer botón realiza un ciclo de 1000 iteraciones.
    private Button c1k;
    //Este segundo, un ciclo de 10.000 it.
    private Button c10k;
    //100.000 it.
    private Button c100k;
    //Realiza el cálculo del fib(1000) de manera recursiva
    private Button crash;
    //Divide entre 0
    private Button crash2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1k = findViewById(R.id.c1K);
        c10k = findViewById(R.id.c10K);
        c100k = findViewById(R.id.c100K);
        crash = findViewById(R.id.crash);
        crash2 = findViewById(R.id.crash2);

        c1k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0;i<1000;i++){
                    n++;
                }
                Toast.makeText(MainActivity.this, "1000 vueltas!", Toast.LENGTH_SHORT).show();
            }
        });
        c10k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i =0; i<10000; i++){
                    n++;
                }
                Toast.makeText(MainActivity.this, "10K vueltas!", Toast.LENGTH_SHORT).show();
            }
        });
        c100k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 0;
                for(int i = 0; i<100000; i++){
                    n++;
                }
                Toast.makeText(MainActivity.this, "100K @-@_", Toast.LENGTH_SHORT);
            }
        });
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n =  1000;
                fib(n);
            }

            private int fib(int n){
                if(n==1 || n==0){
                    return 1;
                }else if(n==2){
                    return 2;
                }else{
                    return fib(n-2)+fib(n-1);
                }
            }

        });

        crash2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 3;
                int m = n/0;
            }

        });


    }
}
