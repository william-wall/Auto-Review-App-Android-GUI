package ie.williamwall.autoreview.review;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.LoginActivityFirebase;

public class AdministrationReview extends AppCompatActivity implements View.OnClickListener {

    int id = -1;
    ListView mainList;
//    EditText editTextTitle;
//    EditText editTextDesc;
    private TextView userLoginName;
//    Button addButton, editButton, clearButton, saveButton;
    //            moveButton;
    ArrayList<Review> someReviews;
    CustomAdapterReview myAdapter;
    ArrayAdapter<Review> adapter;
    SearchView sv;
//
//    public static AdministrationReview getInstance() {
//        return instance;
//    }
//
//    public ArrayList<Review> getSomeReviews(){
//        return someReviews;
//    }

    private void init() {
        mainList = (ListView) findViewById(R.id.list_cars);
//        editTextTitle = (EditText) findViewById(R.id.edit_title);
//        editTextDesc = (EditText) findViewById(R.id.edit_desc);
//        addButton = (Button) findViewById(R.id.add);
//        editButton = (Button) findViewById(R.id.edit);
//        clearButton = (Button) findViewById(R.id.clear);
//        saveButton = (Button) findViewById(R.id.save);
//        moveButton = (Button) findViewById(R.id.fullWindow);
        sv = (SearchView) findViewById(R.id.searchCar);
        userLoginName = (TextView) findViewById(R.id.userLoginDisplay);
//        final String message3 = getIntent().getStringExtra("message_key_user");
//        userLoginName.setText(message3);
//        addButton.setOnClickListener(this);
//        editButton.setOnClickListener(this);
//        clearButton.setOnClickListener(this);
//        saveButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




//        final String gotUpdatedTitle = getIntent().getStringExtra("sendBackTitle_key");
//        final String gotUpdatedDesc = getIntent().getStringExtra("sendBackDesc_key");
//        Review tempEdit = new Review(R.mipmap.ic_launcher, gotUpdatedTitle, gotUpdatedDesc);
//        someReviews.set(id, tempEdit);
//        id = -1;
//        myAdapter.notifyDataSetChanged();





        init();
        loadData();
        Review reviewInstance = new Review(R.mipmap.car, "OPEL", "VECTRA");
        someReviews.add(reviewInstance);

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
                window(position);
//                titleInstance = someReviews.get(position).getReviewTitle();
//                descInstance = someReviews.get(position).getReviewDesc();
//                Intent move = new Intent(AdministrationReview.this, ReviewDetail.class);
//                move.putExtra("sendTitle_key", titleInstance);
//                move.putExtra("sendDesc_key", descInstance);
//                startActivity(move);

            }
        });
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                someReviews.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(AdministrationReview.this, "clear", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
//                Toast.makeText(this, "Car Review Added", Toast.LENGTH_SHORT).show();
//                String stringTitle = editTextTitle.getText().toString();
//                String stringDesc = editTextDesc.getText().toString();
//                Review temp = new Review(R.mipmap.ic_launcher, stringTitle, stringDesc);
//                someReviews.add(temp);
//                myAdapter.notifyDataSetChanged();
//                saveData();
                break;
            case R.id.edit:
//                String stringEditTitle = editTextTitle.getText().toString();
//                String stringEditDesc = editTextDesc.getText().toString();
//                Review tempEdit = new Review(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc);
//                someReviews.set(id, tempEdit);
//                id = -1;
//                myAdapter.notifyDataSetChanged();
//                saveData();
                break;
            case R.id.clear:
                break;
            case R.id.save:
//                Toast.makeText(this, "Review Saved!", Toast.LENGTH_SHORT).show();
//                saveData();
                break;
        }
    }

    public void searchViewTitle() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, someReviews);
        mainList.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AdministrationReview.this, adapter.getItem(i).getReviewTitle(), Toast.LENGTH_SHORT).show();
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
            Intent Intent = new Intent(AdministrationReview.this, LoginActivityFirebase.class);
            startActivity(Intent);

//            return true;
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
//        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
//                "so be very careful what way you drive it for god sake"));
//        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
//                "so be very careful what way you drive it for god sake"));
//        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
//                "so be very careful what way you drive it for god sake"));
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

    public int window(int position)
    {



        AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationReview.this);
        alert.setTitle("Administer Review");

        LinearLayout layout = new LinearLayout(AdministrationReview.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText title = new EditText(AdministrationReview.this);
        layout.addView(title);

        final EditText desc = new EditText(AdministrationReview.this);
        layout.addView(desc);

        id = position;
        title.setText(someReviews.get(position).getReviewTitle());
        desc.setText(someReviews.get(position).getReviewDesc());



        alert.setView(layout);

        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditTitle = title.getText().toString();
                String stringEditDesc = desc.getText().toString();
                Review tempEdit = new Review(R.mipmap.car, stringEditTitle, stringEditDesc);
                someReviews.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
//                wname = Wifi_textbox.getText().toString();
//                mname = Mac_textbox.getText().toString();
//                fname = Floorname.getText().toString();
//                wgname = WingName.getText().toString();

                Toast.makeText(AdministrationReview.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
                saveData();
            }
        });

        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                String stringEditTitle = title.getText().toString();
                String stringEditDesc = desc.getText().toString();
                Review tempEdit = new Review(R.mipmap.car, stringEditTitle, stringEditDesc);
                someReviews.set(id, tempEdit);
                id = -1;
                someReviews.remove(tempEdit);
                myAdapter.notifyDataSetChanged();
                saveData();




            }
        });
        alert.setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // what ever you want to do with No option.

                AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationReview.this);
                alert.setTitle("Administer Review");

                LinearLayout layout = new LinearLayout(AdministrationReview.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText title = new EditText(AdministrationReview.this);
                layout.addView(title);

                final EditText desc = new EditText(AdministrationReview.this);
                layout.addView(desc);

//                id = position;
//                title.setText(someReviews.get(position).getReviewTitle());
//                desc.setText(someReviews.get(position).getReviewDesc());

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String stringEditTitle = title.getText().toString();
                        String stringEditDesc = desc.getText().toString();
                        Review temp = new Review(R.mipmap.car, stringEditTitle, stringEditDesc);
                        someReviews.add(temp);
                        myAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(AdministrationReview.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();

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
