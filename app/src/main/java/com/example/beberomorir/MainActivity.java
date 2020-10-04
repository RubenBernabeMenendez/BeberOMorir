package com.example.beberomorir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        this.bd = admin.getWritableDatabase();
        System.out.println(this.bd.getPath());
    }

    /** Called when the user taps the Send button */
    public void openMenu(View view) {
        // Do something in response to button
        Intent i = new Intent(this, MenuActivity.class );
        startActivity(i);
    }
}