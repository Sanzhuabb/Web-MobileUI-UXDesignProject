package com.example.project2_tvbox_ui_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText ed1,ed2;
    Button b1,b2;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1=findViewById(R.id.username);
        ed2=findViewById(R.id.password);
        b1=findViewById(R.id.btn1);
        b2=findViewById(R.id.btn2);
        t1=findViewById(R.id.registerLink);

        t1.setMovementMethod(LinkMovementMethod.getInstance());
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,MainActivity.class);
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public void login()
    {
        String user= ed1.getText().toString();
        String pass=ed2.getText().toString();

        if(user.equals("") || pass.equals(""))
        {
            Toast.makeText(this,"Username or password corrected",Toast.LENGTH_SHORT).show();
        }
        else if(null!=checkUser(user,pass)){
            String userDb= checkUser(user,pass);
            Intent intent= new Intent(Login.this,Home.class);
            intent.putExtra("uname",userDb);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"User name or passpword does not match",Toast.LENGTH_SHORT).show();
            ed1.setText("");
            ed2.setText("");
            ed1.requestFocus();
        }
    }
    public String checkUser(String user, String pass)
    {
        SQLiteDatabase db= openOrCreateDatabase("Ann", Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("Select id, user, pass from user where user = ? and pass = ?",new String[]{user,pass});
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            String username=cursor.getString(1);
            String password = cursor.getString(2);
            SharedPreferences.Editor sp= getSharedPreferences("username",MODE_PRIVATE).edit();
            sp.putString("uname",username);
            sp.apply();
            cursor.close();
            return username;
        }return null;
    }
}