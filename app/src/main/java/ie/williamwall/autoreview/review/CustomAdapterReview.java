package ie.williamwall.autoreview.review;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ie.williamwall.autoreview.R;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

public class CustomAdapterReview extends ArrayAdapter {

    Activity activity;
    int layout;
    ArrayList<Review> someReviews;

    public CustomAdapterReview(@NonNull Activity activity, int layout, @NonNull ArrayList<Review> someReviews) {
        super(activity, layout, someReviews);
        this.activity = activity;
        this.layout = layout;
        this.someReviews = someReviews;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout, null);
        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView editTitle = (TextView) convertView.findViewById(R.id.edit_title);
        TextView editDesc = (TextView) convertView.findViewById(R.id.edit_desc);
        TextView time = (TextView) convertView.findViewById(R.id.timeStamp);
        avatar.setImageResource(someReviews.get(position).getAvatar());
        editTitle.setText(someReviews.get(position).getReviewTitle());
        editDesc.setText(someReviews.get(position).getReviewDesc());
        time.setText(someReviews.get(position).getTimeStamp());
        return convertView;
    }
}
