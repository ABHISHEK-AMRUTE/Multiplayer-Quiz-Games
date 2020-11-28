package com.amrute_studio.game_multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
 shared_prefference local_database;
 EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationbar_color));
        name = findViewById(R.id.name);
        local_database = new shared_prefference(this);

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      local_database.setName( name.getText().toString());
             }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Greeter_class.class));
            }
        });

    }


}