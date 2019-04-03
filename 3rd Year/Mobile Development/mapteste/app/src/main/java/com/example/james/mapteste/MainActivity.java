package com.example.james.mapteste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    List<Place> places;

    FirebaseDatabase database;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        places = new ArrayList<>();

        mRef = database.getReference().child("Places");

        mRef.addListenerForSingleValueEvent( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot Dchild : dataSnapshot.getChildren() ){
                    Place p = new Place();
                    p.setName(((String) Dchild.child("Name").getValue()));
                    p.setLon(Double.parseDouble(Dchild.child("Lon").getValue().toString()));
                    p.setLat(Double.parseDouble(Dchild.child("Lat").getValue().toString()));
                    places.add(p);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void launchMaps(View v){
        Intent maps = new Intent(this, MapsActivity.class);
        maps.putParcelableArrayListExtra("p", (ArrayList) places);
        startActivity(maps);
    }

}
