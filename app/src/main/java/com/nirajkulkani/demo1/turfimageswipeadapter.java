package com.nirajkulkani.demo1;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.List;

public class turfimageswipeadapter extends PagerAdapter
{
//    private int [] turf_image_array={R.drawable.image2,R.drawable.image3};
    private Context context ;
    private List<turfImageUpload> turfImageUploads;
    LayoutInflater layoutInflater ;
    TextView tv;

    public turfimageswipeadapter(Context context,List<turfImageUpload> imageUploads) {
        this.context = context;
        this.turfImageUploads=imageUploads ;
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return turfImageUploads.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        turfImageUpload tf=turfImageUploads.get(position);
        View view=layoutInflater.inflate(R.layout.swiping_turfimages,container,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.turf_images);

        Picasso .with(context).load(tf.getImageid()).placeholder(R.drawable.stadium).into(imageView);

       ViewPager vp=(ViewPager) container;
           container.addView(view);
        return view ;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
