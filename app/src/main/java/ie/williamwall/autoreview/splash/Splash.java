package ie.williamwall.autoreview.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import ie.williamwall.autoreview.firebase.LoginActivityFirebase;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.R;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

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
                    for (int i = 0; i < 100; i++) {
                        splashProgress.setProgress(i);
                        sleep(60);
                    }
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), LoginActivityFirebase.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        myThread.start();
    }
}
