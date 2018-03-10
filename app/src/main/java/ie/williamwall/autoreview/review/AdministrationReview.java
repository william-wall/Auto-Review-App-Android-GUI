package ie.williamwall.autoreview.review;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import ie.williamwall.autoreview.R;
import ie.williamwall.autoreview.firebase.LoginActivityFirebase;
import ie.williamwall.autoreview.maps.MapsActivity;
import ie.williamwall.autoreview.misc.AdministrationUserOld;
import ie.williamwall.autoreview.misc.Register;
import ie.williamwall.autoreview.user.AdministrationUser;
import ie.williamwall.autoreview.user.User;
import ie.williamwall.autoreview.weather.Weather;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class AdministrationReview extends AppCompatActivity {

    int id = -1;
    ListView mainList;
    private TextView userLoginName;
    ArrayList<Review> someReviews;
    CustomAdapterReview myAdapter;
    ArrayAdapter<Review> adapter;
    SearchView sv;

    private void init() {
        mainList = (ListView) findViewById(R.id.list_cars);
        sv = (SearchView) findViewById(R.id.searchCar);
        userLoginName = (TextView) findViewById(R.id.userLoginDisplay);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWindow();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationReview.this);
//                alert.setTitle("Add New Review");
//                LinearLayout layout = new LinearLayout(AdministrationReview.this);
//                layout.setOrientation(LinearLayout.VERTICAL);
//                final EditText title = new EditText(AdministrationReview.this);
//                title.setHint("Review Title, E.G. Opel Vectra 1.6 16v");
//                layout.addView(title);
//                final EditText desc = new EditText(AdministrationReview.this);
//                desc.setHint("Review Description, E.G. Terrible fuel consumption...");
//                layout.addView(desc);
//                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                        String stringEditTitle = title.getText().toString();
//                        String stringEditDesc = desc.getText().toString();
//                        Review temp = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
//                        someReviews.add(temp);
//                        myAdapter.notifyDataSetChanged();
//                        saveData();
//                        Toast.makeText(AdministrationReview.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        Toast.makeText(AdministrationReview.this, "Cancel", Toast.LENGTH_LONG).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
//                        builder.setTitle("Confirm");
//                        builder.setMessage("Are you sure?");
//                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                        builder.show();
//                    }
//                });
//                alert.setView(layout);
//                alert.show();
            }
        });
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        init();
        loadData();
        myAdapter = new CustomAdapterReview(this, R.layout.item_layout_administration_review, someReviews);
        mainList.setAdapter(myAdapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String titleInstance = "";
                String descInstance = "";
                id = position;
                titleInstance = someReviews.get(position).getReviewTitle();
                descInstance = someReviews.get(position).getReviewDesc();
                crudWindow(position);
            }
        });
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                someReviews.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(AdministrationReview.this, "Deleted Review", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void searchViewTitle() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, someReviews);
        mainList.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AdministrationReview.this, adapter.getItem(i).getReviewTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_review);
        item.setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logged Off", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, LoginActivityFirebase.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_weather) {
            Toast.makeText(this, "Weather Report", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, Weather.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_location) {
            Toast.makeText(this, "Current Location", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, MapsActivity.class);
            startActivity(Intent);
            return true;
        }
        if (id == R.id.action_review) {
            Toast.makeText(this, "Administration Review", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_user) {
            Toast.makeText(this, "Administration User", Toast.LENGTH_SHORT).show();
            Intent Intent = new Intent(AdministrationReview.this, AdministrationUser.class);
            startActivity(Intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(someReviews);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        someReviews = new ArrayList<Review>();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Review>>() {
        }.getType();
        someReviews = gson.fromJson(json, type);
        if (someReviews == null) {
            someReviews = new ArrayList<>();
        }
    }
    public boolean validate(String stringEditTitle, EditText mTitle, String stringEditDesc, EditText mDesc) {
        boolean valid = true;
        if (stringEditTitle.isEmpty() || stringEditTitle.length() < 5) {
            mTitle.setError("Title must be at least 5 characters!");
            valid = false;
        }
        if (stringEditDesc.isEmpty() || stringEditDesc.length() < 20) {
            mDesc.setError("Review must be at least 20 characters!");
            valid = false;
        }
        if (stringEditTitle.isEmpty() || stringEditTitle.length() < 5 || stringEditDesc.isEmpty() || stringEditDesc.length() < 20) {
            valid = false;
        }
        return valid;
    }

    public void addWindow() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationReview.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_review, null);
        final EditText mTitle = (EditText) mView.findViewById(R.id.newTitle);
        final EditText mDesc = (EditText) mView.findViewById(R.id.newDesc);
        mBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringEditTitle = mTitle.getText().toString();
                String stringEditDesc = mDesc.getText().toString();
                if (!validate(stringEditTitle, mTitle, stringEditDesc, mDesc)) {
                    Toast.makeText(AdministrationReview.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Review temp = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                    someReviews.add(temp);
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationReview.this, "Added Sucessfully", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    public int crudWindow(int position) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdministrationReview.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_crud_screen_review, null);
        final EditText mTitle = (EditText) mView.findViewById(R.id.newTitle);
        final EditText mDesc = (EditText) mView.findViewById(R.id.newDesc);
        id = position;
        mTitle.setText(someReviews.get(position).getReviewTitle());
        mDesc.setText(someReviews.get(position).getReviewDesc());
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String stringEditTitle = mTitle.getText().toString();
                String stringEditDesc = mDesc.getText().toString();
                if (!validate(stringEditTitle, mTitle, stringEditDesc, mDesc)) {
                    Toast.makeText(AdministrationReview.this, "You have incorrect field data, look at errors!", Toast.LENGTH_LONG).show();
                } else {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                    someReviews.set(id, tempEdit);
                    id = -1;
                    myAdapter.notifyDataSetChanged();
                    saveData();
                    Toast.makeText(AdministrationReview.this, "Updated Sucessfully", Toast.LENGTH_LONG).show();
                }
            }
        });
        mBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdministrationReview.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String stringEditTitle = mTitle.getText().toString();
                        String stringEditDesc = mDesc.getText().toString();
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
                        someReviews.set(id, tempEdit);
                        id = -1;
                        someReviews.remove(tempEdit);
                        myAdapter.notifyDataSetChanged();
                        saveData();
                        Toast.makeText(AdministrationReview.this, "Deleted User", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        mBuilder.setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addWindow();
            }
        });
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        return position;
    }

//    public int window(int position) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationReview.this);
//        alert.setTitle("Administer Review");
//        LinearLayout layout = new LinearLayout(AdministrationReview.this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        final EditText title = new EditText(AdministrationReview.this);
//        title.setHint("Review Title, E.G. Opel Vectra 1.6 16v");
//        layout.addView(title);
//        final EditText desc = new EditText(AdministrationReview.this);
//        desc.setHint("Review Description, E.G. Terrible fuel consumption...");
//        layout.addView(desc);
//        id = position;
//        title.setText(someReviews.get(position).getReviewTitle());
//        desc.setText(someReviews.get(position).getReviewDesc());
//        alert.setView(layout);
//        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String stringEditTitle = title.getText().toString();
//                String stringEditDesc = desc.getText().toString();
//                Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
//                someReviews.set(id, tempEdit);
//                id = -1;
//                myAdapter.notifyDataSetChanged();
//                Toast.makeText(AdministrationReview.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
//                saveData();
//            }
//        });
//        alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String stringEditTitle = title.getText().toString();
//                String stringEditDesc = desc.getText().toString();
//                Review tempEdit = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
//                someReviews.set(id, tempEdit);
//                id = -1;
//                someReviews.remove(tempEdit);
//                myAdapter.notifyDataSetChanged();
//                saveData();
//                Toast.makeText(AdministrationReview.this, "Deleted Review", Toast.LENGTH_SHORT).show();
//            }
//        });
//        alert.setNeutralButton("New", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                AlertDialog.Builder alert = new AlertDialog.Builder(AdministrationReview.this);
//                alert.setTitle("Administer Review");
//                LinearLayout layout = new LinearLayout(AdministrationReview.this);
//                layout.setOrientation(LinearLayout.VERTICAL);
//                final EditText title = new EditText(AdministrationReview.this);
//                title.setHint("Review Title, E.G. Opel Vectra 1.6 16v");
//                layout.addView(title);
//                final EditText desc = new EditText(AdministrationReview.this);
//                desc.setHint("Review Description, E.G. Terrible fuel consumption...");
//                layout.addView(desc);
//                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                        String stringEditTitle = title.getText().toString();
//                        String stringEditDesc = desc.getText().toString();
//                        Review temp = new Review(R.mipmap.caricon, stringEditTitle, stringEditDesc, currentDateTimeString);
//                        someReviews.add(temp);
//                        myAdapter.notifyDataSetChanged();
//                        saveData();
//                        Toast.makeText(AdministrationReview.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
//                    }
//                });
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                    }
//                });
//                alert.setView(layout);
//                alert.show();
//            }
//        });
//        alert.show();
//        return position;
//    }
}
