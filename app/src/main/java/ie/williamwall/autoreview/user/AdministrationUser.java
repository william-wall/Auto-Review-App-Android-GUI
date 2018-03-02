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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.LoginActivityFirebase;

/**
 * Created by william on 27/02/2018.
 */

public class AdministrationUser extends AppCompatActivity {
    ListView lv;
//    EditText nameTxt, emailTxt, phoneTxt, passwordTxt;
    SearchView searchName;
    CustomAdapterUser myAdapter;
    ArrayList<User> users = new ArrayList<User>();
    int id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadData();
        User userInstance = new User(R.mipmap.car, "JAMES", "HACK", "0879858985" ,"12");
        users.add(userInstance);
        lv = (ListView) findViewById(R.id.listViewMain);
        searchName = (SearchView) findViewById(R.id.searchViewName);

//        nameTxt = (EditText) findViewById(R.id.editName);
//        emailTxt = (EditText) findViewById(R.id.editEmail);
//        phoneTxt = (EditText) findViewById(R.id.editPhone);
//        passwordTxt = (EditText) findViewById(R.id.editPassword);



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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationUser.this, LoginActivityFirebase.class);
            startActivity(Intent);

//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void add() {

//        String stringName = nameTxt.getText().toString();
//        String stringEmail = emailTxt.getText().toString();
//        String stringPhone = phoneTxt.getText().toString();
//        String stringPassword = passwordTxt.getText().toString();
//        User temp = new User(R.mipmap.car, stringName, stringEmail, stringPhone, stringPassword);
//        users.add(temp);
//        myAdapter.notifyDataSetChanged();
//        saveData();
//        Toast.makeText(AdministrationUser.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();

    }

    private void update() {

    }

    private void delete() {

    }

    private void clear() {

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

    public int window(int position)
    {



        AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationUser.this);
        alert.setTitle("Administer Review");

        LinearLayout layout = new LinearLayout(AdministrationUser.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText name = new EditText(AdministrationUser.this);
        name.setHint("Name");
        layout.addView(name);

        final EditText email = new EditText(AdministrationUser.this);
        email.setHint("Email");
        layout.addView(email);

        final EditText phone = new EditText(AdministrationUser.this);
        phone.setHint("Phone");
        layout.addView(phone);

        final EditText password = new EditText(AdministrationUser.this);
        password.setHint("Password");
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
                User tempEdit = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword);
                users.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
                saveData();
                Toast.makeText(AdministrationUser.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditName = name.getText().toString();
                String stringEditEmail = email.getText().toString();
                String stringEditPhone = phone.getText().toString();
                String stringEditPassword = password.getText().toString();
                User tempEdit = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword);
                users.set(id, tempEdit);
                id = -1;
                users.remove(tempEdit);
                myAdapter.notifyDataSetChanged();
                saveData();
            }
        });
        alert.setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationUser.this);
                alert.setTitle("Administer User");

                LinearLayout layout = new LinearLayout(AdministrationUser.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText name = new EditText(AdministrationUser.this);
                name.setHint("Name");
                layout.addView(name);

                final EditText email = new EditText(AdministrationUser.this);
                email.setHint("Email");
                layout.addView(email);

                final EditText phone = new EditText(AdministrationUser.this);
                phone.setHint("Phone");
                layout.addView(phone);

                final EditText password = new EditText(AdministrationUser.this);
                password.setHint("Password");
                layout.addView(password);




                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String stringEditName = name.getText().toString();
                        String stringEditEmail = email.getText().toString();
                        String stringEditPhone = phone.getText().toString();
                        String stringEditPassword = password.getText().toString();
                        User temp = new User(R.mipmap.car, stringEditName, stringEditEmail, stringEditPhone, stringEditPassword);
                        users.add(temp);
                        myAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(AdministrationUser.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.setView(layout);


                alert.show();

            }
        });

        alert.show();

        return position;

    }

}