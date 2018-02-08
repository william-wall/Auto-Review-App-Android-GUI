package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    ProgressBar splashProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashProgress = (ProgressBar) findViewById(R.id.progressBarSplash);
        splashProgress.setMax(100);
        splashProgress.setProgress(0);





        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    for(int i =0; i < 100; i++)
                    {
                        splashProgress.setProgress(i);
                        sleep(50);

                    }

                    User me = new User("William", "william@williamwall.ie", 1, "password");
                    System.out.println("My name is:" + me.getName());
                    me.setName("OtherName");
                    System.out.println("My name is:" + me.getName());
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        myThread.start();
   }
}
