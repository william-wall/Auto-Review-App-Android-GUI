package ie.williamwall.autoreview.review;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseAdministrator.MainActivityFirebase;
import ie.williamwall.autoreview.home.HomeScreen;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.user.AdministrationUser;
import ie.williamwall.autoreview.weather.Weather;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

public class AdministrationReview extends AppCompatActivity {

    int id = -1;
    ListView mainList;
    private TextView userLoginName;
    ArrayList<Review> someReviews;
    CustomAdapterReview myAdapter;
    ArrayAdapter<Review> adapter;
    TextView userName;

    private void init() {
        mainList = (ListView) findViewById(R.id.list_cars);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_review);
        userName = (TextView) findViewById(R.id.userName);
        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(userId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWindow();
            }
        });
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        init();
        loadData();
        myAdapter = new CustomAdapterReview(this, R.layout.item_layout_administration_review, someReviews);
        mainList.setAdapter(myAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String titleInstance = "";
                String descInstance = "";
                id = position;
                titleInstance = someReviews.get(position).getReviewTitle();
                descInstance = someReviews.get(position).getReviewDesc();
                crudWindow(position);
            }
        });
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                someReviews.remove(position);
                myAdapter.notifyDataSetChanged();
                saveData();
                Toast.makeText(AdministrationReview.this, "Deleted Review", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_review);
        item.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, MainActivityFirebase.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_weather) {
            Toast.makeText(this, "WeatherFrag Report", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, Weather.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_location) {
            Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, MapsActivity.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_review) {
            return true;
        }
        if (id == R.id.action_user) {
            Toast.makeText(this, "Administration ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, AdministrationUser.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.home_icon) {
            Toast.makeText(this, "Home Administration", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, HomeScreen.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.info_icon) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
            builder.setTitle("Confirm");
            builder.setMessage("Do you want to populate list with random records?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Review temp = new Review(R.mipmap.caricon, "AUDI A6 S-Line 3.0", "Massive amount of power, comfort is hard to beat. Real stylish motor. Worth the money, I have never heard a bad review about one of these cars!", currentDateTimeString);
                    Review temp2 = new Review(R.mipmap.caricon, "Toyota Avensis 2.0 D", "Reliable car, never gives trouble and new buy is cheap!\n" +
                            "not that costly and vrt is moderate! The tax for such a car is quite cheap", currentDateTimeString);
                    Review temp3 = new Review(R.mipmap.caricon, "OPEL Vectra 1.6 V6 Club", "Slow and very bad fuel consumption, would not recommend! Petrol cars like these are a thing of the past!", currentDateTimeString);
                    Review temp4 = new Review(R.mipmap.caricon, "VW Golf 1.9 TDI 2003", "Very good on fuel, not too bad on power either. Reliable car although getting quite old now. It seems that this car needs a lot of services more regular than other cars!", currentDateTimeString);
                    someReviews.add(temp);
                    someReviews.add(temp2);
                    someReviews.add(temp3);
                    someReviews.add(temp4);
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    dialog.dismiss();
                    Toast.makeText(AdministrationReview.this, "Random Data Added!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(someReviews);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        someReviews = new ArrayList<Review>();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Review>>() {
        }.getType();
        someReviews = gson.fromJson(json, type);
        if (someReviews == null) {
            someReviews = new ArrayList<>();
        }
    }

    public boolean validate(String stringEditTitle, EditText mTitle, String stringEditDesc, EditText mDesc) {
        boolean valid = true;
        if (stringEditTitle.isEmpty() || stringEditTitle.length() < 5) {
            mTitle.setError("Title must be at least 5 characters!");
            valid = false;
        }
        if (stringEditDesc.isEmpty() || stringEditDesc.length() < 20) {
            mDesc.setError("Review must be at least 20 characters!");
            valid = false;
        }
        if (stringEditTitle.isEmpty() || stringEditTitle.length() < 5 || stringEditDesc.isEmpty() || stringEditDesc.length() < 20) {
            valid = false;
        }
        return valid;
    }

    public void addWindow() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationReview.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_review, null);
        final EditText mTitle = (EditText) mView.findViewById(R.id.newTitle);
        final EditText mDesc = (EditText) mView.findViewById(R.id.newDesc);
        mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringEditTitle = mTitle.getText().toString();
                String stringEditDesc = mDesc.getText().toString();
                if (!validate(stringEditTitle, mTitle, stringEditDesc, mDesc)) {
                    Toast.makeText(AdministrationReview.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Review temp = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                    someReviews.add(temp);
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationReview.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    public int crudWindow(int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationReview.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_review, null);
        final EditText mTitle = (EditText) mView.findViewById(R.id.newTitle);
        final EditText mDesc = (EditText) mView.findViewById(R.id.newDesc);
        id = position;
        mTitle.setText(someReviews.get(position).getReviewTitle());
        mDesc.setText(someReviews.get(position).getReviewDesc());
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditTitle = mTitle.getText().toString();
                String stringEditDesc = mDesc.getText().toString();
                if (!validate(stringEditTitle, mTitle, stringEditDesc, mDesc)) {
                    Toast.makeText(AdministrationReview.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                    someReviews.set(id, tempEdit);
                    id = -1;
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationReview.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String stringEditTitle = mTitle.getText().toString();
                        String stringEditDesc = mDesc.getText().toString();
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                        someReviews.set(id, tempEdit);
                        id = -1;
                        someReviews.remove(tempEdit);
                        myAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(AdministrationReview.this, "Deleted ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        mBuilder.setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addWindow();
            }
        });
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        return position;
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        userName.setText("Administrator ID: " + user.getEmail());

    }


}

