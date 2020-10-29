package com.nirajkulkani.demo1;

import android.net.Uri;

public class turfImageUpload
{
    private String name ;
    private String imageid;

    public turfImageUpload(){}

    public turfImageUpload(String name, String imageid) {
        if(name.trim().equals("")){
            name="no name ";
        }
        this.name = name;
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }
}
