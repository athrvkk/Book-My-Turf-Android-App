package com.nirajkulkani.demo1;

public class turfList {
    private String turf_id ;
    private  String turf_name ;
    private int rating  ;
    private String contact;
    private String address ;
    private int image;
    private double Latitude;
    private  double Longitude;
    private String  info ;
    private int AW;
    private int MW;
    private    int EW;
    private int AWE;
    private int MWE ;
    private   int EWE ;
    private String imageid;





    public turfList(){}

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public turfList(String id , String turf_name, int rating, String contact, String address,double latitude,double longitude,String informatiion,String imageid) {
        this.turf_id=id ;
        this.turf_name = turf_name;
        this.rating = rating;
        this.contact=contact ;
        this.address=address ;
        this.Latitude=latitude ;
        this.Longitude=longitude ;
       this.info=informatiion ;

    }


    public String getTurf_id() {
        return turf_id;
    }

    public void setTurf_id(String turf_id) {
        this.turf_id = turf_id;
    }

    public String getTurf_name() {
        return turf_name;
    }

    public void setTurf_name(String turf_name) {
        this.turf_name = turf_name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAW() {
        return AW;
    }

    public void setAW(int AW) {
        this.AW = AW;
    }

    public int getMW() {
        return MW;
    }

    public void setMW(int MW) {
        this.MW = MW;
    }

    public int getEW() {
        return EW;
    }

    public void setEW(int EW) {
        this.EW = EW;
    }

    public int getAWE() {
        return AWE;
    }

    public void setAWE(int AWE) {
        this.AWE = AWE;
    }

    public int getMWE() {
        return MWE;
    }

    public void setMWE(int MWE) {
        this.MWE = MWE;
    }

    public int getEWE() {
        return EWE;
    }

    public void setEWE(int EWE) {
        this.EWE = EWE;
    }
}
