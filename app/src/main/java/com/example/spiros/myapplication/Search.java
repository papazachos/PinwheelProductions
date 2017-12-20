package com.example.spiros.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference dbRef;


    ListView listView;

    List<Vasi2> addsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.d("TheApp", "application created");
        dbRef = db.getReference("/Adds/");

        listView =(ListView) findViewById(R.id.listView);
    }


    @Override
    protected void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                addsList.clear();
                for(DataSnapshot addsSnapshot : dataSnapshot.getChildren()){

                    Vasi2 vasi = addsSnapshot.getValue(Vasi2.class);
                    addsList.add(vasi);
                }

                ArrayAdapter adapter = new ArrayAdapter(Search.this,R.layout.activity_search,addsList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
