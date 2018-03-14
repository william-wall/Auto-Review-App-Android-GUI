package ie.williamwall.autoreview.notInUse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ie.williamwall.autoreview.R;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class SuccessfulRegistration extends AppCompatActivity {

    private Button continueButton;
    private TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_registration);
        String message = getIntent().getStringExtra("message_key");
        continueButton = (Button)findViewById(R.id.continueButton);
        messageTextView = (TextView) findViewById(R.id.usersName);
        messageTextView.setText(message);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(SuccessfulRegistration.this, MainMenuUser.class);
                startActivity(Intent);
            }
        });
    }
}


