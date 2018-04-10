package ie.williamwall.autoreview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import ie.williamwall.autoreview.firebaseReview.MyAdapter;
import ie.williamwall.autoreview.firebaseReview.Person;
import ie.williamwall.autoreview.firebaseReview.UpdatingReviewImage;

import static ie.williamwall.autoreview.firebaseReview.CustomImage.DATABASE_PATH;

public class HomeNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;

    Activity activity;
    TextView userNameDisplay;
    TextView userNameDisplayNav;
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
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        userNameDisplayNav = (TextView) findViewById(R.id.usersNameNav);
        userNameDisplay = (TextView) findViewById(R.id.userSignInName);
        listView = (ListView) findViewById(R.id.list1);




        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(userId);








//        String gettingUserName = getDataToView(userId);
//        userNameDisplayNav.setText(gettingUserName);

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
                myAdapter = new MyAdapter(HomeNavigation.this, R.layout.data_items, list);
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
                String gotIT=   getDataToView(userId);




                final Person user = (Person) adapterView.getItemAtPosition(i);
                selectedUser = user;

//                Person userSend = new Person();
//                selectedUser = userSend;
                String userInstanceReview = user.getUserName();

                String UID = user.getName();
                String title = user.getName();
                String review = user.getEmail();



                if(!userInstanceReview.matches(gotIT))
                {
                    Toast.makeText(getApplicationContext(), "You cannot edit a review you didnt create!!!", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent move = new Intent(HomeNavigation.this, UpdatingReviewImage.class);
                    move.putExtra("message_key", title);
                    move.putExtra("message_key2", review);
//                move.putExtra("message_key3", UID);
                    move.putExtra("MyClass", user);
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
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        userNameDisplay.setText(user.getEmail());
//        userNameDisplayNav.setText(user.getEmail());

    }
    @SuppressLint("SetTextI18n")
    private String getDataToView(FirebaseUser user) {
        String jjjj = user.getEmail();
        return jjjj;
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
        getMenuInflater().inflate(R.menu.home_navigation, menu);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(HomeNavigation.this,HomeNavigation.class);
                startActivity(h);
                break;
            case R.id.nav_import:
                Intent i= new Intent(HomeNavigation.this,WeatherNavigation.class);
                startActivity(i);
                break;
            case R.id.nav_gallery:
                Intent g= new Intent(HomeNavigation.this,MapsNavigation.class);
                startActivity(g);
                break;
            case R.id.nav_slideshow:
                Intent s= new Intent(HomeNavigation.this,Slideshow.class);
                startActivity(s);
            case R.id.nav_tools:
                Intent t= new Intent(HomeNavigation.this,AccountNavigation.class);
                startActivity(t);
                break;
            // this is done, now let us go and intialise the home page.
            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
