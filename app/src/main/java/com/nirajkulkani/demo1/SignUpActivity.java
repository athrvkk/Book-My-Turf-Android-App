package com.nirajkulkani.demo1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 101;
    EditText editTextEmail,editTextPassword,pc;
    private FirebaseAuth mAuth;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button b1;
    TextView textView,tp;
    private ImageView i1;
    EditText edname1;
    EditText edphn ;
    private ProgressDialog pd;

    Uri ImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail=(EditText)findViewById(R.id.editText10);
        editTextPassword=(EditText)findViewById(R.id.editText11);
        pc=(EditText)findViewById(R.id.editText);
        b1 = (Button)findViewById(R.id.button);
        getSupportActionBar().setTitle("Book my Turf");

        mAuth=FirebaseAuth.getInstance();

        findViewById(R.id.button).setOnClickListener(this);
        mDisplayDate=(TextView)findViewById(R.id.textView);
        edname1=(EditText)findViewById(R.id.editText6);
        edphn=(EditText)findViewById(R.id.editText12);
        pd = new ProgressDialog(this);

    }


    @Override
    public void onClick(View v)
    {
        if(v==b1)
        {
            registerUser();
        }
    }









    private void registerUser()
    {

        final String name2=edname1.getText().toString().trim();
        final String phn1=edphn.getText().toString().trim();
        final String email=editTextEmail.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        final String p=editTextPassword.getText().toString().trim();

        if((name2.length()==0) || (email.length()==0) || (phn1.length()==0) || (password.length()==0) || (p.length()==0))
        {
            Toast.makeText(getApplicationContext(), "Please enter the Empty Fields...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.isEmpty()) {

            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            editTextEmail.setError("Please Enter a Valid email");
            editTextPassword.requestFocus();
            return;
        }

        if ((!android.util.Patterns.PHONE.matcher(phn1).matches()) || (phn1.length()!=10))
        {
            Toast.makeText(getApplicationContext(), "Please enter valid Contact Number...", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty()) {

            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<8) {

            editTextPassword.setError("Minimum 8 Characters");
            editTextPassword.requestFocus();
            return;
        }

        if(!password.equals(p))
        {
            editTextPassword.setError("Password does not match.");
            editTextPassword.requestFocus();
            return;
        }

        else
        {
            pd.setMessage("Registering User...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    pd.dismiss();
                    String ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    if(task.isSuccessful())
                    {
                        Users users= new Users(name2,phn1,email,password);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(users)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            finish();
                                            Toast.makeText(getApplicationContext(),"You Have Registered Successfully",Toast.LENGTH_SHORT).show();
                                            Intent intent;
                                            intent=new Intent(SignUpActivity.this,MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }
                                });

                    }
                    else {

                        if(task.getException() instanceof FirebaseAuthUserCollisionException) {

                            Toast.makeText(getApplicationContext(), "Email ID Already Exists", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }

    }


}
