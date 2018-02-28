package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import ie.williamwall.autoreview.review.AdministrationReview;
import ie.williamwall.autoreview.user.Register;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class Login extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info, usersName;
    private Button login;
    private Button register;
    private int counter = 5;
    private TextView currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.enterName);
        password = (EditText)findViewById(R.id.enterPassword);
        info = (TextView)findViewById(R.id.textView);
        login = (Button)findViewById(R.id.loginButton);
        register = (Button)findViewById(R.id.registerButton);
        currentTime = (TextView) findViewById(R.id.timeView);
        usersName = (TextView) findViewById(R.id.logName);
        final String message = getIntent().getStringExtra("message_key");
        final String message2 = getIntent().getStringExtra("message_key4");
        usersName.setText("Welcome "+message);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentTime.setText(currentDateTimeString);
        info.setText("No of attempts remaining: 5");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString(), password.getText().toString(), message, message2);
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(Login.this, Register.class);
                startActivity(move);
            }

        });
    }

    private void validate(String userName, String userPassword, String message, String message2)
    {
        if ( (userName.equals("admin"))&&(userPassword.equals("1234")) || (userName.equals(message))&&(userPassword.equals(message2)))
        {
            Intent Intent = new Intent(Login.this, AdministrationReview.class);
            startActivity(Intent);
        }
        else
        {
            counter--;

            info.setText("No of attempts remaining: "+String.valueOf(counter));

            if(counter==0)
            {
                login.setEnabled(false);
            }
            else
            {

            }
        }
    }
}
