package ie.williamwall.autoreview.notInUse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.review.CustomAdapterReview;
import ie.williamwall.autoreview.review.Review;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

public class MainMenuUser extends AppCompatActivity implements View.OnClickListener {

    int id = -1;
    ListView mainList;
    EditText editTextTitle;
    EditText editTextDesc;
    Button addButton, editButton;
    ArrayList<Review> someReviews;
    CustomAdapterReview myadapter;
    TextView userName;

    private void init() {
        mainList = (ListView) findViewById(R.id.list_cars);
        editTextTitle = (EditText) findViewById(R.id.edit_title);
        editTextDesc = (EditText) findViewById(R.id.edit_desc);
        addButton = (Button) findViewById(R.id.add);
        editButton = (Button) findViewById(R.id.edit);
        addButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_user);
        userName = (TextView) findViewById(R.id.userName);
        String message = getIntent().getStringExtra("message_key");
        userName.setText(message);
        init();
        someReviews = new ArrayList<Review>();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
        someReviews.add(new Review(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake",currentDateTimeString));
        myadapter = new CustomAdapterReview(this, R.layout.item_layout_administration_review, someReviews);
        mainList.setAdapter(myadapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                id = position;
                editTextTitle.setText(someReviews.get(position).getReviewTitle());
                editTextDesc.setText(someReviews.get(position).getReviewDesc());
            }
        });
//        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                someReviews.remove(position);
//                myadapter.notifyDataSetChanged();
//                Toast.makeText(MainMenuUser.this, "clear", Toast.LENGTH_SHORT ).show();
//
//                return false;
//            }
//        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add:
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();
                String stringTitle = editTextTitle.getText().toString();
                String stringDesc = editTextDesc.getText().toString();
                Review temp = new Review(R.mipmap.ic_launcher, stringTitle, stringDesc,currentDateTimeString);
                someReviews.add(temp);
                myadapter.notifyDataSetChanged();
                break;
            case R.id.edit:
                String currentDateTimeString2 = DateFormat.getDateTimeInstance().format(new Date());

                String stringEditTitle = editTextTitle.getText().toString();
                String stringEditDesc = editTextDesc.getText().toString();
                Review tempEdit = new Review(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc,currentDateTimeString2);
                someReviews.set(id, tempEdit);
                id = -1;
                myadapter.notifyDataSetChanged();
                break;
            case R.id.clear:
                break;
        }
    }
}
