package ie.williamwall.autoreview.review;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ie.williamwall.autoreview.R;

public class ReviewDetail extends AppCompatActivity {

    private EditText title;
    private EditText desc;
    private Button update;

//    public ArrayList<Review> someReviews = <<AdministrationReview>>.getSomeReviews();

//    public ArrayList<Review> someReviews = AdministrationReview.getInstance().getFrozen();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        title = (EditText) findViewById(R.id.titleDisplay);
        desc = (EditText) findViewById(R.id.descDisplay);
        update = (Button) findViewById(R.id.updateBtn);
        String instanceTitle = getIntent().getStringExtra("sendTitle_key");
        String instanceDesc = getIntent().getStringExtra("sendDesc_key");
        title.setText(instanceTitle);
        desc.setText(instanceDesc);






        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window();

//                String changedTitle = title.getText().toString();
//                String changedDesc = desc.getText().toString();
//                Intent move = new Intent(ReviewDetail.this, AdministrationReview.class);
//                move.putExtra("sendBackTitle_key", changedTitle);
//                move.putExtra("sendBackDesc_key", changedDesc);
//                startActivity(move);


            }
        });



    }
    public void window()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}
