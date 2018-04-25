package ie.williamwall.autoreview.firebaseReview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ie.williamwall.autoreview.R;

import static ie.williamwall.autoreview.firebaseReview.CustomImage.DATABASE_PATH;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI
public class ViewData extends AppCompatActivity {
    Activity activity;
    TextView userNameDisplay;
    ListView listView;
    List<Person> list;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    private Person selectedUser;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        userNameDisplay = (TextView) findViewById(R.id.userSignInName);
        listView = (ListView) findViewById(R.id.list1);
        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(userId);

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

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
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

                final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                String gotIT = getDataToView(userId);

                final Person user = (Person) adapterView.getItemAtPosition(i);
                selectedUser = user;

                String userInstanceReview = user.getUserName();

                String UID = user.getName();
                String title = user.getName();
                String review = user.getEmail();


                if (!userInstanceReview.matches(gotIT)) {
                    Toast.makeText(getApplicationContext(), "You cannot edit a review you didnt create!!!", Toast.LENGTH_LONG).show();

                } else {

                    Intent move = new Intent(ViewData.this, UpdatingReviewImage.class);
                    move.putExtra("message_key", title);
                    move.putExtra("message_key2", review);
                    move.putExtra("MyClass", user);
                    startActivity(move);
                }
            }
        });

    }

    public void browseImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActualImage(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        userNameDisplay.setText(user.getEmail());

    }

    @SuppressLint("SetTextI18n")
    private String getDataToView(FirebaseUser user) {
        String getDataString = user.getEmail();
        return getDataString;
    }
}
