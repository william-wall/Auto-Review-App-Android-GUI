package ie.williamwall.autoreview.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import ie.williamwall.autoreview.facebook.Facebook;
import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseAdministrator.LoginActivityFirebase;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

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
