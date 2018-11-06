package com.example.sunejas.voix;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sunejas.voix.Models.CandidateModel;
import com.example.sunejas.voix.Models.ElectionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CandidateRegisterActivity extends AppCompatActivity {
    List<EditText> allEds = new ArrayList<EditText>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_register);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.main_ll);
        Button submitButton = (Button)findViewById(R.id.submit);
        final Intent intent1 = new Intent(CandidateRegisterActivity.this , IdGeneratorActivity.class);
        Intent intent = getIntent();
        final String electionKey = intent.getStringExtra("electionKey");
        Toast.makeText(this, electionKey, Toast.LENGTH_SHORT).show();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("elections").child(electionKey).child("candidates");
        String message = intent.getStringExtra("number_of_candidates");
        final Integer numberOfCandidates = Integer.parseInt(message);
        for(int i=1;i<=numberOfCandidates;i++){
            EditText et= new EditText(CandidateRegisterActivity.this);
            allEds.add(et);
            et.setId(i);
            et.setHint("Candidate Name " + Integer.toString(i));
            linearLayout.addView(et);

        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i <numberOfCandidates; i++) {
                    databaseReference.push().setValue(new CandidateModel(allEds.get(i).getText().toString(),0));
                }
                intent1.putExtra("electionId",electionKey);
                startActivity(intent1);
            }
        });


    }
}
