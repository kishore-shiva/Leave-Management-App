package com.example.isqlglobal;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isqlglobal.member;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;


public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    member member;
    long no_of_accounts;
    int open;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        member = new member();

        login = findViewById(R.id.button);
    }

    public void login(View view) {
        login.setEnabled(false);
        open =0;
        EditText Username = findViewById(R.id.editText);
        EditText Password = findViewById(R.id.editText4);
        final String txtusername = (Username.getText().toString()).toLowerCase().trim();
        final String txtpassword = Password.getText().toString();
        final Intent mem_page = new Intent(this, member_page.class);
        final Intent intent = new Intent(this, create_account.class);

        if(txtusername.isEmpty()||txtpassword.isEmpty()){
            Toast.makeText(this,"please fill the required fields to login!",Toast.LENGTH_LONG).show();
            login.setEnabled(true);
        }
        else if (txtusername.equals("saravanan") && txtpassword.equals("1234")) {
            startActivity(intent);
        } else {
            reff = FirebaseDatabase.getInstance().getReference().child("Member");
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        no_of_accounts = dataSnapshot.getChildrenCount();
                        for (long i = 1;i <= no_of_accounts; i++) {
                            reff = FirebaseDatabase.getInstance().getReference().child("Member").child(String.valueOf(i));
                            final long finalI = i;
                            final long finalI1 = i;
                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String username = dataSnapshot.child("username").getValue().toString();
                                    String password = dataSnapshot.child("password").getValue().toString();
                                    if (txtusername.equals(username) && txtpassword.equals(password)) {
                                        String name = dataSnapshot.child("name").getValue().toString();
                                        mem_page.putExtra("Name", name);
                                        login.setEnabled(true);
                                        startActivity(mem_page);
                                        Toast.makeText(MainActivity.this,"Login sucessfull!",Toast.LENGTH_LONG).show();
                                        open = 1;

                                    }
                                    else if(finalI1 ==no_of_accounts&&open==0) {
                                        Toast.makeText(MainActivity.this,"invalid username or password.Please try again!",Toast.LENGTH_LONG).show();
                                        login.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
}






