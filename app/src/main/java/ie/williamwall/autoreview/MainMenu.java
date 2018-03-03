package ie.williamwall.autoreview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.williamwall.autoreview.review.CustomAdapterReview;
import ie.williamwall.autoreview.review.Review;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    int id = -1;
    ListView mainList;
    EditText editTextTitle;
    EditText editTextDesc;
    private TextView userLoginName;
    Button addButton, editButton, clearButton, saveButton;
//            moveButton;
    ArrayList<Review> someReviews;
    CustomAdapterReview myAdapter;
    ArrayAdapter<Review> adapter;
    SearchView sv;

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
        setContentView(R.layout.activity_main_menu_administrator);
        init();
        loadData();
        myAdapter = new CustomAdapterReview(this, R.layout.item_layout_administration_review, someReviews);
        mainList.setAdapter(myAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                id = position;
                editTextTitle.setText(someReviews.get(position).getReviewTitle());
                editTextDesc.setText(someReviews.get(position).getReviewDesc());
            }
        });
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                someReviews.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
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
                Toast.makeText(MainMenu.this, adapter.getItem(i).getReviewTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();
                String stringTitle = editTextTitle.getText().toString();
                String stringDesc = editTextDesc.getText().toString();

                Review temp = new Review(R.mipmap.ic_launcher, stringTitle, stringDesc, currentDateTimeString);
                someReviews.add(temp);
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.edit:
                String currentDateTimeString2 = DateFormat.getDateTimeInstance().format(new Date());

                String stringEditTitle = editTextTitle.getText().toString();
                String stringEditDesc = editTextDesc.getText().toString();
                Review tempEdit = new Review(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc, currentDateTimeString2);
                someReviews.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.clear:
                break;
            case R.id.save:
                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();
                saveData();
                break;
        }
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
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        someReviews = new ArrayList<Review>();
        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
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
}
