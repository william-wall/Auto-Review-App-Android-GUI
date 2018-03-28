package ie.williamwall.autoreview.firebaseReview;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase;
import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.review.Review;

import static ie.williamwall.autoreview.firebaseReview.CustomImage.DATABASE_PATH;

public class ViewData extends AppCompatActivity {

    ListView listView;
    List<Person> list;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    private Person selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        listView = (ListView) findViewById(R.id.list1);

        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching please wait");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);



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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Person user = (Person) adapterView.getItemAtPosition(i);
                selectedUser = user;

                 String title = user.getName();
                 String review = user.getEmail();
//                Toast.makeText(ViewData.this, title, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ViewData.this, review, Toast.LENGTH_SHORT).show();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewData.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_review_update, null);
                  final EditText mTitle = (EditText) mView.findViewById(R.id.insertName);
                final EditText mDesc = (EditText) mView.findViewById(R.id.insertEmail);
                mTitle.setText(title);
                mDesc.setText(review);
                mBuilder.setView(mView);
                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
         String title = mTitle.getText().toString();
                        String desc = mDesc.getText().toString();

                        databaseReference.child(user.getUid()).child("name").setValue(title);
                        databaseReference.child(user.getUid()).child("email").setValue(desc);

//                        databaseReference.child(user.getUid()).child("email").setValue("FRANK");
//                        databaseReference.child(user.getUid()).child("name").setValue(title);
//                        databaseReference.child(user.getUid()).child("email").setValue(review);
                    }
                });
                mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        databaseReference.child(selectedUser.getUid()).removeValue();




//                        AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
//                        builder.setTitle("Confirm");
//                        builder.setMessage("Are you sure you want to delete?");
//                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                String stringEditTitle = mTitle.getText().toString();
//                                String stringEditDesc = mDesc.getText().toString();
//                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                                Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
//                                someReviews.set(id, tempEdit);
//                                id = -1;
//                                someReviews.remove(tempEdit);
//                                myAdapter.notifyDataSetChanged();
//                                saveData();
//                                Toast.makeText(AdministrationReview.this, "Deleted ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase", Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                        builder.show();
                    }
                });
                mBuilder.setNeutralButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        addWindow();
                    }
                });
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
//                return position;

                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////




            }
        });
    }
}
