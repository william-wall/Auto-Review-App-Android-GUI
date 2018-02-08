package ie.williamwall.autoreview;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    int id =-1;
    ListView mainList;
    EditText editTextTitle;
    EditText editTextDesc;
    Button addButton, editButton, clearButton, saveButton;
    ArrayList<Reviews> someReviews;
    CustomAdapter myAdapter;
   ArrayAdapter<Reviews> adapter;
    SearchView sv;

    private void init(){
        mainList = (ListView) findViewById(R.id.list_cars);
        editTextTitle = (EditText) findViewById(R.id.edit_title);
        editTextDesc = (EditText) findViewById(R.id.edit_desc);
        addButton = (Button) findViewById(R.id.add);
        editButton = (Button) findViewById(R.id.edit);
        clearButton = (Button) findViewById(R.id.clear);
        saveButton = (Button) findViewById(R.id.save);
        sv = (SearchView) findViewById(R.id.searchCar);

//        fillData();





        addButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

//        FileInputStream fis;
//        try {
//            fis = openFileInput("CalEvents");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            ArrayList<Reviews> returnlist = (ArrayList<Reviews>) ois.readObject();
//            ois.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        File file = new File(context.getFilesDir(), someReviews);


//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            try {
//                // create a file in downloads directory
//                FileOutputStream fos =
//                        new FileOutputStream(
//                                new File(Environment.getExternalStoragePublicDirectory(
//                                        Environment.DIRECTORY_DOWNLOADS), "name.ser")
//                        );
//                ObjectOutputStream os = new ObjectOutputStream(fos);
//                os.writeObject(someReviews);
//                os.close();
//                Log.v("MyApp","File has been written");
//            } catch(Exception ex) {
//                ex.printStackTrace();
//                Log.v("MyApp","File didn't write");
//            }
//        }

//                        readArrayListFromFile();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        readArrayListFromFile();


        init();

        loadData();





        myAdapter = new CustomAdapter(this, R.layout.item_layout, someReviews);
        mainList.setAdapter(myAdapter);






        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                id = position;
                editTextTitle.setText(someReviews.get(position).getReviewTitle());
                editTextDesc.setText(someReviews.get(position).getReviewDesc());




            }
        });

        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                someReviews.remove(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();

                return false;
            }
        });

//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });



//        sv.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //perform your click operation here
//                searchViewTitle();
//            }
//        });






    }


public void searchViewTitle()
{
    // SEARCH
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

    //        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Toast.makeText(MainMenu.this, adapter.getItem(i).getReviewTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });

}







    public void writeArrayListToFile() { // file handling - write to file - this will execute when user presses exit option


        try {
            File f = new File("carData.dat");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(someReviews);

//            JOptionPane.showMessageDialog(null,"File handling - Written to file!");
            oos.close();
            fos.close();

        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
//            Toast.makeText(this, "write list error", Toast.LENGTH_SHORT).show();
        }
    }

    public void readArrayListFromFile() { // file handling - read from file

        try {
            File f = new File("carData.dat");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            someReviews = (ArrayList<Reviews>) ois.readObject();

//            JOptionPane.showMessageDialog(null,"File handling - Read from file!");
            ois.close();
            fis.close();

        } catch (Exception e) {
//            Toast.makeText(this, "read list error", Toast.LENGTH_SHORT).show();

            // System.out.println("Error: " + e.getMessage());
            someReviews = new ArrayList<>();
        }

    }




    //    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:




                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();
                String stringTitle = editTextTitle.getText().toString();
                String stringDesc = editTextDesc.getText().toString();
                Reviews temp = new Reviews(R.mipmap.ic_launcher, stringTitle, stringDesc);
                someReviews.add(temp);

                myAdapter.notifyDataSetChanged();








//                        writeArrayListToFile();

                break;
            case R.id.edit:
                String stringEditTitle = editTextTitle.getText().toString();
                String stringEditDesc = editTextDesc.getText().toString();
                Reviews tempEdit = new Reviews(R.mipmap.ic_launcher, stringEditTitle, stringEditDesc);
                someReviews.set(id, tempEdit);
                id = -1;
                myAdapter.notifyDataSetChanged();
                break;



            case R.id.clear:
//                someReviews.remove(position);
//                myadapter.notifyDataSetChanged();
//                Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();



//                String stringDeleteTitle = editTextTitle.getText().toString();
//                String stringDeleteDesc = editTextDesc.getText().toString();
//                Reviews tempDelete = new Reviews(R.mipmap.ic_launcher, stringDeleteTitle, stringDeleteDesc);
//
//                tempDelete = null;



//                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        someReviews.remove(position);
//                        myadapter.notifyDataSetChanged();
//                        Toast.makeText(MainMenu.this, "clear", Toast.LENGTH_SHORT ).show();
//
//
//                    }
//                });


                break;


            case R.id.save:


                Toast.makeText(this, "Click Add button", Toast.LENGTH_SHORT).show();

                saveData();
                break;

        }


    }
    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(someReviews);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData()
    {
        someReviews=new ArrayList<Reviews>();
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Toyota", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Honda", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        someReviews.add(new Reviews(R.mipmap.ic_launcher_round, "Audi", "This is a really fast car and it can go really fast " +
                "so be very careful what way you drive it for god sake"));
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Reviews>>() {}.getType();
        someReviews = gson.fromJson(json, type);

        if(someReviews == null)
        {
            someReviews = new ArrayList<>();
        }

    }

}
