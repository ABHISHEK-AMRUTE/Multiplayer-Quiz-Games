package com.amrute_studio.game_multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Greeter_class extends AppCompatActivity {
TextView name;
shared_prefference local_databse;
    Spinner spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeter_class);
        local_databse = new shared_prefference(this);
        name = findViewById(R.id.name);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                local_databse.setName(name.getText().toString());
                startActivity(new Intent(Greeter_class.this,Home.class));
            }
        });

        String[] users = { "Male", "Female", "Others"};

        spin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }
}