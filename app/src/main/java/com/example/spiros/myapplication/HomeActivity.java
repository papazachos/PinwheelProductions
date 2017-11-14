package com.example.spiros.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button search;
    private Button createadd;


    //FirebaseDatabase db = FirebaseDatabase.getInstance();
    //DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // Log.d("TheApp", "application created");
        //dbRef = db.getReference("/Adds/");
        createadd = (Button)findViewById(R.id.createadd);
        search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSearchClick();

            }
        });

        createadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateClick();
            }
        });

    }

    private void onSearchClick()
    {

        Intent a = new Intent(HomeActivity.this,Search.class);
        startActivity(a);

    }

    private void onCreateClick()
    {

        Intent b = new Intent(HomeActivity.this,Create2.class);
        startActivity(b);


    }

}
