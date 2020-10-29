package com.nirajkulkani.demo1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class account extends AppCompatActivity implements  View.OnClickListener {
    private static final int PICK_IMAGE = 101;
    private FirebaseAuth fb;
    private DatabaseReference dRef, imgRef;
    private StorageReference sRef;
    private ProgressDialog pd;

    private ImageView i1, in, ic;
    private TextView tv1, tv2;
    private EditText et1, et2, et3, et4;
    private Button b1, b2;

    private String p;

    private Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        getSupportActionBar().setTitle("Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fb = FirebaseAuth.getInstance();


        i1 = (ImageView) findViewById(R.id.ProfilePhoto);
        in = (ImageView) findViewById(R.id.ac_editname);
        ic = (ImageView) findViewById(R.id.ac_editcall);

        tv1 = (TextView) findViewById(R.id.ac_title);

        et1 = (EditText) findViewById(R.id.ac_name);
        et2 = (EditText) findViewById(R.id.ac_phone);
        et3 = (EditText) findViewById(R.id.ac_oldpass);
        et4 = (EditText) findViewById(R.id.ac_newpass);


        b1 = (Button) findViewById(R.id.ac_change_pass);
        b2 = (Button) findViewById(R.id.ac_done);

        pd = new ProgressDialog(this);

        retrieveData();
        //retrieveProfileImg();

        in.setOnClickListener(this);
        ic.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }


    //----------ON BUTTON CLICK-------------------

    @Override
    public void onClick(View v) {

        if (v == in) {
            et1.getText().clear();
            in.setVisibility(v.INVISIBLE);
        }

        if (v == ic) {
            et2.getText().clear();
            ic.setVisibility(v.INVISIBLE);
        }


        if (v == b1) {
            et3.setVisibility(v.VISIBLE);
            et4.setVisibility(v.VISIBLE);
        }


        if (v == b2)
        {
            if (et4.length() == 0)
            {
                check();
            }

            else
            {
                check(p);
            }
        }

    }


    //-------------------------------------DATA RETRIEVAL START---------------------------------------------

    private void retrieveData() {
        pd.setMessage("Getting User Information...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        dRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users u = dataSnapshot.getValue(Users.class);

                String n = u.getName1();
                String c = u.getPhn();
                p = u.getPassword();

                et1.setText(n);
                et2.setText(c);

                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //---------------------CHECK START WITHOUT PASSWORD CHANGE-------------------------------

    private void check() {
        pd.setMessage("Updating Changes...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        final String new_name = et1.getText().toString().trim();
        final String new_call = et2.getText().toString().trim();
        final String old_pass = et3.getText().toString().trim();
        final String new_pass = et4.getText().toString().trim();

        if ((!android.util.Patterns.PHONE.matcher(new_call).matches()) || (new_call.length() != 10)) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Please enter valid Contact Number...", Toast.LENGTH_SHORT).show();
            return;
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null)
            {
                dRef.child("phn").setValue(new_call);
                dRef.child("name1").setValue(new_name);

                Toast.makeText(getApplicationContext(), "Information Updated Successfully!", Toast.LENGTH_SHORT).show();
                fb.signOut();
                finish();
                Intent back = new Intent(account.this, LoginActivity.class);
                startActivity(back);
            }
        }

    }


    //---------------------CHECK START WITHOUT PASSWORD CHANGE-------------------------------

    private void check(String p) {
        pd.setMessage("Updating Changes...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        final String new_name = et1.getText().toString().trim();
        final String new_call = et2.getText().toString().trim();
        final String old_pass = et3.getText().toString().trim();
        final String new_pass = et4.getText().toString().trim();


        if ((!android.util.Patterns.PHONE.matcher(new_call).matches()) || (new_call.length() != 10)) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Please enter valid Contact Number...", Toast.LENGTH_SHORT).show();
            return;
        }



        if(!old_pass.equals(p))
        {
            pd.dismiss();
            Toast.makeText(getApplicationContext(), "Wrong Old Password...", Toast.LENGTH_SHORT).show();
            return;
        }


        else
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if(user != null)
            {

                user.updatePassword(new_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        pd.dismiss();

                        dRef.child("phn").setValue(new_call);
                        dRef.child("name1").setValue(new_name);
                        dRef.child("password").setValue(new_pass);

                        Toast.makeText(getApplicationContext(), "Information Updated Successfully!", Toast.LENGTH_SHORT).show();
                        fb.signOut();
                        finish();
                        Intent back = new Intent(account.this, MainActivity.class);
                        startActivity(back);
                    }
                });

            }
        }

    }
}
