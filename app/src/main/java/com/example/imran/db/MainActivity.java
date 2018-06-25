package com.example.imran.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SQLiteDatabase sqLiteDatabase;
    EditText etSearch,etName,etPass;
    Button update,insert,selectall;
    String search,name,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabase=openOrCreateDatabase("empdb",MODE_PRIVATE,null);

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS emp(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(255),pass VARCHAR(255));");

        etSearch=(EditText)findViewById(R.id.etSearch);
        etName=(EditText)findViewById(R.id.etName);
        etPass=(EditText)findViewById(R.id.etPass);
        update=(Button)findViewById(R.id.updateb);
        insert=(Button)findViewById(R.id.insertb);
        selectall=(Button)findViewById(R.id.selectallb);

        update.setOnClickListener(this);
        insert.setOnClickListener(this);
        selectall.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.insertb)
        {
            name=etName.getText().toString().trim();
            pass=etPass.getText().toString().trim();

            if (name.equals("")||pass.equals(""))
            {
                Toast.makeText(this,"all fields are compulsory",Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                sqLiteDatabase.execSQL("INSERT INTO emp(name,pass)VALUES('"+name+"','"+pass+"')");
                Toast.makeText(this,"RECORD SAVED",Toast.LENGTH_LONG).show();
            }
        }
        else  if (v.getId()==R.id.selectallb)
        {
            Cursor cursorselectall=sqLiteDatabase.rawQuery("SELECT *FROM emp",null);
            if (cursorselectall.getCount()==0)
            {
                Toast.makeText(this,"database is empty",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while (cursorselectall.moveToFirst())
            {
                buffer.append("emp name :"+cursorselectall.getString(0));
                buffer.append("emp pass :"+cursorselectall.getString(1));
            }
            Toast.makeText(this,buffer.toString(),Toast.LENGTH_LONG).show();
        }
        else if (v.getId()==R.id.updateb)
        {
            search=etSearch.getText().toString().trim();
            name=etName.getText().toString().trim();
            pass=etPass.getText().toString().trim();

            if (search.equals(""))
            {
                Toast.makeText(this,"enter name for update record",Toast.LENGTH_LONG).show();
                return;
            }
            Cursor cursorupdate=sqLiteDatabase.rawQuery("SELECT *FROM emp WHERE name='"+search+"'",null);

            if (cursorupdate.moveToFirst())
            {
                if (name.equals("")||pass.equals(""))
                {
                    Toast.makeText(this,"All fields are compulsory",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    sqLiteDatabase.execSQL("UPDATE emp set name='"+name+"',pass='"+pass+"',where search='"+search+"'");
                    Toast.makeText(this,"record updated ",Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(this,"data not found",Toast.LENGTH_LONG).show();
            }
        }
    }
}
