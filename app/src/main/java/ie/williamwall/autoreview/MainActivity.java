package ie.williamwall.autoreview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    EditText nameTxt;
    Button addBtn, updateBtn, deleteBtn, clearBtn;
    ArrayList<String>names=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.listViewMain);
        nameTxt=(EditText) findViewById(R.id.editText);
        addBtn=(Button) findViewById(R.id.add);
        updateBtn=(Button) findViewById(R.id.update);
        deleteBtn=(Button) findViewById(R.id.delete);
        clearBtn=(Button) findViewById(R.id.clear);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, names);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long id){
                nameTxt.setText(names.get(pos));
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             add();
            }

        });
        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                update();
            }

        });
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clear();
            }

        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                delete();
            }

        });
    }
    private void add()
    {
        String name = nameTxt.getText().toString();
        if(!name.isEmpty()&&name.length()>0)
        {
            adapter.add(name);

            adapter.notifyDataSetChanged();

            nameTxt.setText("");

            Toast.makeText(getApplicationContext(),"Added "+name,Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Nothing to add",Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        String name = nameTxt.getText().toString();
        int pos=lv.getCheckedItemPosition();
        if(!name.isEmpty()&&name.length()>0)
        {
            adapter.remove(names.get(pos));
            adapter.insert(name, pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(),"Updated " +name,Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(),"Nothing to update",Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(){
        int pos=lv.getCheckedItemPosition();

        if (pos > -1)
        {
            adapter.remove(names.get(pos));
            adapter.notifyDataSetChanged();
            nameTxt.setText("");
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Nothing to delete",Toast.LENGTH_SHORT).show();

        }
    }

    private void clear(){
        adapter.clear();
    }
}
