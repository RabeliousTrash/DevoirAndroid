package com.example.emsi_notif_sujet4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.emsi_notif_sujet4.Dao.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    private TextView joinText,signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);

        joinText=(TextView) findViewById(R.id.account);
        signInText=(TextView) findViewById(R.id.sin);

        joinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);

            }
        });


    }
}
