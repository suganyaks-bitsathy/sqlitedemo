package com.example.sqlite;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editname,editsurname,editmarks,editid;
    Button btnadddata;
    Button viewalldata;
    Button updatedata;
    Button deletedata;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHelper(this);
        editname=(EditText)findViewById(R.id.editText);
        editsurname=(EditText)findViewById(R.id.editText2);
        editmarks=(EditText)findViewById(R.id.editText3);
        editid=(EditText)findViewById(R.id.editText4);
        btnadddata=(Button)findViewById(R.id.button_add);
        AddData();
        viewalldata=(Button)findViewById(R.id.button2);
        viewall();
        updatedata=(Button)findViewById(R.id.button3);
        Updatadata();
        deletedata =(Button)findViewById(R.id.button4);
        Deletedata();
        clear=(Button)findViewById(R.id.button5);
        clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editid.setText("");
                        editname.setText("");
                        editsurname.setText("");
                        editmarks.setText("");

                    }
                }
        );

    }

    public void Deletedata()
    {
        deletedata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Integer rows=db.deletedata(editid.getText().toString());
                    if(rows>0)
                    {
                        Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
                    }

                    }
                }
        );
    }

    public void Updatadata()
    {
        updatedata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isupdated=db.updatedata(editid.getText().toString(),editname.getText().toString(),editsurname.getText().toString(),editmarks.getText().toString());
                        if(isupdated)
                        {
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }
    public void AddData()
    {
        btnadddata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       boolean isInserted= db.insertdata(editname.getText().toString(),editsurname.getText().toString(),editmarks.getText().toString());
                       if(isInserted)
                       {
                           Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                       }

                    }
                }
        );
    }

    public void viewall()
    {
        viewalldata.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor cs= db.getAlldata();
                        if(cs.getCount()==0)
                        {
                            showMessage("Error!","No data to retrieve");
                            return;
                        }
                       StringBuffer sb=new StringBuffer();
                        while(cs.moveToNext())
                        {
                            sb.append("ID: "+cs.getString(0)+"\n");
                            sb.append("NAME: "+cs.getString(1)+"\n");
                            sb.append("LNAME: "+cs.getString(2)+"\n");
                            sb.append("MARKS: "+cs.getString(3)+"\n");

                        }
                        showMessage("Data!",sb.toString());

                    }
                }
        );
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
