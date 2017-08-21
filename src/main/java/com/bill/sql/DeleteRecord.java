package com.bill.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DeleteRecord extends AppCompatActivity {

    SQLiteDatabase db;
    EditText text;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);

        text = (EditText) findViewById(R.id.editdelete);
        delete = (Button) findViewById(R.id.btnDelete);

        db = openOrCreateDatabase("ClassDB", Context.MODE_PRIVATE,null);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (text.getText().toString().trim().length()==0)
                {
                    showmessage("Error", "Type your roll number");
                }

                Cursor cursor = db.rawQuery("SELECT *  FROM students WHERE rollno='"+text.getText()+"'", null);

                if (cursor.moveToFirst()){

                    db.execSQL("DELETE FROM students WHERE rollno = '"+text.getText()+"'");
                    showmessage("Deleted", "Record deleted susseccfully");

                };
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
