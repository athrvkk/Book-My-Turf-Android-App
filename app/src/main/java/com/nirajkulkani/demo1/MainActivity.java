package com.nirajkulkani.demo1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  SearchView.OnQueryTextListener,NavigationView.OnNavigationItemSelectedListener
{
    public static final String TURF_NAME="turf_name ";
    public static final String TURF_ID1 ="turf_id";
    public static final String CONTACT1 ="Contact";
    public static final String ADDRESS1="Address";
    public static final String LATITUDE="Latitude";
    public static final String LONGITUDE="Longitude";
    public static  final String MW="MW";
    public static  final String MWE="MWE";
    public static  final String AW="AW";
    public static  final String AWE="AWE";
    public static  final String EW="EW";
    public static  final String EWE="EWE";
    public static final String INFORMATION="information";
    private ProgressDialog pd;

    EditText search ;
    private RecyclerView turflistonhome ;
    private turfListAdapter adapter,seachadapter ;
    private List<turfList> turfLists ;
    private DrawerLayout mdrawerlayout ;
    private ActionBarDrawerToggle mtoggle ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turflistRecyclerview();
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mtoggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView;
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        pd = new ProgressDialog(this);
        getSupportActionBar().setTitle("Book my Turf");

        //--------------setting the navigation header details--------------------


        View head = navigationView.getHeaderView(0);

        pd.setMessage("Getting User Information...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        final TextView head_name = (TextView)head.findViewById(R.id.nav_name);

        //---------------Retrieving Data----------------

        DatabaseReference navhead = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


        navhead.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Users u = dataSnapshot.getValue(Users.class);

                String n = u.getName1();
                head_name.setText(n);
                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}
        });

    }



    public void turflistRecyclerview()
    {
        turflistonhome=(RecyclerView)findViewById(R.id.recyclerview_home);
        turflistonhome.setHasFixedSize(true);
        turflistonhome.setLayoutManager(new LinearLayoutManager(this));
        turfLists= new ArrayList<>();
        DatabaseReference databaseReference ;
        databaseReference= FirebaseDatabase.getInstance().getReference("turf_list");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                turfLists.clear();
                for(DataSnapshot turf:dataSnapshot.getChildren()){
                    turfList turfList= (turfList) turf.getValue(com.nirajkulkani.demo1.turfList.class);


                    turfLists.add(turfList);
                }
                adapter= new turfListAdapter(turfLists,MainActivity.this);
                turflistonhome.setAdapter(adapter);
                adapter.setOnItemClickListener(new turfListAdapter.OnItemClickListner() {
                    @Override
                    public void itemClick(int position) {
                       // Toast.makeText(MainActivity.this," normal adapter",Toast.LENGTH_SHORT).show();
                        turfList t1=turfLists.get(position);
                        Intent intent =new Intent(getApplicationContext(),turf_description_activity.class);
                        intent.putExtra(TURF_ID1,t1.getTurf_id());
                        intent.putExtra(CONTACT1,t1.getContact());
                        intent.putExtra(ADDRESS1,t1.getAddress());
                        intent.putExtra(TURF_NAME,t1.getTurf_name());
                        intent.putExtra(MW,t1.getMW());
                        intent.putExtra(MWE,t1.getMWE());
                        intent.putExtra(AW,t1.getAW());
                        intent.putExtra(AWE,t1.getAWE());
                        intent.putExtra(EW,t1.getEW());
                        intent.putExtra(EWE,t1.getEWE());
                        intent.putExtra(LATITUDE,t1.getLatitude());
                        intent.putExtra(LONGITUDE,t1.getLongitude());
                        intent.putExtra(INFORMATION,t1.getInfo());
                        intent.putExtra(TURF_NAME,t1.getTurf_name());
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_example,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener( this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String s) {
        turflistonhome.removeAllViews();
        final String userInput =s.toLowerCase();
        final List<turfList> newList=new ArrayList<>();
      DatabaseReference db=FirebaseDatabase.getInstance().getReference("turf_list");
      db.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              int count =0 ;
              newList.clear();
              for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                turfList tl=snapshot.getValue(turfList.class);
                if(tl.getTurf_name().toLowerCase().contains(userInput)){
                    newList.add(tl);
                    count++ ;
                }
                else if(tl.getAddress().toLowerCase().contains(userInput)){
                    newList.add(tl);
                    count++ ;
                }

                 if(count==10){
                    break ;
                 }

              }
              seachadapter=new turfListAdapter(newList,MainActivity.this);
              turflistonhome.setAdapter(seachadapter);
              seachadapter.setOnItemClickListener(new turfListAdapter.OnItemClickListner() {
                  @Override
                  public void itemClick(int position) {
                     // Toast.makeText(MainActivity.this,"sarch adapter",Toast.LENGTH_SHORT).show();
                      turfList t1=newList.get(position);
                      Intent intent =new Intent(getApplicationContext(),turf_description_activity.class);
                      intent.putExtra(TURF_ID1,t1.getTurf_id());
                      intent.putExtra(CONTACT1,t1.getContact());
                      intent.putExtra(ADDRESS1,t1.getAddress());
                      intent.putExtra(TURF_NAME,t1.getTurf_name());
                      intent.putExtra(MW,t1.getMW());
                      intent.putExtra(MWE,t1.getMWE());
                      intent.putExtra(AW,t1.getAW());
                      intent.putExtra(AWE,t1.getAWE());
                      intent.putExtra(EW,t1.getEW());
                      intent.putExtra(EWE,t1.getEWE());
                      intent.putExtra(LATITUDE,t1.getLatitude());
                      intent.putExtra(LONGITUDE,t1.getLongitude());
                      intent.putExtra(INFORMATION,t1.getInfo());
                      intent.putExtra(TURF_NAME,t1.getTurf_name());
                      startActivity(intent);
                  }
              });
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

        //adapter.updateList(newList);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {

            case R.id.nav_acc :
                Intent intent= new Intent(MainActivity.this,account.class);


                startActivity(intent);


                break;
            case R.id.nav_cancel :
                Intent intent1= new Intent(getApplicationContext(),cancel.class);
                startActivity(intent1);
                break;

            case R.id.nav_logout :
                FirebaseAuth fb=FirebaseAuth.getInstance();
                fb.signOut();
                Intent back=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(back);
                break;

        }
        mdrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)) {
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
