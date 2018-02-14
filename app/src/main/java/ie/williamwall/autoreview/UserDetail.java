package ie.williamwall.autoreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class UserDetail extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        name = (EditText) findViewById(R.id.nameDisplay);
        email = (EditText) findViewById(R.id.emailDisplay);
        phone = (EditText) findViewById(R.id.phoneDisplay);
        password = (EditText) findViewById(R.id.passwordDisplay);
        String instanceName = getIntent().getStringExtra("sendName_key");
        String instanceEmail = getIntent().getStringExtra("sendEmail_key");
        String instancePhone = getIntent().getStringExtra("sendPhone_key");
        String instancePassword = getIntent().getStringExtra("sendPassword_key");
        name.setText(instanceName);
        email.setText(instanceEmail);
        phone.setText(instancePhone);
        password.setText(instancePassword);
    }

}
