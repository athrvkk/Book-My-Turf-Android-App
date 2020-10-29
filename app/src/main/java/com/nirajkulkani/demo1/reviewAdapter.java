package com.nirajkulkani.demo1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.ViewHolder1> {
    private List<reviewlist> reviewlists ;
    private Context context ;

    public reviewAdapter(List<reviewlist> reviewlists, Context context) {
        this.reviewlists = reviewlists;
        this.context = context;
    }

    @NonNull
    @Override
    public reviewAdapter.ViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviecard,viewGroup,false);
        return new ViewHolder1(view);
    }





    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.ViewHolder1 viewHolder, int i) {
      reviewlist reviewlist=reviewlists.get(i);
      viewHolder.review_name.setText(reviewlist.getName());
      viewHolder.review_title.setText(reviewlist.getReviewtitle());
      viewHolder.review_description.setText(reviewlist.getReviewdescriptor());
      viewHolder.review_rating.setText((String.valueOf(reviewlist.getReviewrating())));

    }



    @Override
    public int getItemCount() {
        return reviewlists.size();
    }
   public class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView review_name;
        private TextView review_title;
        private TextView review_description;
        private TextView review_rating;
          private RelativeLayout ll ;


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            ll=(RelativeLayout)itemView.findViewById(R.id.rll);
             review_name=(TextView)itemView.findViewById(R.id.nameofperson);
             review_title=(TextView)itemView.findViewById(R.id.tvname_turf);
             review_description=(TextView)itemView.findViewById(R.id.tvaddress_turf);
             review_rating=(TextView)itemView.findViewById(R.id.textViewRating);

        }
    }
}