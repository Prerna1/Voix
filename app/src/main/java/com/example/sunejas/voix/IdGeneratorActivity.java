package com.example.sunejas.voix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunejas.voix.Utilities.GenerateRandomString;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IdGeneratorActivity extends AppCompatActivity {
    GenerateRandomString generateRandomString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_generator);
        Intent i = getIntent();
        String electionKey = i.getStringExtra("electionId");
        Toast.makeText(this, electionKey, Toast.LENGTH_SHORT).show();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("elections").child(electionKey).child("electionId");
        generateRandomString = new GenerateRandomString();
        String  electionIdString = generateRandomString.randomString(5);
        TextView electionId = (TextView)findViewById(R.id.election_id);
        electionId.setText(electionIdString);
        databaseReference.setValue(electionIdString);
    }

}
