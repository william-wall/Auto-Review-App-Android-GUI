package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.user.AdministrationUser;

public class HomeScreen extends AppCompatActivity {

    private Button administrator;
    private Button user;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        administrator = (Button)findViewById(R.id.admin);
        user = (Button)findViewById(R.id.user);
        userName = (TextView)findViewById(R.id.userName);

        final String message3 = getIntent().getStringExtra("message_key_user");
        userName.setText(message3);

        administrator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this, AdministrationReview.class);
                startActivity(move);
            }

        });
        user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(HomeScreen.this,AdministrationUser.class);
                startActivity(move);
            }

        });

    }

}
