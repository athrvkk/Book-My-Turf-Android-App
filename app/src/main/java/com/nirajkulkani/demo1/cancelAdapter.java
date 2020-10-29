package com.nirajkulkani.demo1;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class cancelAdapter extends RecyclerView.Adapter<cancelAdapter.ViewHolder> {
    private List<users_booking> book ;
    Context context ;

    public cancelAdapter(List<users_booking> book, Context context) {
        this.book = book;
        this.context = context;
    }

    @NonNull
    @Override
    public cancelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cancelcard,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cancelAdapter.ViewHolder viewHolder, int i) {
        final users_booking bookedList= book.get(i);
        String datetoshow="";
        try {
            datetoshow =DateFormat.getDateInstance(DateFormat.FULL).format(new SimpleDateFormat("yyyy-MM-dd").parse(bookedList.getDt().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       viewHolder.date.setText("DATE : "+datetoshow);
       viewHolder.time.setText("TIME : "+bookedList.getT());
       viewHolder.price.setText("PRICE : "+bookedList.getPrice());
       viewHolder.turfname.setText(bookedList.getTurf_name1());
       viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder dialog= new AlertDialog.Builder(context);
               dialog.setTitle("BOOKING CANCELLATION");
               dialog.setCancelable(true);
               dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
               dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       DatabaseReference drbookingforturf= FirebaseDatabase.getInstance().getReference("Booking").child(bookedList.getTurfid()).child(bookedList.getDt()).child(bookedList.getBookingid());
                       DatabaseReference drUserBooking =FirebaseDatabase.getInstance().getReference("User_Booking").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(bookedList.getBookingid());
                       drbookingforturf.removeValue();
                       drUserBooking.removeValue() ;
                       Toast.makeText(context,"CANCELLATION SUCCESSFULL.....",Toast.LENGTH_SHORT).show();
                       NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                       String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

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


                       NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);

                       notificationBuilder.setAutoCancel(true)
                               .setDefaults(Notification.DEFAULT_ALL)
                               .setWhen(System.currentTimeMillis())
                               .setSmallIcon(R.drawable.football4)
                               .setTicker("Hearty365")
                               //     .setPriority(Notification.PRIORITY_MAX)
                               .setContentTitle("CANCELLATION")

                               .setContentText("CANCELLATION SUCCESSFULL.")
                               .setContentInfo("Info");
                       notificationManager.notify(/*notification id*/1, notificationBuilder.build());

                   }
               });
               AlertDialog mdialog=dialog.create();
               mdialog.show();
           }
       });

    }

    @Override
    public int getItemCount() {
        return book.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date,time ,price,turfname ;
        private Button btn_cancel ;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=(TextView)itemView.findViewById(R.id.tv_cancel_date);
            time=(TextView)itemView.findViewById(R.id.tv_cancel_time);
            price=(TextView)itemView.findViewById(R.id.tv_cancel_price);
            turfname=(TextView)itemView.findViewById(R.id.tv_cancel_turfname);
            btn_cancel=(Button) itemView.findViewById(R.id.btn_cancel);
        }
    }
}

