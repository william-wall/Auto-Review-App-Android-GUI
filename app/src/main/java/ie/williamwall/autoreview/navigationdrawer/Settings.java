package ie.williamwall.autoreview.navigationdrawer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseAdministrator.LoginActivityFirebase;

public class Settings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    private FirebaseAuth auth;

    private static final int REQUEST_VIDEO_CODE =1000 ;
    Button btnShareLink;
//    btnSharePhoto, btnShareVideo;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if(ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                    startActivity(new Intent(Settings.this, LoginActivityFirebase.class));
                    finish();
                }
            }
        };
        btnShareLink = (Button)findViewById(R.id.btnShareLink);
//        btnSharePhoto = (Button)findViewById(R.id.btnSharePhoto);
//        btnShareVideo = (Button)findViewById(R.id.btnShareVideo);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(Settings.this, "Share Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Settings.this, "Share Cancel", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is a useful link")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }
            }
        });

//        btnSharePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//                    @Override
//                    public void onSuccess(Sharer.Result result) {
//                        Toast.makeText(Settings.this, "Share Successful", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText(Settings.this, "Share Cancel", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Toast.makeText(Settings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//                Picasso.with(getBaseContext())
//                        .load("http://upload.wikimedia.org/wikipedia/en/1/17/Batman-BenAffleck.jpg")
//                        .into(target);
//
////                                        Toast.makeText(Facebook.this, "Share Cancel", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        btnShareVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("video/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select video"), REQUEST_VIDEO_CODE);
//            }
//        });


        //We dont need this.


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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_VIDEO_CODE)
            {
                Uri selectedVideo = data.getData();

                ShareVideo video = new ShareVideo.Builder()
                        .setLocalUrl(selectedVideo)
                        .build();

                ShareVideoContent videoContent = new ShareVideoContent.Builder()
                        .setContentTitle("This is a useful video")
                        .setContentDescription("Youtube video")
                        .setVideo(video)
                        .build();

                if(shareDialog.canShow(ShareVideoContent.class))
                    shareDialog.show(videoContent);
            }
        }
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("ie.williamwall.autoreview", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
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
                Intent h= new Intent(Settings.this,HomeNavigation.class);
                startActivity(h);
                break;
            case R.id.nav_import:
                Intent i= new Intent(Settings.this,WeatherNavigation.class);
                startActivity(i);
                break;
            case R.id.nav_gallery:
                Intent g= new Intent(Settings.this,MapsNavigation.class);
                startActivity(g);
                break;
//            case R.id.nav_slideshow:
//                Intent s= new Intent(Settings.this,ShareNavigation.class);
//                startActivity(s);
            case R.id.nav_settings:
                Intent se= new Intent(Settings.this,Settings.class);
                startActivity(se);
                break;
            case R.id.nav_tools:
                Intent t= new Intent(Settings.this,AccountNavigation.class);
                startActivity(t);
                break;
            case R.id.nav_logout:
                auth.signOut();
// this listener will be called when there is change in firebase user session
                FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user == null) {
                            // user auth state is changed - user is null
                            // launch login activity
                            startActivity(new Intent(Settings.this, LoginActivityFirebase.class));
                            finish();
                        }
                    }
                };
                break;

            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
            //copy this now.
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
                startActivity(new Intent(Settings.this, LoginActivityFirebase.class));
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
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
//        userNameDisplay.setText(user.getEmail());
//        userNameDisplayNav.setText(user.getEmail());

    }
}
