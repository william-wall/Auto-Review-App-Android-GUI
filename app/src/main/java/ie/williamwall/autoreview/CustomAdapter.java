package ie.williamwall.autoreview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class CustomAdapter extends ArrayAdapter {

    Activity activity;
    int layout;
    ArrayList<Reviews>someReviews;

    public CustomAdapter(@NonNull Activity activity, int layout, @NonNull ArrayList<Reviews> someReviews) {
        super(activity, layout, someReviews);
        this.activity=activity;
        this.layout=layout;
        this.someReviews=someReviews;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout,null);
        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView editTitle = (TextView) convertView.findViewById(R.id.edit_title);
        TextView editDesc = (TextView) convertView.findViewById(R.id.edit_desc);
        avatar.setImageResource(someReviews.get(position).getAvatar());
        editTitle.setText(someReviews.get(position).getReviewTitle());
        editDesc.setText(someReviews.get(position).getReviewDesc());
        return convertView;
    }
}
