package ie.williamwall.autoreview.firebaseUser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ie.williamwall.autoreview.R;

public class FirebaseUser extends AppCompatActivity {

    private EditText input_name, input_email;
    private ListView list_data;
    private ProgressBar circular_progress;

    private FirebaseDatabase mFirebaseDatabaseUser;
    private DatabaseReference mDatabaseReferenceUser;

    private List<UserInstanceFirebase> list_users = new ArrayList<>();

    private UserInstanceFirebase selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUser);
        toolbar.setTitle("Firebase ");
        setSupportActionBar(toolbar);

        circular_progress = (ProgressBar) findViewById(R.id.circular_progress);
        input_name = (EditText) findViewById(R.id.name);
        input_email = (EditText) findViewById(R.id.email);
        list_data = (ListView) findViewById(R.id.list_data);
        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserInstanceFirebase user = (UserInstanceFirebase) adapterView.getItemAtPosition(i);
                selectedUser = user;
                input_name.setText(user.getName());
                input_email.setText(user.getEmail());
            }
        });

        initFirebase();
        addEventFirebaseListner();


    }

    private void addEventFirebaseListner() {
        circular_progress.setVisibility(View.VISIBLE);

        list_data.setVisibility(View.INVISIBLE);

        mDatabaseReferenceUser.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (list_users.size() > 0)
                    list_users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UserInstanceFirebase user = postSnapshot.getValue(UserInstanceFirebase.class);
                    list_users.add(user);
                }
                ListViewAdapterUserFirebase adapter = new ListViewAdapterUserFirebase(FirebaseUser.this, list_users);
                list_data.setAdapter(adapter);

                circular_progress.setVisibility(View.INVISIBLE);
                list_data.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabaseUser = FirebaseDatabase.getInstance();
        mDatabaseReferenceUser = mFirebaseDatabaseUser.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            createUser();
        }
        else if(item.getItemId()==R.id.menu_save)
        {
            UserInstanceFirebase user = new UserInstanceFirebase(selectedUser.getUid(),input_name.getText().toString(),input_email.getText().toString());
            updateUser(user);
        }
        else if(item.getItemId() == R.id.menu_remove){
            deleteUser(selectedUser);
        }
            return true;
    }

    private void deleteUser(UserInstanceFirebase selectedUser) {
        mDatabaseReferenceUser.child("users").child(selectedUser.getUid()).removeValue();
        clearEditText();
    }

    private void updateUser(UserInstanceFirebase user) {
        mDatabaseReferenceUser.child("users").child(user.getUid()).child("name").setValue(user.getName());
        mDatabaseReferenceUser.child("users").child(user.getUid()).child("email").setValue(user.getEmail());
        clearEditText();

    }


    private void createUser()
    {
        UserInstanceFirebase user = new UserInstanceFirebase(UUID.randomUUID().toString(),input_name.getText().toString(),input_email.getText().toString());
        mDatabaseReferenceUser.child("users").child(user.getUid()).setValue(user);
        clearEditText();
    }

    private void clearEditText() {
        input_name.setText("");
        input_email.setText("");

    }
}