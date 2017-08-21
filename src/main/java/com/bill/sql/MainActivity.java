package com.bill.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editRoll, editNme, editMarks;
    Button btnAdd, btnVw, btnDel,btnSearch;

    //create a varriable db
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editRoll = (EditText) findViewById(R.id.edtRollno);
        editNme  = (EditText) findViewById(R.id.edtName);
        editMarks = (EditText) findViewById(R.id.edtMarks);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnVw = (Button) findViewById(R.id.btnView);
        btnDel = (Button) findViewById(R.id.btnDelete);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        //method for creating database(create or view DB)
        db = openOrCreateDatabase("ClassDB", Context.MODE_PRIVATE,null);

        //THIS IS A QUERY FOR CREATING A TABLE WITH THREE COLUMNS(rollno, name and marks)
        db.execSQL("CREATE TABLE IF NOT EXISTS students(rollno VARCHAR, name VACHAR, marks VARCHAR);");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  page = new Intent(MainActivity.this,SearchPage.class);
                startActivity(page);
            }
        });

        //setting an on click listener on the button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //check if user has typed anything
                if(editRoll.getText().toString().trim().length()==0){
                    showmessage("ROLL NO ERROR", "PLEASE ENTER ROLL NUMBER");
                }
                else if (editNme.getText().toString().trim().length()==0){
                    showmessage("NAME ERROR","PLEASE ENTER NAME ");
                }
                else if (editMarks.getText().toString().trim().length()==0){
                    showmessage("MARKS ERROR","PLEASE ENTER MARKS");
                }

                else {
                    //ISERTING DATA IN DB
                    db.execSQL("INSERT INTO students VALUES('"+editRoll.getText()+"','"+editNme.getText()+"','"+editMarks.getText()+"');");
                    showmessage("SUCCESS", "Data was saved successfully");
                    clear();
                }


            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(MainActivity.this,DeleteRecord.class);
                startActivity(b);
            }

        });
        btnVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT * FROM students ",null);

                //check for any record
                if (c.getCount()==0)
                {
                    showmessage("Sorry","no records found");
                }

                //Use buffer to appent th records
                StringBuffer buffer= new StringBuffer();
                while (c.moveToNext())
                {
                    buffer.append("Roll Number: "+ c.getString(0));
                    buffer.append("\n");
                    buffer.append("Student Name:"+c.getString(1));
                    buffer.append("\n");
                    buffer.append("Student Marks:"+c.getString(2));
                    buffer.append("\n---------------\n");
                }

                showmessage("STUDENT RECORDS", buffer.toString());

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
    public void clear(){
        editRoll.setText("");
        editMarks.setText("");
        editNme.setText("");

    }
}
