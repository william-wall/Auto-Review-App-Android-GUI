package ie.williamwall.autoreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ReviewDetail extends AppCompatActivity {

    private EditText title;
    private EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        title = (EditText) findViewById(R.id.titleDisplay);
        desc = (EditText) findViewById(R.id.descDisplay);
        String instanceTitle = getIntent().getStringExtra("sendTitle_key");
        String instanceDesc = getIntent().getStringExtra("sendDesc_key");
        title.setText(instanceTitle);
        desc.setText(instanceDesc);
    }
}
