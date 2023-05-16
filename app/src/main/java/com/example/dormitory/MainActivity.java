package com.example.dormitory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonmynotes= (Button) findViewById(R.id.buttonmynotes);
        View.OnClickListener buttongo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyNotesActivity.class);
                startActivity(intent);
            }
        };

        buttonmynotes.setOnClickListener(buttongo);
    }
}