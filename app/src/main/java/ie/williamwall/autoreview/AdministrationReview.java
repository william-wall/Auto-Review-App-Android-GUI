package ie.williamwall.autoreview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdministrationReview extends AppCompatActivity implements View.OnClickListener {

    int id = -1;
    ListView mainList;
    EditText editTextTitle;
    EditText editTextDesc;
    private TextView userLoginName;
    Button addButton, editButton, clearButton, saveButton;
    //            moveButton;
    ArrayList<Reviews> someReviews;
    CustomAdapter myAdapter;
    ArrayAdapter<Reviews> adapter;
    SearchView sv;
//
//    public static AdministrationReview getInstance() {
//        return instance;
//    }
//
//    public ArrayList<Reviews> getSomeReviews(){
//        return someReviews;
//    }

    private void init() {
        mainList = (ListView) findViewById(R.id.list_cars);
        editTextTitle = (EditText) findViewById(R.id.edit_title);
        editTextDesc = (EditText) findViewById(R.id.edit_desc);
        addButton = (Button) findViewById(R.id.add);
        editButton = (Button) findViewById(R.id.edit);
        clearButton = (Button) findViewById(R.id.clear);
        saveButton = (Button) findViewById(R.id.save);
//        moveButton = (Button) findViewById(R.id.fullWindow);
        sv = (SearchView) findViewById(R.id.searchCar);
        userLoginName = (TextView) findViewById(R.id.userLoginDisplay);
        final String message3 = getIntent().getStringExtra("message_key");
        userLoginName.setText(message3);
        addButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
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
//        Reviews tempEdit = new Reviews(R.mipmap.ic_launcher, gotUpdatedTitle, gotUpdatedDesc);
//        someReviews.set(id, tempEdit);
//        id = -1;
//        myAdapter.notifyDataSetChanged();





        init();
        loadData();
        myAdapter = new CustomAdapter(this, R.layout.item_layout_administration_review, someReviews);
        mainList.setAdapter(myAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String titleInstance = "";
                String descInstance = "";
                id = position;
                editTextTitle.setText(someReviews.get(position).getReviewTitle());
                editTextDesc.setText(someReviews.get(position).getReviewDesc());
                titleInstance = someReviews.get(position).getReviewTitle();
                descInstance = someReviews.get(position).getReviewDesc();
                Intent move = new Intent(AdministrationReview.this, ReviewDetail.class);
                move.putExtra("sendTitle_key", titleInstance);
                move.putExtra("sendDesc_key", descInstance);
                startActivity(move);

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
                Toast.makeText(this, "Car Review Added", Toast.LENGTH_SHORT).show();
                String stringTitle = editTextTitle.getText().toString();
                String stringDesc = editTextDesc.getText().toString();
                Reviews temp = new Reviews(R.mipmap.ic_launcher, stringTitle, stringDesc);
                someReviews.add(temp);
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.edit:
                String stringEditTitle = editTextTitle.getText().toString();
                String stringEditDesc = editTextDesc.getText().toString();
                Reviews tempEdit = new Reviews(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc);
                someReviews.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.clear:
                break;
            case R.id.save:
                Toast.makeText(this, "Review Saved!", Toast.LENGTH_SHORT).show();
                saveData();
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
            Intent Intent = new Intent(AdministrationReview.this, Login.class);
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
        someReviews = new ArrayList<Reviews>();
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Reviews>>() {
        }.getType();
        someReviews = gson.fromJson(json, type);
        if (someReviews == null) {
            someReviews = new ArrayList<>();
        }
    }

}
