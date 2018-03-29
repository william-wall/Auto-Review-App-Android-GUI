package ie.williamwall.autoreview.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseAdministrator.MainActivityFirebase;
import ie.williamwall.autoreview.firebaseReview.ViewData;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.firebaseReview.CustomImage;
import ie.williamwall.autoreview.user.AdministrationUser;
import ie.williamwall.autoreview.weather.Weather;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

public class HomeScreen extends AppCompatActivity {

    private Button review;
    private Button user;
    private Button weather;
    private Button location;
    private TextView userTime;
    private TextView userName;
    private TextView choose;
    private ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        review = (Button) findViewById(R.id.review);
        user = (Button) findViewById(R.id.user);
        location = (Button) findViewById(R.id.location);
        weather = (Button) findViewById(R.id.weather);
        userName = (TextView) findViewById(R.id.userName);
        userTime = (TextView) findViewById(R.id.userTime);
        choose = (TextView) findViewById(R.id.choose);
        appLogo = (ImageView) findViewById(R.id.imageViewHome);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        userTime.setText(currentDateTimeString);
        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(userId);

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(HomeScreen.this, AdministrationReview.class);
                startActivity(move);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(HomeScreen.this, AdministrationUser.class);
                startActivity(move);
            }

        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(HomeScreen.this, MapsActivity.class);
                startActivity(move);
            }

        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(HomeScreen.this, Weather.class);
                startActivity(move);
            }
        });
        appLogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent move = new Intent(HomeScreen.this, ViewData.class);
                startActivity(move);
            }

        });
        choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent move = new Intent(HomeScreen.this, FirebaseUser.class);
                startActivity(move);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(HomeScreen.this, MainActivityFirebase.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_weather) {
            Toast.makeText(this, "Weather Report", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(HomeScreen.this, Weather.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_location) {
            Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(HomeScreen.this, MapsActivity.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_review) {
            Toast.makeText(this, "Administration Review", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(HomeScreen.this, AdministrationReview.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_user) {
            Toast.makeText(this, "Administration ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(HomeScreen.this, AdministrationUser.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.home_icon) {
            Toast.makeText(this, "You are already home!", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.info_icon) {
            Toast.makeText(this, "Only used in ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase and Review Administration!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        userName.setText(user.getEmail());

    }
}
