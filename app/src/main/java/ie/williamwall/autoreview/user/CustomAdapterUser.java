package ie.williamwall.autoreview.user;

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
// GitHub @ github.com/william-wall

public class CustomAdapterUser extends ArrayAdapter {

    Activity activity;
    int layout;
    ArrayList<User>users;

    public CustomAdapterUser(@NonNull Activity activity, int layout, @NonNull ArrayList<User> users) {
        super(activity, layout, users);
        this.activity=activity;
        this.layout=layout;
        this.users=users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(layout,null);
        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView name = (TextView) convertView.findViewById(R.id.instanceName);
        TextView email = (TextView) convertView.findViewById(R.id.instanceEmail);
        TextView phone = (TextView) convertView.findViewById(R.id.instancePhone);
        TextView password = (TextView) convertView.findViewById(R.id.instancePassword);
        avatar.setImageResource(users.get(position).getAvatar());
        name.setText(users.get(position).getName());
        email.setText(users.get(position).getEmail());
        phone.setText(users.get(position).getPhone());
        password.setText(users.get(position).getPassword());
        return convertView;
    }
}
