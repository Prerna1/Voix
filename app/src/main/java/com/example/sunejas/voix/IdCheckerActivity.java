package com.example.sunejas.voix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunejas.voix.Models.ElectionModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.provider.Settings.Secure;

public class IdCheckerActivity extends AppCompatActivity {
    int flag=0;
    String electionKey;
    Calendar currentCalendar;
    ElectionModel election;
    Date currentDate,startDate,endDate;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_checker);

        final EditText electionId = (EditText)findViewById(R.id.et_elction_id);
        Button submitButton =(Button)findViewById(R.id.submit_btn);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("elections");
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar = Calendar.getInstance();
                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String formattedDate = sdf.format(currentCalendar.getTime());
                try {
                    currentDate = sdf.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("peda",sdf.format(currentDate));
                final String s = electionId.getText().toString();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("prerna",snapshot.toString());
                            election = new ElectionModel();
                            election = snapshot.getValue(ElectionModel.class);
                            if(election.electionId.equals(s)){
                                electionKey = snapshot.getKey();
                                try {
                                    startDate = sdf.parse(election.startDate + " " + election.startTime);
                                    endDate = sdf.parse(election.endDate + " " + election.endTime);
                                    Log.d("peda",sdf.format(startDate));
                                    Log.d("peda",sdf.format(endDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                flag=1;
                                break;
                            }
                        }
                        if(flag==1){
                            flag=0;
                            if(startDate.compareTo(currentDate)>0){
                                Toast.makeText(IdCheckerActivity.this, "Poll not started yet", Toast.LENGTH_SHORT).show();
                            }
                            else if(currentDate.compareTo(endDate)>0){
                                Toast.makeText(IdCheckerActivity.this, "Poll ended", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(IdCheckerActivity.this, PollActivity.class);
                                intent.putExtra("electionKey", electionKey);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(IdCheckerActivity.this, "Enter valid id", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }
}
