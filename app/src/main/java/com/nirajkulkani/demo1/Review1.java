package com.nirajkulkani.demo1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Review1 extends AppCompatActivity {
    private RecyclerView reviewlist ;
    private reviewAdapter reviewAdapter ;
    private List<reviewlist>  numofReviews ;
    private ImageButton cross;
   private Button add ;
   String name1 ;
   String phn1 ;
   ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review1);
        imageButton=findViewById(R.id.cross);
        DatabaseReference fd=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        getSupportActionBar().setTitle("Reviews");

        fd.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users =(Users) dataSnapshot.getValue(Users.class);
                name1=users.getName1() ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent=getIntent() ;
        final String idturf=intent.getStringExtra("turf_id");
        add=(Button)findViewById(R.id.button3);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final AlertDialog.Builder dialog= new AlertDialog.Builder(Review1.this);
                View view1=getLayoutInflater().inflate(R.layout.addreview,null);

                 final EditText reviewT=(EditText)view1.findViewById(R.id.Reveiwtitle);
                 final EditText reviewD=(EditText)view1.findViewById(R.id.reviewdesc);
                  final RatingBar rb=(RatingBar)view1.findViewById(R.id.ratingBar);
                 final TextView showRating=(TextView)view1.findViewById(R.id.ratingP);



                Button b1=(Button)view1.findViewById(R.id.submitreview);
                cross = (ImageButton)view1.findViewById(R.id.review_cross);

                dialog.setView(view1);
                dialog.setCancelable(true);
                final AlertDialog dia=dialog.create();

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dia.dismiss();
                    }
                });

//                cross.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dia.dismiss();
//                    }
//                });


                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        showRating.setText(String.valueOf(v));
                        switch ((int)ratingBar.getRating()){
                        case 1:
                        showRating.setText("Very bad");
                        break;
                        case 2:
                        showRating.setText("Average");
                        break;
                        case 3:
                        showRating.setText("Good");
                        break;
                        case 4:
                        showRating.setText("Great");
                        break;
                        case 5:
                        showRating.setText("Awesome");
                        break;
                        default:
                        showRating.setText("");
                    }
                    }
                });



                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                               String title;
                                String des;
                                int rate= (int) rb.getRating();
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Reviews").child(idturf);
                                if(!reviewT.getText().toString().isEmpty()) {
                                    title = reviewT.getText().toString();
                                }
                                else{
                                    title=null;
                                }
                                if(!reviewD.getText().toString().isEmpty()){
                                    des=reviewD.getText().toString();
                                }
                                else{
                                    des=null;
                                }
                                String rid=reference.push().getKey();

                                com.nirajkulkani.demo1.reviewlist reviewlist1= new reviewlist(rid,name1,title,des,rate);
                                reference.child(rid).setValue(reviewlist1);

                                dia.dismiss();
                    }
                });

                dia.show();
            }
        });

        reviewlist=(RecyclerView)findViewById(R.id.recyclerviewforreview);
        reviewlist.setHasFixedSize(true);
        reviewlist.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Reviews").child(idturf);
        numofReviews=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numofReviews.clear();
                for(DataSnapshot rev:dataSnapshot.getChildren()){
                    com.nirajkulkani.demo1.reviewlist r1=(reviewlist) rev.getValue(com.nirajkulkani.demo1.reviewlist.class);
                    numofReviews.add(r1);
                }
                //function for setting overall rating of app ;
                int sum=0 ;
                for(int i=0 ;i<numofReviews.size();i++){
                    com.nirajkulkani.demo1.reviewlist reviewlist=numofReviews.get(i);
                    sum=sum+reviewlist.getReviewrating();

                }
                int avg ;
                try {
                     avg = (int) sum / numofReviews.size();
                }catch (Exception e){
                    avg=0 ;
                }
                FirebaseDatabase.getInstance().getReference("turf_list").child(idturf).child("rating").setValue(avg);
                reviewAdapter= new reviewAdapter(numofReviews,Review1.this);
                reviewlist.setAdapter(reviewAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
