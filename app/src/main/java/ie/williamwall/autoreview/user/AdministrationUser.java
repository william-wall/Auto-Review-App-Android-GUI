package ie.williamwall.autoreview.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.LoginActivityFirebase;
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
//    Boolean valid;

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
                window(position);
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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationUser.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_add_screen, null);
//                 final TextView mTitle = (TextView) mView.findViewById(R.id.titleView);
//                final TextView mSub = (TextView) mView.findViewById(R.id.subView);
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
                        if (stringEditName.isEmpty() || stringEditEmail.length() > 32) {
                            mName.setError("Please Enter Valid Name");
                        }
                        if (stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches()) {
                            mEmail.setError("Email must be valid pattern");

                        }
                        if (stringEditPhone.isEmpty() || stringEditPhone.length() < 6) {
                            mPhone.setError("Phone must be at least 6 char");

                        }
                        if (stringEditPassword.isEmpty() || stringEditPassword.length() < 8) {
                            mPassword.setError("Password must be at least 8 char");

                        }
                        if(stringEditName.isEmpty() || stringEditEmail.length() > 32||stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches()||stringEditPhone.isEmpty() || stringEditPhone.length() < 6||stringEditPassword.isEmpty() || stringEditPassword.length() < 8)
                        {
                            Toast.makeText(AdministrationUser.this, "You have incorrect field data!", Toast.LENGTH_LONG).show();

                        }
                        else {
                            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                            User temp = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                            users.add(temp);
                            myAdapter.notifyDataSetChanged();
                            saveData();
                            Toast.makeText(AdministrationUser.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
//                        if (stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches()) {
//                            email.setError("Please Enter Valid Email");
//                        }
//                        if (stringEditPhone.isEmpty()) {
//                            phone.setError("Please Enter Valid Phone");
//                        }
//                        if (stringEditPassword.isEmpty()) {
//                            password.setError("Please Enter Valid Password");
//                        }
//                            else{
//
//                            }
                    }
                });


//                View mView = getLayoutInflater().inflate(R.layout.activity_add_screen,null);
//                final EditText newName = (EditText)mView.findViewById(R.id.newN);
//                final EditText newEmail = (EditText)mView.findViewById(R.id.newE);
//                final EditText newPhone = (EditText)mView.findViewById(R.id.newPh);
//                final EditText newPassword = (EditText)mView.findViewById(R.id.newP);
//                 AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationUser.this);
////                alert.setTitle("Administration User");
////                final LinearLayout layout = new LinearLayout(AdministrationUser.this);
////                layout.setOrientation(LinearLayout.VERTICAL);
////                final EditText name = new EditText(AdministrationUser.this);
////                name.setHint("Name, E.G. John Doe");
////                layout.addView(name);
////                final EditText email = new EditText(AdministrationUser.this);
////                email.setHint("Email, E.G. johndoe@gmail.com");
////                layout.addView(email);
////                final EditText phone = new EditText(AdministrationUser.this);
////                phone.setHint("Phone, E.G. 0871234567");
////                layout.addView(phone);
////                final EditText password = new EditText(AdministrationUser.this);
////                password.setHint("Password, E.G. somepassword");
////                layout.addView(password);
//                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
////                        String stringEditName = newName.getText().toString();
////                        String stringEditEmail = newEmail.getText().toString();
////                        String stringEditPhone = newPhone.getText().toString();
////                        String stringEditPassword = newPassword.getText().toString();
////                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
////
////
////                            User temp = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
////                            users.add(temp);
////                            myAdapter.notifyDataSetChanged();
////                            saveData();
////                            Toast.makeText(AdministrationUser.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
//
//                    }
//                });
//                alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
////                        Toast.makeText(AdministrationUser.this, "Cancel", Toast.LENGTH_LONG).show();
////                        AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationUser.this);
////                        builder.setTitle("Confirm");
////                        builder.setMessage("Are you sure?");
////                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();
////                            }
////                        });
////                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                            }
////                        });
////                        builder.show();
//                    }
//                });
//                alert.setView(mView);
//                AlertDialog dialog = alert.create();
//                alert.show();
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
////                        if (!name.getText().toString().isEmpty()) {
////                            name.setError("Please Enter Valid Name");
////                            dialog.dismiss();
////                        }
////                    else {
////                            Toast.makeText(AdministrationUser.this, "Fucked up", Toast.LENGTH_SHORT).show();
////                        }
//                    }
//                });
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
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        SharedPreferences sharedPreferencesUser = getSharedPreferences("shared preferences3", MODE_PRIVATE);
        SharedPreferences.Editor editorUser = sharedPreferencesUser.edit();
        Gson gsonUser = new Gson();
        String jsonUser = gsonUser.toJson(users);
        editorUser.putString("task list3", jsonUser);
        editorUser.apply();
    }

    private void loadData() {
        users = new ArrayList<User>();
        SharedPreferences sharedPreferencesUser = getSharedPreferences("shared preferences3", MODE_PRIVATE);
        Gson gsonUser = new Gson();
        String jsonUser = sharedPreferencesUser.getString("task list3", null);
        Type typeUser = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gsonUser.fromJson(jsonUser, typeUser);
        if (users == null) {
            users = new ArrayList<>();
        }
    }

    public int window(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationUser.this);
        alert.setTitle("Administer User");
        LinearLayout layout = new LinearLayout(AdministrationUser.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(AdministrationUser.this);
        name.setHint("Name, E.G. John Doe");
        layout.addView(name);
        final EditText email = new EditText(AdministrationUser.this);
        email.setHint("Email, E.G. johndoe@gmail.com");
        layout.addView(email);
        final EditText phone = new EditText(AdministrationUser.this);
        phone.setHint("Phone, E.G. 0871234567");
        layout.addView(phone);
        final EditText password = new EditText(AdministrationUser.this);
        password.setHint("Password, E.G. somepassword");
        layout.addView(password);
        id = position;
        name.setText(users.get(position).getName());
        email.setText(users.get(position).getEmail());
        phone.setText(users.get(position).getPhone());
        password.setText(users.get(position).getPassword());
        alert.setView(layout);
        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditName = name.getText().toString();
                String stringEditEmail = email.getText().toString();
                String stringEditPhone = phone.getText().toString();
                String stringEditPassword = password.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                User tempEdit = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                users.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
                saveData();
                Toast.makeText(AdministrationUser.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditName = name.getText().toString();
                String stringEditEmail = email.getText().toString();
                String stringEditPhone = phone.getText().toString();
                String stringEditPassword = password.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                User tempEdit = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                users.set(id, tempEdit);
                id = -1;
                users.remove(tempEdit);
                myAdapter.notifyDataSetChanged();
                saveData();
                Toast.makeText(AdministrationUser.this, "Deleted User", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addWindow();
            }
        });
        alert.show();
        return position;
    }

    public boolean validate(String stringEditName, EditText name, String stringEditEmail, EditText email, String stringEditPhone, EditText phone, String stringEditPassword, EditText password) {
        boolean valid = true;
        if (stringEditName.isEmpty() || stringEditName.length() > 32) {
            name.setError("Please Enter Valid Name");
            valid = false;
        }
        if (stringEditEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(stringEditEmail).matches()) {
            email.setError("Please Enter Valid Email");
        }
        if (stringEditPhone.isEmpty()) {
            phone.setError("Please Enter Valid Phone");
        }
        if (stringEditPassword.isEmpty()) {
            password.setError("Please Enter Valid Password");
        }
        return valid;
    }

    public void addWindow() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationUser.this);
        alert.setTitle("Administration User");
        final LinearLayout layout = new LinearLayout(AdministrationUser.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(AdministrationUser.this);
        name.setHint("Name, E.G. John Doe");
        layout.addView(name);
        final EditText email = new EditText(AdministrationUser.this);
        email.setHint("Email, E.G. johndoe@gmail.com");
        layout.addView(email);
        final EditText phone = new EditText(AdministrationUser.this);
        phone.setHint("Phone, E.G. 0871234567");
        layout.addView(phone);
        final EditText password = new EditText(AdministrationUser.this);
        password.setHint("Password, E.G. somepassword");
        layout.addView(password);
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditName = name.getText().toString();
                String stringEditEmail = email.getText().toString();
                String stringEditPhone = phone.getText().toString();
                String stringEditPassword = password.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                if (!validate(stringEditName, name, stringEditEmail, email, stringEditPhone, phone, stringEditPassword, password)) {
                    Toast.makeText(AdministrationUser.this, "Signup has Failed", Toast.LENGTH_SHORT).show();

                } else {
                    User temp = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword, currentDateTimeString);
                    users.add(temp);
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationUser.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.setView(layout);
        alert.show();
    }
}