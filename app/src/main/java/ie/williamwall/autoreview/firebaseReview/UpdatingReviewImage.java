package ie.williamwall.autoreview.firebaseReview;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.navigationdrawer.HomeNavigation;

public class UpdatingReviewImage extends AppCompatActivity {
    ImageView imageView;
    EditText name, email;
    private Person selectedUser;

//    Person user;
//
//   public void setPerson(Person user)
//   {
//       this.user = user;
//
//
//   }

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public static final String STORAGE_PATH = "images/";
    public static final String DATABASE_PATH = "mainObject";
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating_review);

        imageView = (ImageView) findViewById(R.id.insertImages);
        name = (EditText) findViewById(R.id.insertName);
        email = (EditText) findViewById(R.id.insertEmail);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);




//        Person user = (Person) getIntent().getParcelableExtra("MyClass");
//
//       String title = user.getName();
//        String review=  user.getEmail();

        String title = getIntent().getStringExtra("message_key");
        String review = getIntent().getStringExtra("message_key2");



//String title =  user.getName();
//
//String review =user.getEmail();
//
                name.setText(title);
                email.setText(review);














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
                imageView.setImageBitmap(bitmap);
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

    public void uploadData(View view){
        if(imageUri != null)
        {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getActualImage(imageUri));
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Intent intent = getIntent();
                    Person user = (Person) intent.getSerializableExtra("MyClass");


                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

//                    Intent intent = getIntent();
//                    Person user = (Person) intent.getSerializableExtra("MyClass");
//                    String UID = getIntent().getStringExtra("message_key3");
//                final Person user = (Person) adapterView.getItemAtPosition(i);
//                selectedUser = user;
//                    final EditText mTitle = (EditText) findViewById(R.id.insertName);

//                    String title = getIntent().getStringExtra("message_key");
//
//                    databaseReference.child(user.getUid()).child("name").setValue(title);

//                    Editable title = mTitle.getText();
                    String NAME = name.getText().toString();
                    String EMAIL = email.getText().toString();
//                    String TIME = user.getUserTime();
//                    Person person = new Person(UUID.randomUUID().toString(),NAME, EMAIL, taskSnapshot.getDownloadUrl().toString());
//                    databaseReference.child(person.getUid()).setValue(person);

                    databaseReference.child(user.getUid()).child("name").setValue(NAME);


                    databaseReference.child(user.getUid()).child("email").setValue(EMAIL);
                    databaseReference.child(user.getUid()).child("imageUri").setValue(taskSnapshot.getDownloadUrl().toString());
                    databaseReference.child(user.getUid()).child("userTime").setValue(currentDateTimeString);

//                    databaseReference.child("name").setValue(NAME);
//                    databaseReference.child("uid").setValue(UUID.randomUUID().toString());

//

//                    String id = databaseReference.push().getKey();
//
//                    databaseReference.child(id).setValue(person);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data uploaded", Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double totalProgress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded % "+ (int)totalProgress);
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Please select data first", Toast.LENGTH_LONG).show();
        }
    }
    public void viewAllData(View view){
        Intent intent = new Intent(UpdatingReviewImage.this, HomeNavigation.class);
        startActivity(intent);

    }
    public void deleteInstance(View view){

//        databaseReference.child(user.getUid()).child("name").setValue(NAME);
        Intent intent = getIntent();
        Person user = (Person) intent.getSerializableExtra("MyClass");
        databaseReference.child(user.getUid()).removeValue();
        Intent intentMove = new Intent(UpdatingReviewImage.this, ViewData.class);
        startActivity(intentMove);
        Toast.makeText(getApplicationContext(), "Successfully deleted review", Toast.LENGTH_LONG).show();


    }
}
