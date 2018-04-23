package ie.williamwall.autoreview.navigationdrawer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import ie.williamwall.autoreview.newNavigation.About;
import ie.williamwall.autoreview.newNavigation.ReviewHome;
import ie.williamwall.autoreview.newNavigation.ShareFacebook;
import ie.williamwall.autoreview.newNavigation.WeatherReport;
import ie.williamwall.autoreview.firebaseAdministrator.LoginActivityFirebase;
import ie.williamwall.autoreview.firebaseReview.CustomImage;
import ie.williamwall.autoreview.firebaseReview.Person;
import ie.williamwall.autoreview.maps.MapsActivity;

public class Updating extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView imageView;
    EditText name, email;
    private Person selectedUser;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    private FirebaseAuth auth;
    TextView userNameDisplayNav;

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
        setContentView(R.layout.activity_updating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.insertImages);
        name = (EditText) findViewById(R.id.insertName);
        email = (EditText) findViewById(R.id.insertEmail);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();


        auth = FirebaseAuth.getInstance();

        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(userId);


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Updating.this, LoginActivityFirebase.class));
                    finish();
                }
            }
        };


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        userNameDisplayNav = (TextView) headerView.findViewById(R.id.usersNameNav);
        final FirebaseUser userNav = FirebaseAuth.getInstance().getCurrentUser();
        String gotNameNav = getDataToView(userNav);
        userNameDisplayNav.setText(gotNameNav);



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
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
//        userNameDisplay.setText(user.getEmail());
//        userNameDisplayNav.setText(user.getEmail());

    }
    @SuppressLint("SetTextI18n")
    private String getDataToView(FirebaseUser user) {
        String jjjj = user.getEmail();
        return jjjj;
    }
    public String getActualImage(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.review_home, menu);

//        final FirebaseUser userId2 = FirebaseAuth.getInstance().getCurrentUser();
//        setDataToView(userId2);


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
            Intent h= new Intent(Updating.this,About.class);
            startActivity(h);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent h= new Intent(Updating.this,ReviewHome.class);
            startActivity(h);
        } else if (id == R.id.nav_gallery) {
            Intent h= new Intent(Updating.this,WeatherReport.class);
            startActivity(h);
        } else if (id == R.id.nav_slideshow) {
            Intent h= new Intent(Updating.this,MapsActivity.class);
            startActivity(h);
        } else if (id == R.id.nav_manage) {
            Intent h= new Intent(Updating.this,ShareFacebook.class);
            startActivity(h);
        } else if (id == R.id.nav_share) {
            Intent h= new Intent(Updating.this,AccountNavigation.class);
            startActivity(h);
        } else if (id == R.id.nav_send) {
            auth.signOut();
// this listener will be called when there is change in firebase user session
            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(Updating.this, LoginActivityFirebase.class));
                        finish();
                    }
                }
            };
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(Updating.this, LoginActivityFirebase.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public boolean validate() {
        boolean valid = true;
        if (name.getText().toString().isEmpty() || name.length() <10) {
            name.setError("Title must be at least 10 characters");
            valid = false;
        }
        if (email.getText().toString().isEmpty()|| email.length() <20) {
            email.setError("Review must be at least 20 characters");
            valid = false;
        }
        if(imageUri == null  )
        {
            valid = false;
        }
        return valid;
    }
    public void uploadData(View view){



        if(!validate())
        {
            Toast.makeText(getApplicationContext(), "Please select data first", Toast.LENGTH_LONG).show();


        }else{


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


                    String NAME = name.getText().toString();
                    String EMAIL = email.getText().toString();

                    databaseReference.child(user.getUid()).child("name").setValue(NAME);


                    databaseReference.child(user.getUid()).child("email").setValue(EMAIL);
                    databaseReference.child(user.getUid()).child("imageUri").setValue(taskSnapshot.getDownloadUrl().toString());
                    databaseReference.child(user.getUid()).child("userTime").setValue(currentDateTimeString);



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
            Intent intentMove = new Intent(Updating.this, ReviewHome.class);
            startActivity(intentMove);
        }
    }
    public void viewAllData(View view){
        Intent intent = new Intent(Updating.this, ReviewHome.class);
        startActivity(intent);

    }
    public void deleteInstance(View view){

//        databaseReference.child(user.getUid()).child("name").setValue(NAME);
        AlertDialog.Builder builder = new AlertDialog.Builder(Updating.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                Person user = (Person) intent.getSerializableExtra("MyClass");
                databaseReference.child(user.getUid()).removeValue();
                Intent intentMove = new Intent(Updating.this, ReviewHome.class);
                startActivity(intentMove);
                Toast.makeText(getApplicationContext(), "Successfully deleted review", Toast.LENGTH_LONG).show();
                Toast.makeText(Updating.this, "Deleted", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();






    }
}
