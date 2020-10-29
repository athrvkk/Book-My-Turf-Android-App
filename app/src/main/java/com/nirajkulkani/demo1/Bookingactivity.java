package com.nirajkulkani.demo1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Bookingactivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ViewPager viewPager ;
    String name1 ;
    String phn1 ;
    TextView t1 ,t2,t3 ;
    Button[] slots=new Button[16];
String turfid,turfname ;
int AW,MW,EW,AWE,MWE,EWE ;
    TextView tv ;
String currentdate1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingactivity);
        Bundle bundle=getIntent().getExtras();
        getSupportActionBar().setTitle("Bookings");

        turfid =bundle.getString("turf_id");
        AW=bundle.getInt("AW");
        MW=bundle.getInt("MW");
        EW=bundle.getInt("EW");
        AWE=bundle.getInt("AWE");
        MWE=bundle.getInt("MWE");
        EWE=bundle.getInt("EWE");
        turfname=bundle.getString("turf_name");
        DatabaseReference fd=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        fd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users=dataSnapshot.getValue(Users.class);
                name1=users.getName1() ;
                phn1=users.getPhn() ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        Button date=(Button)findViewById(R.id.btn_calender);
        tv=(TextView)findViewById(R.id.textView);
        t1=(TextView)findViewById(R.id.tv_Morning);
        t2=(TextView)findViewById(R.id.tv_afternoon);
        t3=(TextView)findViewById(R.id.tv_evening);
        slots[0] = (Button)findViewById(R.id.s1);
        slots[1] = (Button)findViewById(R.id.s2);
        slots[2] = (Button)findViewById(R.id.s3);
        slots[3] = (Button) findViewById(R.id.s4);
        slots[4] = (Button) findViewById(R.id.s5);
        slots[5] = (Button) findViewById(R.id.s6);
        slots[6] = (Button) findViewById(R.id.s7);
        slots[7]= (Button) findViewById(R.id.s8);
        slots[8]= (Button) findViewById(R.id.s9);
        slots[9] = (Button)findViewById(R.id.s10);
        slots[10] = (Button)findViewById(R.id.s11);
        slots[11]= (Button)findViewById(R.id.s12);
        slots[12]= (Button)findViewById(R.id.s13);
        slots[13] = (Button) findViewById(R.id.s14);
        slots[14]= (Button) findViewById(R.id.s15);
        slots[15]= (Button)findViewById(R.id.s16);






        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datapicker= new dataPicker();
                datapicker.show(getSupportFragmentManager(),"date picker");

            }
        });



    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        SimpleDateFormat ss =new SimpleDateFormat("yyyy-MM-dd");
        currentdate1=ss.format(c.getTime());
        String currentdate= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tv.setText(currentdate);
        for(Button slot:slots){
            slot.setVisibility(View.VISIBLE);
            slot.setEnabled(true);
            slot.setBackground(getDrawable(R.drawable.green_button));
            slot.setTextColor(getResources().getColor(R.color.green));
        }
        t1.setVisibility(View.VISIBLE);
        t2.setVisibility(View.VISIBLE);
        t3.setVisibility(View.VISIBLE);
        Calendar c2=Calendar.getInstance();
        SimpleDateFormat data=new SimpleDateFormat("hh:mm a");
        String time=data.format(c2.getTime());
        Date today= new Date();
      //
        //  Toast.makeText(this,""+tv.getText().toString(),Toast.LENGTH_SHORT).show();
        if(currentdate1.equals(new SimpleDateFormat("yyyy-MM-dd").format(today))){
            SimpleDateFormat simpDate;

            simpDate = new SimpleDateFormat("hh:mm a");
           // sd= new SimpleDateFormat("hh:mm a");
            String currenttime=simpDate.format(today);

            for(int i5=0;i5<slots.length;i5++) {
                String sdate=slots[i5].getText().toString().substring(3, 5) + ":00 " + slots[i5].getText().toString().substring(5, 7).toUpperCase();
                DateFormat sdf=new SimpleDateFormat("hh:mm a");
                try {
                    Date slottime=  sdf.parse(sdate);
                    if(simpDate.parse(currenttime).before(slottime)){

                        //DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Booking").child(turfid).child(tv.getText().toString());
                        //new references:
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Booking").child(turfid).child(currentdate1);
                        ref.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!dataSnapshot.exists()){

                                    for(int i=0 ;i<slots.length;i++){
                                        clickon(slots[i],i);
                                    }
                                }
                                else {
                                    for (DataSnapshot books : dataSnapshot.getChildren()) {
                                        BookedList list = (BookedList) books.getValue(BookedList.class);
                                        int index=0 ;
                                        for (int i=0 ;i<slots.length;i++) {

                                            check(list, slots[i],i);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                    else
                    {
                        slots[i5].setVisibility(View.GONE);
                        t1.setVisibility(View.GONE);
                        t2.setVisibility(View.GONE);
                        t3.setVisibility(View.GONE);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(sdate.equals("12:00 AM")){
                    slots[i5].setVisibility(View.VISIBLE);

                }

            }
        } else{

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Booking").child(turfid).child(currentdate1);

            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()){

                        for(int i=0 ;i<slots.length;i++){

                            clickon(slots[i],i);
                        }
                    }
                    else {
                        for (DataSnapshot books : dataSnapshot.getChildren()) {
                            BookedList list = (BookedList) books.getValue(BookedList.class);
                            int index=0 ;
                            for (int i=0 ;i<slots.length;i++) {

                                check(list, slots[i],i);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @SuppressLint("ResourceAsColor")
    public void check(BookedList bookedList, final Button button, final int index){

        if ((bookedList.time.equals(button.getText().toString()))) {

            button.setBackground(getDrawable(R.drawable.login_red_button));
            button.setTextColor(getResources().getColor(R.color.red));
            button.setEnabled(false);



        }

        else
        {
            if(!currentdate1.equals(bookedList.getDate().toString())){
                button.setEnabled(true);
                button.setBackground(getDrawable(R.drawable.green_button));
                button.setTextColor(getResources().getColor(R.color.green));
            }



            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mdialog= new AlertDialog.Builder(Bookingactivity.this);

                    View view1= getLayoutInflater().inflate(R.layout.bookingdialog,null);
                    TextView tv_date=(TextView)view1.findViewById(R.id.tv_date);
                    TextView tv_time=(TextView)view1.findViewById(R.id.tv_time);
                    ImageButton cross=(ImageButton)view1.findViewById(R.id.cross);

                    final TextView tv_price=(TextView)view1.findViewById(R.id.tv_price);
                    Button b1=(Button)view1.findViewById(R.id.btn_book);
                    tv_date.setText(tv.getText().toString());
                    tv_time.setText(button.getText().toString());
                    // to get the day from the  date of yyyy-MM-dd format
                    Calendar calendar =Calendar.getInstance() ;
                    int day=0 ;
                    try {
                        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(currentdate1));
                        day=calendar.get(Calendar.DAY_OF_WEEK);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(day==1||day==7){
                        if(index>=0 &&index<=4){
                            tv_price.setText(String.valueOf(MWE));
                        }
                        else if(index>=5 &&index<=10){
                            tv_price.setText(String.valueOf(AWE));
                        }
                        else if(index>=11&&index<=15){
                            tv_price.setText(String.valueOf(EWE));
                        }

                    }
                    else
                    {
                        if(index>=0 &&index<=4){
                            tv_price.setText(String.valueOf(MW));
                        }
                        else if(index>=5 && index<=10){
                            tv_price.setText(String.valueOf(AW));
                        }
                        else if(index>=11 &&index<=15){
                            tv_price.setText(String.valueOf(EW));
                        }
                    }

                    mdialog.setView(view1);
                    mdialog.setCancelable(true);
                    final AlertDialog dialog=mdialog.create();

                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                        }
                    });

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // FirebaseDatabase.getInstance().getReference("User_Booking").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users_booking);
                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Booking").child(turfid).child(currentdate1);
                            BookedList bookedList1= new BookedList(currentdate1,button.getText().toString(),tv_price.getText().toString(),phn1,name1,"Booked");
                            String bid=reference.push().getKey();

                            reference.child(bid).setValue(bookedList1);
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_Booking").child(FirebaseAuth.getInstance().getCurrentUser().getUid()) ;
                            users_booking users_booking= new users_booking(currentdate1,button.getText().toString(),tv_price.getText().toString(),turfname,bid,turfid);

                            ref.child(bid).setValue(users_booking);
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
                            String datetoshow="";
                            try {
                                datetoshow =DateFormat.getDateInstance(DateFormat.FULL).format(new SimpleDateFormat("yyyy-MM-dd").parse(bookedList1.getDate().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                                // Configure the notification channel.
                                notificationChannel.setDescription("Channel description");
                                notificationChannel.enableLights(true);
                                notificationChannel.setLightColor(Color.RED);
                                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                notificationChannel.enableVibration(true);
                                notificationManager.createNotificationChannel(notificationChannel);
                            }


                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Bookingactivity.this, NOTIFICATION_CHANNEL_ID);

                            notificationBuilder.setAutoCancel(true)
                                    .setDefaults(Notification.DEFAULT_ALL)
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.drawable.football4)
                                    .setTicker("Hearty365")
                                    //     .setPriority(Notification.PRIORITY_MAX)
                                    .setContentTitle("BOOKING CONFIRMED")

                                    .setStyle(new NotificationCompat.BigTextStyle().bigText("On Time:"+bookedList1.time() +" and \nDate:"+bookedList1.getDate()))
                                    .setContentText("On Time:"+bookedList1.time() +" and \nDate:"+datetoshow)
                                    .setContentInfo("Info");
                            notificationManager.notify(/*notification id*/1, notificationBuilder.build());

                            dialog.dismiss();
                        }
                    });


                    dialog.show();

                }
            });
        }}

    public void clickon(final Button button, final int index){


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mdialog= new AlertDialog.Builder(Bookingactivity.this);

                View view1= getLayoutInflater().inflate(R.layout.bookingdialog,null);
                TextView tv_date=(TextView)view1.findViewById(R.id.tv_date);
                TextView tv_time=(TextView)view1.findViewById(R.id.tv_time);
                final TextView tv_price=(TextView)view1.findViewById(R.id.tv_price);
                final Button b1=(Button)view1.findViewById(R.id.btn_book);
                ImageButton cross = (ImageButton)view1.findViewById(R.id.cross);

                tv_date.setText(tv.getText().toString());
                tv_time.setText(button.getText().toString());
                // to get the day from the  date of yyyy-MM-dd format
                Calendar calendar =Calendar.getInstance() ;
                int day=0 ;
                try {
                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(currentdate1));
                    day=calendar.get(Calendar.DAY_OF_WEEK);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //days sathi if
                if(day==1||day==7){
                    if(index>=0 &&index<=4){
                        tv_price.setText(String.valueOf(MWE));
                    }

                    else if(index>=5 &&index<=10){
                        tv_price.setText(String.valueOf(AWE));
                    }
                    else if(index>=11&&index<=15){
                        tv_price.setText(String.valueOf(EWE));
                    }

                }
                else
                {
                    if(index>=0 &&index<=4){
                        tv_price.setText(String.valueOf(MW));
                    }
                    else if(index>=5 && index<=10){
                        tv_price.setText(String.valueOf(AW));
                    }
                    else if(index>=11 &&index<=15){
                        tv_price.setText(String.valueOf(EW));
                    }
                }

                mdialog.setView(view1);
                mdialog.setCancelable(true);
                final AlertDialog dialog=mdialog.create();

                cross.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                b1.setOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        b1.setClickable(false);
                        BookedList bookedList1= new BookedList(currentdate1,button.getText().toString(),tv_price.getText().toString(),phn1,name1,"Booked");
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Booking").child(turfid).child(currentdate1);
                        String bid=reference.push().getKey();

                        reference.child(bid).setValue(bookedList1);
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_Booking").child(FirebaseAuth.getInstance().getCurrentUser().getUid()) ;
                        users_booking users_booking= new users_booking(currentdate1,button.getText().toString(),tv_price.getText().toString(),turfname,bid,turfid);
                        ref.child(bid).setValue(users_booking);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
                        String datetoshow="";
                        try {
                             datetoshow =DateFormat.getDateInstance(DateFormat.FULL).format(new SimpleDateFormat("yyyy-MM-dd").parse(bookedList1.getDate().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                            // Configure the notification channel.
                            notificationChannel.setDescription("Channel description");
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                            notificationChannel.enableVibration(true);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }

                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Bookingactivity.this, NOTIFICATION_CHANNEL_ID);

                        notificationBuilder.setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.football4)
                                .setTicker("Hearty365")
                                //     .setPriority(Notification.PRIORITY_MAX)
                                .setContentTitle("BOOKING CONFIRMED")
                                .setStyle(new NotificationCompat.BigTextStyle().bigText("On Time:"+bookedList1.time() +" and \nDate:"+bookedList1.getDate()))
                                .setContentText("On Time:"+bookedList1.time() +" \nand Date:"+datetoshow)
                                .setContentInfo("Info");
                        notificationManager.notify(/*notification id*/1, notificationBuilder.build());

                        dialog.dismiss();
                    }
                });



                dialog.show();

            }
        });

    }

}



