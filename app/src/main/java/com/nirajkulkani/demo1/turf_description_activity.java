package com.nirajkulkani.demo1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class turf_description_activity extends FragmentActivity {

    Button directions;
    ViewPager viewPager;


    turfimageswipeadapter turfimageswipeadapter;
    private List<turfImageUpload> uploads ;
    CardView review,direction,booking;
 TextView c , a ,con,adda,info,na;
Bundle prr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_turf_description_activity);

         Bundle bundle=getIntent().getExtras();
         final String ID1=bundle.getString(MainActivity.TURF_ID1);
        String contact = bundle.getString(MainActivity.CONTACT1);
        String address = bundle.getString(MainActivity.ADDRESS1);
        final String turfname=bundle.getString(MainActivity.TURF_NAME);
        final int MW1=bundle.getInt(MainActivity.MW);
        final int MWE=bundle.getInt(MainActivity.MWE);
        final int AW=bundle.getInt(MainActivity.AW);
        final int AWE=bundle.getInt(MainActivity.AWE);
        final int EW=bundle.getInt(MainActivity.EW);
        final int EWE=bundle.getInt(MainActivity.EWE);
        final double latitude=bundle.getDouble(MainActivity.LATITUDE);
        final double longitude=bundle.getDouble(MainActivity.LONGITUDE);
        final String information=bundle.getString(MainActivity.INFORMATION);



        na=(TextView)findViewById(R.id.textView5);
        info=(TextView)findViewById(R.id.tv_info);
        review=(CardView) findViewById(R.id.review);
        direction=(CardView)findViewById(R.id.direction);
        booking=(CardView)findViewById(R.id.booking);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        DatabaseReference  dr=FirebaseDatabase.getInstance().getReference("turfimage").child(ID1);
        uploads=new ArrayList<>();

        dr.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                uploads.clear();
                for(DataSnapshot up:dataSnapshot.getChildren()){
                    turfImageUpload upload=up.getValue(turfImageUpload.class);
                    uploads.add(upload);
                }
                turfimageswipeadapter =new turfimageswipeadapter(turf_description_activity.this,uploads);
                viewPager.setAdapter(turfimageswipeadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}
        });

       review.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               Intent intent1= new Intent(getApplicationContext(),Review1.class);
                intent1.putExtra("turf_id",ID1);
               startActivity(intent1);
           }
       });

       c=(TextView)findViewById(R.id.textView3);
       a=(TextView)findViewById(R.id.textView4);
       c.setText(contact);
       a.setText(address);
       info.setText(information);
       na.setText(turfname);
       con=(TextView)findViewById(R.id.turf_contact);
       adda=(TextView)findViewById(R.id.turf_contact2);


       booking.setOnClickListener(new View.OnClickListener()
       {
          @Override
          public void onClick(View view)
          {
              Intent book1= new Intent(turf_description_activity.this
                      ,Bookingactivity.class);


              book1.putExtra("turf_id",ID1);
              book1.putExtra("MW",MW1);
              book1.putExtra("MWE",MWE);
              book1.putExtra("AW",AW);
              book1.putExtra("AWE",AWE);
              book1.putExtra("EW",EW);
              book1.putExtra("EWE",EWE);
              book1.putExtra("turf_name",turfname);

              startActivity(book1);
          }
      });


        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url="http://maps.google.com/maps?f=d&hl=en&daddr="+latitude+","+longitude;

                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

}
