package com.example.sunejas.voix;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sunejas.voix.Models.CandidateModel;
import com.example.sunejas.voix.Models.ElectionModel;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PollActivity extends AppCompatActivity {
    Integer numberOfWinners;
    int numberOfCheckboxesChecked = 0;
    ArrayList<String> retrievedCheckedCandidatesList;
    String candidateKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.main_ll);
        Button submitButton = (Button)findViewById(R.id.submit_button);
        Intent intent = getIntent();
        retrievedCheckedCandidatesList = new ArrayList<>();
        final String electionKey = intent.getStringExtra("electionKey");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("elections").child(electionKey);
        databaseReference.child("numberOfWinners").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("prerna",snapshot.getValue().toString());
                 numberOfWinners = Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        databaseReference.child("candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("prerna", snapshot.toString());
                    CandidateModel candidate = new CandidateModel();
                    candidate = snapshot.getValue(CandidateModel.class);
                    final CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setText(candidate.candidateName);
                    cb.setTextColor(Color.parseColor("#000000"));
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{
                                    new int[]{-android.R.attr.state_checked}, // unchecked
                                    new int[]{android.R.attr.state_checked}, // checked
                            },
                            new int[]{
                                    Color.parseColor("#000000"),  //unchecked color
                                    Color.parseColor("#009000"),  //checked color
                            }
                    );
                    CompoundButtonCompat.setButtonTintList(cb, colorStateList);

                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked && numberOfCheckboxesChecked >= numberOfWinners) {
                                cb.setChecked(false);
                            } else {
                                String checkedText = cb.getText()+"";
                                Log.d("prerna",checkedText);
                                // the checkbox either got unchecked
                                // or there are less than 2 other checkboxes checked
                                // change your counter accordingly
                                if (isChecked) {
                                    numberOfCheckboxesChecked++;
                                    retrievedCheckedCandidatesList.add(checkedText);
                                } else {
                                    numberOfCheckboxesChecked--;
                                    retrievedCheckedCandidatesList.remove(checkedText);
                                }

                                // now everything is fine and you can do whatever
                                // checking the checkbox should do here
                            }
                        }
                    });

                    linearLayout.addView(cb);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<retrievedCheckedCandidatesList.size();i++){
                    final String checkedCandidate = retrievedCheckedCandidatesList.get(i);
                    databaseReference.child("candidates").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                CandidateModel candidate = new CandidateModel();
                                candidate = snapshot.getValue(CandidateModel.class);
                              if(candidate.candidateName.equals(checkedCandidate)){
                                    candidateKey = snapshot.getKey();
                                    databaseReference.child("candidates").child(candidateKey).child("votes").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Long value = (Long)dataSnapshot.getValue();
                                            value = value + 1;
                                            dataSnapshot.getRef().setValue(value);
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                    break;
                                }

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }
startActivity(new Intent(PollActivity.this,ThanksActivity.class));
            }
        });
    }

}
