package com.example.isqlglobal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_account extends AppCompatActivity {
    long no_of_accounts,no_updates,i;
    DatabaseReference reff,reff_up;
    member member;
    TextView leave_updates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        member=new member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    no_of_accounts =(dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        leave_updates = findViewById(R.id.textView16);
        leave_updates.setMovementMethod(new ScrollingMovementMethod());

reff_up = FirebaseDatabase.getInstance().getReference().child("updates");
reff_up.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            no_updates = dataSnapshot.getChildrenCount();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
);
    }
    public void create(View view){
        EditText username=findViewById(R.id.editText2);
        EditText password=findViewById(R.id.editText3);
        EditText name=findViewById(R.id.editText5);
        String txtusername=username.getText().toString();
        String txtpassword=password.getText().toString();
        String txtname=name.getText().toString();
        if(txtname.isEmpty()||txtpassword.isEmpty()||txtusername.isEmpty()){
            Toast.makeText(this,"please fill required details to create an account",Toast.LENGTH_LONG).show();
        }
        else {
            member.setUsername(txtusername);
            member.setPassword(txtpassword);
            member.setName(txtname);
            reff.child(String.valueOf(no_of_accounts + 1)).setValue(member);
            Toast.makeText(this, "Value added successfully", Toast.LENGTH_LONG).show();

        }

    }
    public void clearorview(View view){
        if(no_updates==0){
            leave_updates.setText("NO Updates are remeining as you have viewed all");
        }
        else{
            leave_updates.setText("");
            reff_up.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(i=1;i<=no_updates;i++){
                        String date = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                        String month = dataSnapshot.child(String.valueOf(i)).child("month").getValue().toString();
                        String reason = dataSnapshot.child(String.valueOf(i)).child("reason").getValue().toString();
                        String name = dataSnapshot.child(String.valueOf(i)).child("name").getValue().toString();
                        String leave_type = dataSnapshot.child(String.valueOf(i)).child("leave_type").getValue().toString();
                        String leave_duration = dataSnapshot.child((String.valueOf(i))).child("leave_duration").getValue().toString();
                        leave_updates.append(name+"\n"+leave_type+"\n"+date+","+month+"\n"+leave_duration+"\n"+reason+"\n\n");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void delete(View view){
        Toast.makeText(this,"Updates Cleared!",Toast.LENGTH_LONG).show();
        reff_up.removeValue();

    }
}
