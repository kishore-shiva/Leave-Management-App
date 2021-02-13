package com.example.isqlglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Year;
import java.util.Calendar;

public class Take_Leave extends AppCompatActivity {
    DatabaseReference reff1;
    DatabaseReference reff2, reff_formal;
    member member;
    TextView month;
    EditText date;
    Spinner reasons, leave_type, no_of_days;
    String NAME;
    String MONTH;
    String DATE;
    String REASON;
    String LEAVE_TYPE;
    String txtleavedur;
    String months[];
    int day;
    int year;
    long no_of_leaves, no_of_updates;
    long no_of_stats;
    long i;
    String txtmonth;
    String txtdate;
    double no_of_days_of_leave, leave_duration, leave_duration_casual, a,x,y,z;
    Button takeleave;
    double leavecount[];
    int b,c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__leave);

        takeleave = findViewById(R.id.button3);
        month = findViewById(R.id.textView11);
        date = findViewById(R.id.editText7);


        Calendar calendar = Calendar.getInstance();
        final int Month = calendar.get(Calendar.MONTH);
        months = new String[]{"January", "Febrauary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        month.setText(months[Month]);



        day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <=15) {
            date.setText("" + day);
        } else if (hour > 15) {
            date.setText("" + (++day));
        }
        date.setEnabled(false);
        member = new member();


        reasons = findViewById(R.id.spinner2);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Take_Leave.this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.items));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasons.setAdapter(arrayAdapter);


        reasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        REASON = "Feeling sick";
                        break;
                    case 1:
                        REASON = "Family Occassions";
                        break;
                    case 2:
                        REASON = "Personal";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        leave_type = findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(Take_Leave.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.leave_type));
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leave_type.setAdapter(arrayAdapter1);

        leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        LEAVE_TYPE = "casual leave";
                        break;
                    case 1:
                        LEAVE_TYPE = "formal leave";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        no_of_days = findViewById(R.id.spinner3);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(Take_Leave.this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.nf_days));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        no_of_days.setAdapter(arrayAdapter2);


        no_of_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        no_of_days_of_leave = 0.5;
                        break;
                    case 1:
                        no_of_days_of_leave = 1;
                        break;
                    case 2:
                        no_of_days_of_leave = 2;
                        break;
                    case 3:
                        no_of_days_of_leave = 3;
                        break;
                    case 4:
                        no_of_days_of_leave = 4;
                        break;
                    case 5:
                        no_of_days_of_leave = 5;
                        break;
                    case 6:
                        no_of_days_of_leave = 6;
                        break;
                    case 7:
                        no_of_days_of_leave = 7;
                        break;
                    case 8:
                        no_of_days_of_leave = 8;
                        break;
                    case 9:
                        no_of_days_of_leave = 9;
                        break;
                    case 10:
                        no_of_days_of_leave = 10;
                        break;
                    case 11:
                        no_of_days_of_leave = 11;
                        break;
                    case 12:
                        no_of_days_of_leave = 12;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MONTH = month.getText().toString();
        DATE = date.getText().toString();
        final Intent leave_page = getIntent();
        NAME = leave_page.getStringExtra("Name");

        year = calendar.get(Calendar.YEAR);

        if((MONTH.equals("January")||MONTH.equals("March")||MONTH.equals("May")||MONTH.equals("July")||MONTH.equals("August")||MONTH.equals("October"))&&DATE.equals("32")){
            month.setText(months[Month+1]);
            DATE = "1";
            date.setText(DATE);
            MONTH = months[Month+1];
        }
        else if((MONTH.equals("April") || MONTH.equals("June") || MONTH.equals("September") || MONTH.equals("November"))&&date.getText().equals("31")){
            month.setText(months[Month+1]);
            DATE = "1";
            date.setText(DATE);
            MONTH = months[Month+1];
        }
        else if(MONTH.equals("December")&&DATE.equals("32")){
            month.setText("January");
            DATE = "1";
            date.setText(DATE);
            MONTH = "January";
        }
        else if(MONTH.equals("Febrauary")&& year%4==0&&DATE.equals("30")){
            month.setText(months[Month+1]);
            DATE = "1";
            date.setText(DATE);
            MONTH = months[Month+1];
        }
        else if(MONTH.equals("Febrauary")&& year%4!=0&&DATE.equals("29")){
            month.setText(months[Month+1]);
            DATE = "1";
            date.setText(DATE);
            MONTH = months[Month+1];
        }



        reff1 = FirebaseDatabase.getInstance().getReference().child(NAME).child("casual leave");
        reff1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    no_of_leaves = (dataSnapshot.getChildrenCount());
                    leavecount = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    b = 0;
                    if (no_of_leaves > 0) {
                        leave_duration_casual = 0;
                        for (i = 1; i <= no_of_leaves; i++) {
                            x = 0;
                            txtmonth = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                            txtleavedur = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();
                            txtdate = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();

                            if (txtleavedur.equals("half-day") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 0.5;
                            } else if (txtleavedur.equals("1-DAY") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 1;
                            } else if (txtleavedur.equals("2-DAYS") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 2;
                            } else {
                                leave_duration_casual += 0;
                            }
                            for (int q = 0; q < Month; q++) {
                                if (txtmonth.equals(months[q])) {
                                    if (txtleavedur.equals("half=day")) {
                                        leavecount[q] += 0.5;
                                    } else if (txtleavedur.equals("1-DAY")) {
                                        leavecount[q] += 1;
                                    } else if (txtleavedur.equals("2-DAYS")) {
                                        leavecount[q] += 2;
                                    }
                                }
                            }
                            for (int k = 0; k < Month; k++) {
                                if (leavecount[k] == 0) {
                                    x += 1;
                                } else if (leavecount[k] == 0.5) {
                                    x += 0.5;
                                } else if (leavecount[k] == 1.5) {
                                    x = (x - 0.5);
                                } else if (leavecount[k] == 2) {
                                    x = x - 1;
                                }
                            }
                            if (x + 1 > 2) {
                                y = 2;
                            } else {
                                y = 1 + x;
                            }
                            y = y - leave_duration_casual;
                            if (txtdate.equals(DATE) && txtleavedur.equals("half-day")) {
                                b += 1;
                            } else if (txtdate.equals(DATE) && txtleavedur.equals("1-DAY")) {
                                b += 2;
                            } else if (txtdate.equals(DATE) && txtleavedur.equals("2-DAYS")) {
                                b += 4;
                            }
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        reff2 = FirebaseDatabase.getInstance().getReference().child("updates");
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    no_of_updates = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reff_formal = FirebaseDatabase.getInstance().getReference().child(NAME).child("formal leave");
        reff_formal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    no_of_stats = dataSnapshot.getChildrenCount();
                    c=0;
                    if (no_of_stats > 0) {
                        leave_duration = 0;
                        for (i = 1; i <= no_of_stats; i++) {
                            txtmonth = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                            txtleavedur = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();
                            txtdate = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                            if (txtleavedur.equals("half-day")) {
                                leave_duration += 0.5;
                            } else if (txtleavedur.equals("1-DAY")) {
                                leave_duration += 1;
                            } else if (txtleavedur.equals("2-DAYS")) {
                                leave_duration += 2;
                            } else if (txtleavedur.equals("3-DAYS")) {
                                leave_duration += 3;
                            } else if (txtleavedur.equals("4-DAYS")) {
                                leave_duration += 4;
                            } else if (txtleavedur.equals("5-DAYS")) {
                                leave_duration += 5;
                            } else if (txtleavedur.equals("6-DAYS")) {
                                leave_duration += 6;
                            } else if (txtleavedur.equals("7-DAYS")) {
                                leave_duration += 7;
                            } else if (txtleavedur.equals("8-DAYS")) {
                                leave_duration += 8;
                            } else if (txtleavedur.equals("9-DAYS")) {
                                leave_duration += 9;
                            } else if (txtleavedur.equals("10-DAYS")) {
                                leave_duration += 10;
                            } else if (txtleavedur.equals("11-DAYS")) {
                                leave_duration += 11;
                            } else if (txtleavedur.equals("12-DAYS")) {
                                leave_duration += 12;
                            }
                            if(txtdate.equals(DATE)&&txtleavedur.equals("half-day")){
                                c+=1;
                            }
                            else if(txtdate.equals(DATE)&&txtleavedur.equals("1-DAY")){
                                c+=2;
                            }
                            else if(txtdate.equals(DATE)&&txtleavedur.equals("2-DAYS")){
                                c+=4;
                            }
                            else if(txtdate.equals(DATE)){
                                c+=5;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void check(final View view) {
        takeleave.setEnabled(false);

        Calendar calendar = Calendar.getInstance();


        int Date = Integer.parseInt(date.getText().toString());


         if (LEAVE_TYPE.equals("casual leave") && no_of_days_of_leave > 2) {
            Toast.makeText(this, "casual leave is accepted only for ONE or TWO or HALF-A-DAY repectively", Toast.LENGTH_LONG).show();
            takeleave.setEnabled(true);
        } else if (LEAVE_TYPE.equals("casual leave")) {
            member.setMonth(MONTH);
            member.setDate(DATE);
            member.setReason(REASON);
            a = 0;
            if (no_of_days_of_leave == 0.5) {
                member.setLeave_duration("half-day");
                a += 0.5;

            } else if (no_of_days_of_leave == 1) {
                member.setLeave_duration("1-DAY");
                a += 1;
            } else if (no_of_days_of_leave == 2 && y==2) {
                member.setLeave_duration("2-DAYS");
                a+=2;
            } else if (no_of_days_of_leave == 2 && y < 2&&y>0) {
                Toast.makeText(this, "you have only " + y + " days of leave remaining!\n please select appropriate days of leave!", Toast.LENGTH_LONG).show();
                takeleave.setEnabled(true);
            }
            if (no_of_leaves == 0 || leave_duration_casual == 0) {
                if(no_of_leaves==0){
                    if(MONTH.equals("January")&&no_of_days_of_leave==2){
                        Toast.makeText(this,"you have only 1-DAY casual leave in january\n please select appropriate days of leave!",Toast.LENGTH_LONG).show();
                    }
                    else if(no_of_days_of_leave==2){
                        member.setLeave_duration("2-DAYS");
                        reff1.child(String.valueOf(no_of_leaves + 1)).setValue(member);
                        member.setName(NAME);
                        member.setLeave_type(LEAVE_TYPE);
                        reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                        Toast.makeText(this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    }
                    else{
                        reff1.child(String.valueOf(no_of_leaves + 1)).setValue(member);
                        member.setName(NAME);
                        member.setLeave_type(LEAVE_TYPE);
                        reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                        Toast.makeText(this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    }
                }
                else if(no_of_days_of_leave <= y){
                    reff1.child(String.valueOf(no_of_leaves + 1)).setValue(member);
                    member.setName(NAME);
                    member.setLeave_type(LEAVE_TYPE);
                    reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                    Toast.makeText(this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                }
                } else if (no_of_leaves > 0) {
                if((b+c)==1 && no_of_days_of_leave==0.5&&no_of_days_of_leave<=y) {
                    reff1.child(String.valueOf(no_of_leaves + 1)).setValue(member);
                    member.setName(NAME);
                    member.setLeave_type(LEAVE_TYPE);
                    reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                    Toast.makeText(Take_Leave.this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                }
                else if((b+c)==1&&no_of_days_of_leave!=0.5){
                    Toast.makeText(this,"you have already taken a HALF-DAY leave on"+DATE+","+MONTH+"\nyou can only take another half-day leave",Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true   );
                }
                else if((b+c)>1){
                    Toast.makeText(this,"you have already taken leave on"+DATE+","+MONTH+"\nyou dont need to apply for leave as you have already applied for it!",Toast.LENGTH_LONG).show();
                takeleave.setEnabled(true);
                }
                 else if (no_of_days_of_leave > y && y==0) {
                    Toast.makeText(Take_Leave.this, "you have already taken leave on " + MONTH + "\nyour leave quota has been exhausted " +
                            "for this month!\n Please contact the Manager for further leave!", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);

                }
                else if (no_of_days_of_leave > y && y!=0) {
                    Toast.makeText(Take_Leave.this, "you have only " + y + " days of leave remaining!\\n please select appropriate days of leave!" +
                            "for this month!", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);

                }else if (no_of_days_of_leave <= y ) {
                    reff1.child(String.valueOf(no_of_leaves + 1)).setValue(member);
                    member.setName(NAME);
                    member.setLeave_type(LEAVE_TYPE);
                    reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                    Toast.makeText(Take_Leave.this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                    Toast.makeText(this,""+y,Toast.LENGTH_LONG).show();
                }
                 }
            } else if (LEAVE_TYPE.equals("formal leave")) {
                member.setMonth(MONTH);
                member.setDate(DATE);
                member.setReason(REASON);
                a = 0;
                if (no_of_days_of_leave == 0.5) {
                    member.setLeave_duration("half-day");
                    a += 0.5;
                } else if (no_of_days_of_leave == 1) {
                    member.setLeave_duration("1-DAY");
                    a += 1;
                } else if (no_of_days_of_leave == 2) {
                    member.setLeave_duration("2-DAYS");
                    a += 2;
                } else if (no_of_days_of_leave == 3) {
                    member.setLeave_duration("3-DAYS");
                    a += 3;
                } else if (no_of_days_of_leave == 4) {
                    member.setLeave_duration("4-DAYS");
                    a += 4;
                } else if (no_of_days_of_leave == 5) {
                    member.setLeave_duration("5-DAYS");
                    a += 5;
                } else if (no_of_days_of_leave == 6) {
                    member.setLeave_duration("6-DAYS");
                    a += 6;
                } else if (no_of_days_of_leave == 7) {
                    member.setLeave_duration("7-DAYS");
                    a += 7;
                } else if (no_of_days_of_leave == 8) {
                    member.setLeave_duration("8-DAYS");
                    a += 8;
                } else if (no_of_days_of_leave == 9) {
                    member.setLeave_duration("9-DAYS");
                    a += 9;
                } else if (no_of_days_of_leave == 10) {
                    member.setLeave_duration("10-DAYS");
                    a += 10;
                } else if (no_of_days_of_leave == 11) {
                    member.setLeave_duration("11-DAYS");
                    a += 11;
                } else if (no_of_days_of_leave == 12) {
                    member.setLeave_duration("12-DAYS");
                    a += 12;
                }
                if (no_of_stats == 0 && (b+c)==0) {
                    reff_formal.child(String.valueOf(no_of_stats + 1)).setValue(member);
                    member.setName(NAME);
                    member.setLeave_type(LEAVE_TYPE);
                    reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                    Toast.makeText(this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                }
                    else if(no_of_stats==0 && (b+c)==1 && no_of_days_of_leave==0.5) {
                    reff_formal.child(String.valueOf(no_of_stats + 1)).setValue(member);
                    member.setName(NAME);
                    member.setLeave_type(LEAVE_TYPE);
                    reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                    Toast.makeText(this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                    }
                else if(no_of_stats==0 && (b+c)==1 && no_of_days_of_leave>0.5) {
                    Toast.makeText(this, "You have already taken a HALF-DAY leave on "+DATE+","+MONTH+" You dont need to apply for leave again!", Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                }
                 else if (no_of_stats > 0) {
                    if((b+c)==1 && no_of_days_of_leave==0.5 && (12-leave_duration)<=12) {
                        reff_formal.child(String.valueOf(no_of_stats + 1)).setValue(member);
                        member.setName(NAME);
                        member.setLeave_type(LEAVE_TYPE);
                        reff2.child(String.valueOf(no_of_stats + 1)).setValue(member);
                        Toast.makeText(Take_Leave.this, "Leave updated sucessfully", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    }
                    else if((b+c)==1&&no_of_days_of_leave>0.5) {
                        Toast.makeText(this, "you have already taken a HALF-DAY leave on" + DATE + "," + MONTH + "\nyou can only take another half-day leave", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    }
                    else if((b+c)>1){
                        Toast.makeText(this,"you have already taken leave on"+DATE+","+MONTH+"\nyou dont need to apply for leave as you have already applied for it!",Toast.LENGTH_LONG).show();
                    takeleave.setEnabled(true);
                    }
                    else if ((12 - leave_duration) < no_of_days_of_leave && (12 - leave_duration) > 0) {
                        Toast.makeText(this, "you have only " + (12 - (leave_duration)) + " days of leaves remaining!\nplease select appropriate days of leave", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    } else if ((leave_duration + a) > 12) {
                        Toast.makeText(Take_Leave.this, "You Leave limit has been reached!\nPlease contact your Manager for futher leave", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);
                    } else if ((leave_duration + a) <= 12 && (b+c)==0) {

                        reff_formal.child(String.valueOf(no_of_stats + 1)).setValue(member);
                        member.setName(NAME);
                        member.setLeave_type(LEAVE_TYPE);
                        reff2.child(String.valueOf(no_of_updates + 1)).setValue(member);
                        Toast.makeText(Take_Leave.this, "Leave updated sucessfully!", Toast.LENGTH_LONG).show();
                        takeleave.setEnabled(true);

                    }
                }


            }
        }

    }




