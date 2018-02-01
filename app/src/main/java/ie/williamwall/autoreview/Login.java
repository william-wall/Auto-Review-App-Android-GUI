package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.jar.Attributes;

public class Login extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private Button register;
    private int counter = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.enterName);
        password = (EditText)findViewById(R.id.enterPassword);
        info = (TextView)findViewById(R.id.textView);
        login = (Button)findViewById(R.id.loginButton);
        register = (Button)findViewById(R.id.registerButton);


        info.setText("No of attempts remaining: 5");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString(), password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent move = new Intent(Login.this, Register.class);
                startActivity(move);
                System.out.println("MOVE TO REGISTER");
            }

        });

    }

    private void validate(String userName, String userPassword)
    {
        if ((userName.equals("admin"))&&(userPassword.equals("1234")))
        {
            Intent Intent = new Intent(Login.this, Welcome.class);
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
