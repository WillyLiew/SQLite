package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //TextView output;  // for Part 1
    TextView textView;
    ListView output;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.output);
        textView = findViewById(R.id.textView);
        // Save at data/data/<package>/databases
        db = new DBHelper(this);
        /*
        db.insertData("Malaysia");  // add new data
        db.insertData("Singapore");
        db.insertData("Indonesia");
        db.insertData("Brunei");
        db.updateData(1, "Germany"); // update data of specified recID
        db.deleteData(4); //delete data of specified recID
         */
        Cursor cursor = db.readData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }
        /* Part 1: display with textView
        else {
            StringBuilder sb = new StringBuilder();
            while (cursor.moveToNext()) {
                sb.append(cursor.getString(0) + ":" + cursor.getString(1) + "\n");
            }
            output.setText(sb.toString());
        }*/
        // Part 2: display with row.xml and listView
        else {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                    R.layout.row, cursor,
                    new String[]{"recID", "recContent"},
                    new int[]{R.id.recID, R.id.recContent},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            output.setAdapter(adapter);
            output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String toast = ((Cursor) parent.getItemAtPosition(position)).getString(2);
                    Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Part 3: content provider
        // java-><package>->rightClick->New->Other->Content Provider
        // Ensure the uri content provided is constant
        Cursor cp=getContentResolver().query(
                Uri.parse("content://com.example.sqlite.provider"),
                new String[]{"recID","recContent"},null,null,"recID");
        if(cp.getCount()==0){
            Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }
        else{
            cp.moveToFirst();
            StringBuilder sb=new StringBuilder();
            while(!cp.isAfterLast()){
                sb.append(cp.getString(0)+":"+cp.getString(1)+"\n");
                cp.moveToNext();
            }
            textView.setText(sb.toString());
        }

        // Part 4: External (3rd party app) Content Provider

    }
}
