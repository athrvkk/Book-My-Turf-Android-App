package com.nirajkulkani.demo1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cancel extends AppCompatActivity {

List<users_booking> book ;
RecyclerView cancel ;
    TextView tv ;
 private cancelAdapter cancelAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        getSupportActionBar().setTitle("Your Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         tv=findViewById(R.id.tv_message);
        cancel=(RecyclerView)findViewById(R.id.recycleview_cancel);
        cancel.setHasFixedSize(true);
        cancel.setLayoutManager(new LinearLayoutManager(this));
        book=new ArrayList<>();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_Booking").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener()

        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.exists()){
                    cancel.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);

                }
                else{
                    tv.setVisibility(View.GONE);
                    cancel.setVisibility(View.VISIBLE);

                }
                book.clear();
                for(DataSnapshot bookinglist :dataSnapshot.getChildren()){
                    users_booking users_booking=(users_booking) bookinglist.getValue(com.nirajkulkani.demo1.users_booking.class);

                    book.add(users_booking);
                }
                cancelAdapter=new cancelAdapter(book, com.nirajkulkani.demo1.cancel.this);
                cancel.setAdapter(cancelAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
