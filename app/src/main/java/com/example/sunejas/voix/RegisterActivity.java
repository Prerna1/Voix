package com.example.sunejas.voix;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sunejas.voix.Models.ElectionModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    EditText hostName,electionName,description,startDate,startTime,endDate,endTime,numberOfCandidates,numberOfWinners;
    Button nextButton;
    Calendar myCalendarStart,myCalendarEnd,myCalendarStart1,myCalendarEnd1,currentCalendar,currentCalendar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hostName = (EditText)findViewById(R.id.host_name);
        electionName = (EditText)findViewById(R.id.election_name);
        description=(EditText)findViewById(R.id.description);
        startDate = (EditText)findViewById(R.id.start_date);
        startTime = (EditText)findViewById(R.id.start_time);
        endDate =(EditText)findViewById(R.id.end_date);
        endTime=(EditText)findViewById(R.id.end_time);
        numberOfCandidates =(EditText)findViewById(R.id.number_of_candidates);
        numberOfWinners = (EditText)findViewById(R.id.number_of_winners);
        nextButton = (Button)findViewById(R.id.next_button);
        myCalendarStart = Calendar.getInstance();
        myCalendarEnd = Calendar.getInstance();
        myCalendarStart1 = Calendar.getInstance();
        myCalendarEnd1 = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        currentCalendar1 = Calendar.getInstance();
        currentCalendar.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));
        currentCalendar.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
        currentCalendar.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        currentCalendar1.set(Calendar.YEAR,Calendar.getInstance().get(Calendar.YEAR));
        currentCalendar1.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
        currentCalendar1.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        currentCalendar1.set(Calendar.HOUR_OF_DAY,Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        currentCalendar1.set(Calendar.MINUTE,Calendar.getInstance().get(Calendar.MINUTE));
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("elections");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference pushedPostRef = databaseReference.push();
                String electionKey = pushedPostRef.getKey();
                pushedPostRef.setValue(new ElectionModel(hostName.getText().toString(),electionName.getText().toString(),
                        description.getText().toString(),startDate.getText().toString(),startTime.getText().toString(),endDate.getText().toString(),
                        endTime.getText().toString(),numberOfCandidates.getText().toString(),numberOfWinners.getText().toString()));
                Intent intent= new Intent(RegisterActivity.this ,CandidateRegisterActivity.class);
                intent.putExtra("number_of_candidates",numberOfCandidates.getText().toString());
                intent.putExtra("electionKey",electionKey);
                startActivity(intent);
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendarStart1.set(Calendar.YEAR, year);
                myCalendarStart1.set(Calendar.MONTH, monthOfYear);
                myCalendarStart1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendarEnd1.set(Calendar.YEAR, year);
                myCalendarEnd1.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startDate.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter Start Date first", Toast.LENGTH_SHORT).show();
                } else {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(RegisterActivity.this, date1, myCalendarEnd
                            .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                            myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        myCalendarStart1.set(Calendar.HOUR_OF_DAY,selectedHour);
                        myCalendarStart1.set(Calendar.MINUTE,selectedMinute);
                        if(myCalendarStart1.before(currentCalendar1)){
                            Toast.makeText(RegisterActivity.this, "Start Time can not be less than current time", Toast.LENGTH_SHORT).show();
                        }
                        else{
                        startTime.setText( selectedHour + ":" + selectedMinute);
                    }}
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (startDate.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Enter Start Date first", Toast.LENGTH_SHORT).show();
                }
                else if(startTime.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Enter start time first", Toast.LENGTH_SHORT).show();
                }
                else if(endDate.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "Enter end date first", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(RegisterActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            myCalendarEnd1.set(Calendar.HOUR_OF_DAY, selectedHour);
                            myCalendarEnd1.set(Calendar.MINUTE, selectedMinute);
                            if(myCalendarEnd1.before(myCalendarStart1)){
                                Toast.makeText(RegisterActivity.this, "End time cannot be less than start time", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                endTime.setText(selectedHour + ":" + selectedMinute);
                            }
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            }
        });

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if (myCalendarStart.before(currentCalendar)) {
            Toast.makeText(RegisterActivity.this, "Start date should not be less than current date", Toast.LENGTH_SHORT).show();
        } else {
            startDate.setText(sdf.format(myCalendarStart.getTime()));

        }
    }
    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if(myCalendarEnd.before(myCalendarStart)){
            Toast.makeText(this, "End date cannot be less than start date", Toast.LENGTH_SHORT).show();
        }
        else {
            endDate.setText(sdf.format(myCalendarEnd.getTime()));
        }
    }
}
