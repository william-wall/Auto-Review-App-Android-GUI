package ie.williamwall.autoreview.firebaseReview;

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

public class ViewData extends AppCompatActivity {
    Activity activity;

    ListView listView;
    List<Person> list;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    private Person selectedUser;
    private Uri imageUri;
//    public static final String STORAGE_PATH = "images/";
//    public static final String DATABASE_PATH = "mainObject";

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

//                Person userSend = new Person();
//                selectedUser = userSend;


                String UID = user.getName();
                 String title = user.getName();
                 String review = user.getEmail();




                Intent move = new Intent(ViewData.this, UpdatingReviewImage.class);
                move.putExtra("message_key", title);
                move.putExtra("message_key2", review);
//                move.putExtra("message_key3", UID);
                move.putExtra("MyClass",  user);
                startActivity(move);
//                UpdatingReviewImage reviewInstanceSend = new UpdatingReviewImage();
//                reviewInstanceSend.setPerson(user);
//                final Person user = (Person) adapterView.getItemAtPosition(i);
//                selectedUser = user;
//
//                    String imageLogo = user.getImageUri();
//                 String title = user.getName();
//                 String review = user.getEmail();
////                Toast.makeText(ViewData.this, title, Toast.LENGTH_SHORT).show();
////                Toast.makeText(ViewData.this, review, Toast.LENGTH_SHORT).show();
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewData.this);
//                View mView = getLayoutInflater().inflate(R.layout.activity_review_update, null);
//                ImageView mImage = (ImageView) mView.findViewById(R.id.updateImages);
//
//                final EditText mTitle = (EditText) mView.findViewById(R.id.insertName);
//                final EditText mDesc = (EditText) mView.findViewById(R.id.insertEmail);
////                Glide.with(activity).load(list.get(i).getImageUri()).into(mImage);
////                mImage.setImageURI(Uri.parse(imageLogo));
////                mImage.getImageUri().into(mImage);
////mImage.setImageURI(Uri.parse("image/*"+imageLogo));
////                listView.getItemAtPosition(i).getImageUri().into(mImage);
////                user.getImageUri();
//                mImage.setImageURI(Uri.parse(imageLogo));
//                mTitle.setText(title);
//                mDesc.setText(review);
//                mBuilder.setView(mView);
//                mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//         String title = mTitle.getText().toString();
//                        String desc = mDesc.getText().toString();
//
//                        databaseReference.child(user.getUid()).child("name").setValue(title);
//                        databaseReference.child(user.getUid()).child("email").setValue(desc);
//
////                        databaseReference.child(user.getUid()).child("email").setValue("FRANK");
////                        databaseReference.child(user.getUid()).child("name").setValue(title);
////                        databaseReference.child(user.getUid()).child("email").setValue(review);
//                    }
//                });
//                mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                        databaseReference.child(selectedUser.getUid()).removeValue();
//
//
//
//
////                        AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
////                        builder.setTitle("Confirm");
////                        builder.setMessage("Are you sure you want to delete?");
////                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
////                            public void onClick(DialogInterface dialog, int which) {
////                                String stringEditTitle = mTitle.getText().toString();
////                                String stringEditDesc = mDesc.getText().toString();
////                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
////                                Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
////                                someReviews.set(id, tempEdit);
////                                id = -1;
////                                someReviews.remove(tempEdit);
////                                myAdapter.notifyDataSetChanged();
////                                saveData();
////                                Toast.makeText(AdministrationReview.this, "Deleted ie.williamwall.autoreview.firebaseUser.UserInstanceFirebase", Toast.LENGTH_LONG).show();
////                                dialog.dismiss();
////                            }
////                        });
////                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                            }
////                        });
////                        builder.show();
//                    }
//                });
//                mBuilder.setNeutralButton("New", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
////                        addWindow();
//                        Intent move = new Intent(ViewData.this, CustomImage.class);
//                        startActivity(move);
//                    }
//                });
//                final AlertDialog dialog = mBuilder.create();
//                dialog.show();
////                return position;
//
//                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        });

    }
    public void browseImages(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            imageUri = data.getData();

            try{

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);

//                mImage.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getActualImage(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
