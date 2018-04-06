package ie.williamwall.autoreview.firebaseReview;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ie.williamwall.autoreview.R;

/**
 * Created by william on 26/03/2018.
 */

public class MyAdapter extends ArrayAdapter<Person> {

    Activity activity;
    int resource;
    List<Person> list;

    public MyAdapter( Activity activity, int resource, List<Person> list) {
        super(activity, resource, list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.getImages);
        TextView name = (TextView) view.findViewById(R.id.getName);
        TextView email = (TextView) view.findViewById(R.id.getEmail);
        TextView userName = (TextView) view.findViewById(R.id.getUserName);
        TextView userTime = (TextView) view.findViewById(R.id.getUserTime);


        name.setText(list.get(position).getName());
        email.setText(list.get(position).getEmail());
        userName.setText(list.get(position).getUserName());
        userTime.setText(list.get(position).getUserTime());

        Glide.with(activity).load(list.get(position).getImageUri()).into(imageView);


        return view;
    }
}
