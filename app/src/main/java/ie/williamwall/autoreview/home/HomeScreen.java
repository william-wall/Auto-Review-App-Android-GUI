package ie.williamwall.autoreview.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.user.AdministrationUser;
import ie.williamwall.autoreview.weather.Weather;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class HomeScreen extends AppCompatActivity {

    private Button review;
    private Button user;
    private Button weather;
    private Button location;
    private TextView userTime;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        review = (Button)findViewById(R.id.review);
        user = (Button)findViewById(R.id.user);
        location = (Button)findViewById(R.id.location);
        weather = (Button)findViewById(R.id.weather);
        userName = (TextView)findViewById(R.id.userName);
        userTime = (TextView)findViewById(R.id.userTime);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        userTime.setText(currentDateTimeString);
        final String message3 = getIntent().getStringExtra("message_key_user");
        userName.setText(message3);
        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this, AdministrationReview.class);
                startActivity(move);
            }

        });
        user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this,AdministrationUser.class);
                startActivity(move);
            }

        });
        location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this,MapsActivity.class);
                startActivity(move);
            }

        });
        weather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this,Weather.class);
                startActivity(move);
            }

        });
    }
}
