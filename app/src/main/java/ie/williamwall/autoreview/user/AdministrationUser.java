package ie.williamwall.autoreview.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.LoginActivityFirebase;
import ie.williamwall.autoreview.home.HomeScreen;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.weather.Weather;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall


public class AdministrationUser extends AppCompatActivity {
    ListView lv;
    SearchView searchName;
    CustomAdapterUser myAdapter;
    ArrayList<User> users = new ArrayList<User>();
    Spinner listSpinner;
    int id = -1;
    ArrayList<String> justNames = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadData();
        lv = (ListView) findViewById(R.id.listViewMain);

        searchName = (SearchView) findViewById(R.id.searchViewName);
        listSpinner = (Spinner) findViewById(R.id.searchSpinner);
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, justNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listSpinner.setAdapter(adapter);
        myAdapter = new CustomAdapterUser(this, R.layout.item_layout_administration_user, users);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String nameInstance = "";
                String emailInstance = "";
                String phoneInstance = "";
                String passwordInstance = "";
                id = position;
                nameInstance = users.get(position).getName();
                emailInstance = users.get(position).getEmail();
                phoneInstance = users.get(position).getPhone();
                passwordInstance = users.get(position).getPassword();
                crudWindow(position);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                users.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(AdministrationUser.this, "Deleted User", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                addWindow();
            }
        });
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_user);
        item.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, LoginActivityFirebase.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_weather) {
            Toast.makeText(this, "Weather Report", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, Weather.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_location) {
            Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, MapsActivity.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_review) {
            Toast.makeText(this, "Administration Review", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, AdministrationReview.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_user) {
            Toast.makeText(this, "Administration User", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.home_icon) {
            Toast.makeText(this, "Home Administration", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, HomeScreen.class);
            startActivity(Intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        SharedPreferences sharedPreferencesUser = getSharedPreferences("shared preferences_user", MODE_PRIVATE);
        SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
        Gson gsonUser = new Gson();
        String jsonUser = gsonUser.toJson(users);
        editorUser.putString("task list user", jsonUser);
        editorUser.apply();
    }

    private void loadData() {
        users = new ArrayList<User>();
        SharedPreferences sharedPreferencesUser = getSharedPreferences("shared preferences_user", MODE_PRIVATE);
        Gson gsonUser = new Gson();
        String jsonUser = sharedPreferencesUser.getString("task list user", null);
        Type typeUser = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gsonUser.fromJson(jsonUser, typeUser);
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    public int crudWindow(int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationUser.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_user, null);
        final EditText mName = (EditText) mView.findViewById(R.id.newN);
        final EditText mEmail = (EditText) mView.findViewById(R.id.newE);
        final EditText mPhone = (EditText) mView.findViewById(R.id.newPh);
        final EditText mPassword = (EditText) mView.findViewById(R.id.newP);
        id = position;
        mName.setText(users.get(position).getName());
        mEmail.setText(users.get(position).getEmail());
        mPhone.setText(users.get(position).getPhone());
        mPassword.setText(users.get(position).getPassword());
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditName = mName.getText().toString();
                String stringEditEmail = mEmail.getText().toString();
                String stringEditPhone = mPhone.getText().toString();
                String stringEditPassword = mPassword.getText().toString();
                if (!validate(stringEditName, mName, stringEditEmail, mEmail, stringEditPhone, mPhone, stringEditPassword, mPassword)) {
                    Toast.makeText(AdministrationUser.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    User tempEdit = new User(R.mipmap.user, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                    users.set(id, tempEdit);
                    id = -1;
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationUser.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationUser.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String stringEditName = mName.getText().toString();
                        String stringEditEmail = mEmail.getText().toString();
                        String stringEditPhone = mPhone.getText().toString();
                        String stringEditPassword = mPassword.getText().toString();
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        User tempEdit = new User(R.mipmap.user, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                        users.set(id, tempEdit);
                        id = -1;
                        users.remove(tempEdit);
                        myAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(AdministrationUser.this, "Deleted User", Toast.LENGTH_LONG).show();
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

    public void addWindow() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationUser.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_user, null);
        final EditText mName = (EditText) mView.findViewById(R.id.newN);
        final EditText mEmail = (EditText) mView.findViewById(R.id.newE);
        final EditText mPhone = (EditText) mView.findViewById(R.id.newPh);
        final EditText mPassword = (EditText) mView.findViewById(R.id.newP);
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
                String stringEditName = mName.getText().toString();
                String stringEditEmail = mEmail.getText().toString();
                String stringEditPhone = mPhone.getText().toString();
                String stringEditPassword = mPassword.getText().toString();
                if (!validate(stringEditName, mName, stringEditEmail, mEmail, stringEditPhone, mPhone, stringEditPassword, mPassword)) {
                    Toast.makeText(AdministrationUser.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    User temp = new User(R.mipmap.user, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                    users.add(temp);
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationUser.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    public boolean validate(String stringEditName, EditText mName, String stringEditEmail, EditText mEmail, String stringEditPhone, EditText mPhone, String stringEditPassword, EditText mPassword) {
        boolean valid = true;
        if (stringEditName.isEmpty() || stringEditEmail.length() > 32) {
            mName.setError("Please Enter Valid Name");
            valid = false;
        }
        if (stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches()) {
            mEmail.setError("Email must be valid pattern");
            valid = false;
        }
        if (stringEditPhone.isEmpty() || stringEditPhone.length() < 6) {
            mPhone.setError("Phone must be at least 6 char");
            valid = false;
        }
        if (stringEditPassword.isEmpty() || stringEditPassword.length() < 8) {
            mPassword.setError("Password must be at least 8 char");
            valid = false;
        }
        if (stringEditName.isEmpty() || stringEditEmail.length() > 32 || stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches() || stringEditPhone.isEmpty() || stringEditPhone.length() < 6 || stringEditPassword.isEmpty() || stringEditPassword.length() < 8) {
            valid = false;
        }
        return valid;
    }
}