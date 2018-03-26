package ie.williamwall.autoreview.reviewImageInserts;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ie.williamwall.autoreview.R;

public class ViewData extends AppCompatActivity {

    ListView listView;
    List<Person> list;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        listView = (ListView) findViewById(R.id.list1);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(CustomImage.DATABASE_PATH);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Person person = snap.getValue(Person.class);
                    list.add(person);

                }
                myAdapter = new MyAdapter(ViewData.this, R.layout.data_items, list);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
