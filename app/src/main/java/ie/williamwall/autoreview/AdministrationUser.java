package ie.williamwall.autoreview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class AdministrationUser extends AppCompatActivity {

    ListView lv;
    EditText nameTxt;
    Button addBtn, updateBtn, deleteBtn, clearBtn,saveBtn;
    ArrayList<String> names = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_user);
        loadData();
        lv = (ListView) findViewById(R.id.listViewMain);
        nameTxt = (EditText) findViewById(R.id.editText);
        addBtn = (Button) findViewById(R.id.add);
        updateBtn = (Button) findViewById(R.id.update);
        deleteBtn = (Button) findViewById(R.id.delete);
        clearBtn = (Button) findViewById(R.id.clear);
        saveBtn = (Button) findViewById(R.id.save);
        final String message = getIntent().getStringExtra("message_key");
        names.add(message);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id) {
                nameTxt.setText(names.get(pos));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }

        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }

        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }

        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }

        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(AdministrationUser.this, "Long Click!", Toast.LENGTH_SHORT).show();
//                Intent move = new Intent(AdministrationUser.this, Splash.class);
//                startActivity(move);
//                return false;
//            }
//        });
    }



    private void add() {
        String name = nameTxt.getText().toString();
        if (!name.isEmpty() && name.length() > 0) {
            adapter.add(name);
            adapter.notifyDataSetChanged();
            nameTxt.setText("");
            saveData();
            Toast.makeText(getApplicationContext(), "Added " + name, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to add", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        String name = nameTxt.getText().toString();
        int pos = lv.getCheckedItemPosition();
        if (!name.isEmpty() && name.length() > 0) {
            adapter.remove(names.get(pos));
            adapter.insert(name, pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Updated " + name, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Nothing to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {
        int pos = lv.getCheckedItemPosition();
        if (pos > -1) {
            adapter.remove(names.get(pos));
            adapter.notifyDataSetChanged();
            nameTxt.setText("");
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void clear() {
        adapter.clear();
    }


    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(names);
        editor.putString("task list2", json);
        editor.apply();
    }

    private void loadData() {
        names = new ArrayList<String>();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences2", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list2", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        names = gson.fromJson(json, type);
        if (names == null) {
            names = new ArrayList<>();
        }
    }
}