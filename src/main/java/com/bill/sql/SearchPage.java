package com.bill.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchPage extends AppCompatActivity {

    Button b;
    EditText  edit;

    SQLiteDatabase db;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        b = (Button) findViewById(R.id.btnSearch);
        edit = (EditText) findViewById(R.id.edtSearch);
        list = (ListView) findViewById(R.id.lstview);

        db = openOrCreateDatabase("ClassDB", Context.MODE_PRIVATE,null);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT * FROM students WHERE rollno = '"+edit.getText()+"' ",null);



                //check for any record
                if (c.getCount()==0)
                {
                    showmessage("Sorry","no records found");
                }
                if (c.moveToFirst()) {

                    StringBuffer buffer=new StringBuffer();

                    buffer.append(c.getString(0)+"\n"+c.getString(1)+"\n"+c.getString(2)+"\n#");

                    String[] ar=buffer.toString().split("#");

                    ArrayAdapter<String> ad = new ArrayAdapter<String>(getBaseContext(),R.layout.support,ar);

                    list.setAdapter(ad);

                    //results.setText(c.getString(0)+"\n"+c.getString(1)+"\n"+c.getString(2));


                }


                else{
                    showmessage("Error", "No Matching Record");
                }

            }
        });



        }

    public void showmessage(String title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

}

}
