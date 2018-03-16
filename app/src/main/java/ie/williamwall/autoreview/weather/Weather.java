package ie.williamwall.autoreview.weather;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.MainActivityFirebase;
import ie.williamwall.autoreview.home.HomeScreen;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.user.AdministrationUser;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI


public class Weather extends AppCompatActivity {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    Typeface weatherFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        Function.placeIdTask asyncTask = new Function.placeIdTask(new Function.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
                cityField.setText(weather_city);
                updatedField.setText(weather_updatedOn);
                detailsField.setText(weather_description);
                currentTemperatureField.setText(weather_temperature);
                humidity_field.setText("Humidity: " + weather_humidity);
                pressure_field.setText("Pressure: " + weather_pressure);
                weatherIcon.setText(Html.fromHtml(weather_iconText));
            }
        });
        asyncTask.execute("52.245036", "-7.136621");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_weather);
        item.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(Weather.this, MainActivityFirebase.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_weather) {
            return true;
        }
        if (id == R.id.action_location) {
            Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(Weather.this, MapsActivity.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_review) {
            Toast.makeText(this, "Administration Review", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(Weather.this, AdministrationReview.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_user) {
            Toast.makeText(this, "Administration User", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(Weather.this, AdministrationUser.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.home_icon) {
            Toast.makeText(this, "Home Administration", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(Weather.this, HomeScreen.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.info_icon) {
            Toast.makeText(this, "Only used in User and Review Administration!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
