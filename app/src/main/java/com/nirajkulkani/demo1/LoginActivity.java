package com.nirajkulkani.demo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{
    private FirebaseAuth mAuth;
    EditText editTextEmail,editTextPassword;
    private FirebaseAuth.AuthStateListener listener;
    private ProgressDialog pd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseAuth.getInstance();
        findViewById(R.id.textView6).setOnClickListener(this);
        getSupportActionBar().setTitle("Book my Turf");

        findViewById(R.id.btn3).setOnClickListener(this);
        editTextEmail=(EditText)findViewById(R.id.etName);
        editTextPassword=(EditText)findViewById(R.id.editText11);
        findViewById(R.id.textView6).setOnClickListener(this);
        pd=new ProgressDialog(this);
        listener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null){
                    Intent home= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(home);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    private void userLogin()
    {

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        if((password.length()==0) || (email.length()==0))
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

        if(password.isEmpty()) {

            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6) {

            editTextPassword.setError("Minimum 6 Characters");
            editTextPassword.requestFocus();
            return;
        }


        else
        {
            pd.setMessage("Logging In...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    pd.dismiss();

                    if(task.isSuccessful())
                    {
                        finish();
                        Toast.makeText(getApplicationContext(),"You Have Successfully Logged in",Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                    else{

                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn3:
                userLogin();
                break;

            case R.id.textView6:
                startActivity(new Intent(this,SignUpActivity.class));
                break;



        }

    }
}
