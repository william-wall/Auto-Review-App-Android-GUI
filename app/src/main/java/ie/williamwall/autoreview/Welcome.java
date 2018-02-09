package ie.williamwall.autoreview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class Welcome extends AppCompatActivity {

    ListView mListView;
    int[] images = {R.drawable.car1,
            R.drawable.car2,
            R.drawable.car3,
            R.drawable.car4,
            R.drawable.car5,
            R.drawable.car6,
            R.drawable.car7,
            R.drawable.car8,
            R.drawable.car9,
            R.drawable.car10};
    String[] users = {"User 1", "User 2", "User 3", "User 4", "User 5", "User 6", "User 7", "User 8", "User 9", "User 10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        Register myClass = new Register();
//        String aName = myClass.name;
//        System.out.print(aName);
        mListView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView mImageView = (ImageView) view.findViewById(R.id.imageView);
            final TextView mTextView = (TextView) view.findViewById(R.id.textView);
            mImageView.setImageResource(images[position]);
            mTextView.setText(users[position]);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    Log.d("MyApp", "I am here");
                    Intent move = new Intent(Welcome.this, Vehicle.class);
                    startActivity(move);
                }
            });
            return view;
        }
    }
}
