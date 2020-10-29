package com.nirajkulkani.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class turfListAdapter extends RecyclerView.Adapter<turfListAdapter.ViewHolder>{
     private List<turfList> turfLists ;
    private Context context ;
    private OnItemClickListner mlistner;
    private List<turfList> turfListsfull;
public interface  OnItemClickListner{
    void itemClick(int position);
}
   public void setOnItemClickListener(OnItemClickListner listner){
     mlistner=listner ;
   }
    public turfListAdapter(List<turfList> turfLists, Context context) {
        this.turfLists = turfLists;
        this.context = context;
        turfListsfull= new ArrayList<>(turfLists);
    }

    @NonNull
    @Override
    public turfListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_product,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        turfList turfList=turfLists.get(i);
        viewHolder.turf_name.setText(turfList.getTurf_name());
        viewHolder.address.setText(turfList.getAddress());
        viewHolder.turfrating.setText((String.valueOf(turfList.getRating())) );
        Picasso.with(context).load(turfList.getImageid()).fit().into(viewHolder.imageView);


    }



    @Override
    public int getItemCount() {
        return turfLists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView turf_name, address,turfrating,info ;
        ImageView imageView;
        public RelativeLayout relativeLayout;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            turf_name=(TextView)itemView.findViewById(R.id.tvname_turf);
            address=(TextView)itemView.findViewById(R.id.tvaddress_turf);
            turfrating=(TextView)itemView.findViewById(R.id.textViewRating);
            imageView = itemView.findViewById(R.id.imageView);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.rl);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mlistner!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mlistner.itemClick(position);
                        }
                    }
                }
            });


        }
    }
    public void updateList(List<turfList> newList)
    {
        turfLists=new ArrayList<>();
        turfLists.addAll(newList);
        notifyDataSetChanged();
    }
}
