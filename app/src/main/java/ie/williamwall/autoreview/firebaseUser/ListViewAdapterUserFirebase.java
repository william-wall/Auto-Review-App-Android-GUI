package ie.williamwall.autoreview.firebaseUser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ie.williamwall.autoreview.R;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ https://github.com/william-wall/Auto-Review-App-Android-GUI

public class ListViewAdapterUserFirebase extends BaseAdapter {

    Activity activity;
    List<UserInstanceFirebase> lstUsers;
    LayoutInflater inflater;

    public ListViewAdapterUserFirebase(Activity activity, List<UserInstanceFirebase> lstUsers) {
        this.activity = activity;
        this.lstUsers = lstUsers;
    }

    @Override
    public int getCount() {
        return lstUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return lstUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.user_listview_item,null);

        TextView txtUser = (TextView) itemView.findViewById(R.id.list_name);
        TextView txtEmail = (TextView) itemView.findViewById(R.id.list_email);

        txtUser.setText(lstUsers.get(i).getName());
        txtEmail.setText(lstUsers.get(i).getEmail());

        return itemView;

    }
}
