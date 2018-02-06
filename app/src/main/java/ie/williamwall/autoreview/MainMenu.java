package ie.williamwall.autoreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    int id =-1;
    ListView mainList;
    EditText editTextTitle;
    EditText editTextDesc;
    Button addButton, editButton, clearButton;
    ArrayList<Reviews> someReviews;
    CustomAdapter myadapter;

    private void init(){
        mainList = (ListView) findViewById(R.id.list_cars);
        editTextTitle = (EditText) findViewById(R.id.edit_title);
        editTextDesc = (EditText) findViewById(R.id.edit_desc);
        addButton = (Button) findViewById(R.id.add);
        editButton = (Button) findViewById(R.id.edit);
        clearButton = (Button) findViewById(R.id.clear);

        addButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();

        someReviews=new ArrayList<Reviews>();
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));

        myadapter = new CustomAdapter(this, R.layout.item_layout, someReviews);
        mainList.setAdapter(myadapter);
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
                myadapter.notifyDataSetChanged();
                Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();

                return false;
            }
        });
    }

//    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:
                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();
                String stringTitle = editTextTitle.getText().toString();
                String stringDesc = editTextDesc.getText().toString();
                Reviews temp = new Reviews(R.mipmap.ic_launcher, stringTitle, stringDesc);
                someReviews.add(temp);
                myadapter.notifyDataSetChanged();
                break;
            case R.id.edit:
                String stringEditTitle = editTextTitle.getText().toString();
                String stringEditDesc = editTextDesc.getText().toString();
                Reviews tempEdit = new Reviews(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc);
                someReviews.set(id, tempEdit);
                id = -1;
                myadapter.notifyDataSetChanged();
                break;

            case R.id.clear:
//                someReviews.remove(position);
//                myadapter.notifyDataSetChanged();
//                Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();



//                String stringDeleteTitle = editTextTitle.getText().toString();
//                String stringDeleteDesc = editTextDesc.getText().toString();
//                Reviews tempDelete = new Reviews(R.mipmap.ic_launcher, stringDeleteTitle, stringDeleteDesc);
//
//                tempDelete = null;



//                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        someReviews.remove(position);
//                        myadapter.notifyDataSetChanged();
//                        Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();
//
//
//                    }
//                });

                break;


        }

    }


}