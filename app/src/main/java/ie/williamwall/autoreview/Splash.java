package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                    finish();
//                    User me = new User("William", "william@williamwall.ie", 1, "password");
//                    System.out.println("My name is:" + me.getName());
//                    me.setName("OtherName");
//                    System.out.println("My name is:" + me.getName());
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
