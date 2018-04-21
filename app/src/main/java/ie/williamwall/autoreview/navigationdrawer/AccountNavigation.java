package ie.williamwall.autoreview.navigationdrawer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebaseAdministrator.LoginActivityFirebase;
import ie.williamwall.autoreview.firebaseAdministrator.SignupActivityFirebase;
import ie.williamwall.autoreview.home.HomeScreen;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.review.AdministrationReview;

public class AccountNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;

    private Button btnChangePassword, btnRemoveUser, btnContinue,
            changePassword, remove, signOut;
    private TextView email;
    private EditText oldEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
// its a good pratice to clean and rebuild your project now and then.
    // now lets run our app.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        auth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.useremail);
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AccountNavigation.this, LoginActivityFirebase.class));
                    finish();
                }
            }
        };
        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        changePassword = (Button) findViewById(R.id.changePass);
        btnContinue = (Button) findViewById(R.id.continueMain);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);
        oldEmail = (EditText) findViewById(R.id.old_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);
        oldEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                password.setVisibility(View.GONE);
                newPassword.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String sendingUsername = user.getEmail();
                Intent Intent = new Intent(AccountNavigation.this, HomeNavigation.class);
//                Intent.putExtra("message_key_user", sendingUsername);
                startActivity(Intent);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AccountNavigation.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(AccountNavigation.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountNavigation.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AccountNavigation.this, "Your profile is deleted! Create a account now!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AccountNavigation.this, SignupActivityFirebase.class));
                                                finish();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(AccountNavigation.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
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
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView userNameDisplayNav = (TextView) headerView.findViewById(R.id.usersNameNav);
        final FirebaseUser userNav = FirebaseAuth.getInstance().getCurrentUser();
        String gotNameNav = getDataToView(userNav);
        userNameDisplayNav.setText(gotNameNav);
    }



    @SuppressLint("SetTextI18n")
    private String getDataToView(FirebaseUser user) {
        String jjjj = user.getEmail();
        return jjjj;
    }
    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        email.setText("Email: " + user.getEmail());

    }


    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(AccountNavigation.this, LoginActivityFirebase.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();
// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AccountNavigation.this, LoginActivityFirebase.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

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

    public void sendUser(FirebaseUser user) {
        String sendingUsername = user.getEmail();
        Intent Intent = new Intent(AccountNavigation.this, AdministrationReview.class);
        Intent.putExtra("message_key", sendingUsername);
        startActivity(Intent);
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
                Intent h= new Intent(AccountNavigation.this,HomeNavigation.class);
                startActivity(h);
                break;
            case R.id.nav_import:
                Intent i= new Intent(AccountNavigation.this,WeatherNavigation.class);
                startActivity(i);
                break;
            case R.id.nav_gallery:
                Intent g= new Intent(AccountNavigation.this,MapsActivity.class);
                startActivity(g);
                break;
//            case R.id.nav_slideshow:
//                Intent s= new Intent(AccountNavigation.this,ShareNavigation.class);
//                startActivity(s);
            case R.id.nav_settings:
                Intent se= new Intent(AccountNavigation.this,Settings.class);
                startActivity(se);
                break;
            case R.id.nav_tools:
                Intent t= new Intent(AccountNavigation.this,AccountNavigation.class);
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
                            startActivity(new Intent(AccountNavigation.this, LoginActivityFirebase.class));
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


}
