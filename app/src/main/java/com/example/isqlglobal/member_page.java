package com.example.isqlglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class member_page extends AppCompatActivity {
    DatabaseReference reff_casual,reff_formal;
    String name;
    String txtmonth;
    String txtleavedur;
    TextView USERNAME, PASSWORD, NAME, leave_days;
    long no_of_leaves;
    long i;
    TextView scroll,scroll_formal;
    double leave_duration_casual,leave_duration,leavecount[],x,y;
    long no_of_stats;
    String months[],MONTH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);

       Intent mem_page=getIntent();
       name=mem_page.getStringExtra("Name");

        NAME = findViewById(R.id.textView12);
        NAME.setText(name);
       scroll=findViewById(R.id.textView7);
       scroll_formal=findViewById(R.id.textView17);

       scroll.setMovementMethod(new ScrollingMovementMethod());
       scroll_formal.setMovementMethod((new ScrollingMovementMethod()));


        Calendar calendar = Calendar.getInstance();
        final int Month = calendar.get(Calendar.MONTH);
        months = new String[]{"January", "Febrauary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        MONTH = (months[Month]);


       reff_casual = FirebaseDatabase.getInstance().getReference().child(name).child("casual leave");
        reff_casual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    no_of_leaves = (dataSnapshot.getChildrenCount());
                    leavecount = new double[]{0,0,0,0,0,0,0,0,0,0,0,0};
                    if(no_of_leaves>0) {
                        leave_duration_casual = 0;
                        for (i = 1; i <= no_of_leaves; i++) {
                            x = 0;
                            txtmonth = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                            txtleavedur = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();

                            if (txtleavedur.equals("half-day") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 0.5;
                            } else if (txtleavedur.equals("1-DAY") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 1;
                            } else if (txtleavedur.equals("2-DAYS") && txtmonth.equals(MONTH)) {
                                leave_duration_casual += 2;
                            }
                            for (int q = 0; q < Month; q++) {
                                if (txtmonth.equals(months[q])) {
                                    if (txtleavedur.equals("half=day")) {
                                        leavecount[q] += 0.5;
                                    } else if (txtleavedur.equals("1-DAY")) {
                                        leavecount[q] += 1;
                                    }
                                    else if(txtleavedur.equals("2-DAYS")){
                                        leavecount[q]+=2;
                                    }
                                }
                            }
                            for (int k = 0; k < Month; k++) {
                                if (leavecount[k] == 0) {
                                    x += 1;
                                } else if (leavecount[k] == 0.5) {
                                    x += 0.5;
                                } else if (leavecount[k] == 1.5) {
                                    x = (x-0.5);
                                }
                                else if(leavecount[k]==2){
                                    x=x-1;
                                }
                            }
                            if (x + 1 > 2) {
                                y = 2;
                            } else {
                                y = 1 + x;
                            }
                            y=y-leave_duration_casual;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reff_formal = FirebaseDatabase.getInstance().getReference().child(name).child("formal leave");
        reff_formal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    no_of_stats = dataSnapshot.getChildrenCount();
                    if (no_of_stats > 0) {
                        leave_duration = 0;
                        for (i = 1; i <= no_of_stats; i++) {
                            txtmonth = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                            txtleavedur = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();
                            if (txtleavedur.equals("half-day")) {
                                leave_duration += 0.5;
                            } else if (txtleavedur.equals("1-DAY")) {
                                leave_duration += 1;
                            } else if (txtleavedur.equals("1-DAY")) {
                                leave_duration += 1;
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
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void take_leave(View view){
        Intent leave_page = new Intent(this,Take_Leave.class);
        leave_page.putExtra("Name",name);
        startActivity(leave_page);



    }
    public void leave_summary(View view) {
        if (no_of_leaves == 0){
            scroll.setText("your leave list is empty!");
        }
        else{
            scroll.setText("No.of casual leaves remaining in "+MONTH+":\n"+y+"\n\n");
            reff_casual.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(i=1;i<=no_of_leaves;i++){
                        String date = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                        String month = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                        String reason = dataSnapshot.child(String.valueOf(i)).child("reason").getValue().toString();
                        String leave_duration = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();
                        scroll.append("\t"+i+". "+date+","+"\t"+month+"\n"+"\t     "+reason+"\n       "+leave_duration+"\n"+"\n");
                        scroll.setMovementMethod(new ScrollingMovementMethod());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(no_of_stats==0){
            scroll_formal.setText("your leave list is empty!");
        }
        else{
            scroll_formal.setText("No.of formal leaves remaining:\n"+(12-leave_duration)+"\n\n");
            reff_formal.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(i=1;i<=no_of_stats;i++){
                        String date = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                        String month = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                        String reason = dataSnapshot.child(String.valueOf(i)).child("reason").getValue().toString();
                        String leave_duration = dataSnapshot.child(String.valueOf(i)).child("leave_duration").getValue().toString();
                        scroll_formal.append("\t"+i+". "+date+","+"\t"+month+"\n"+"\t     "+reason+"\n       "+leave_duration+"\n"+"\n");
                        scroll_formal.setMovementMethod(new ScrollingMovementMethod());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
