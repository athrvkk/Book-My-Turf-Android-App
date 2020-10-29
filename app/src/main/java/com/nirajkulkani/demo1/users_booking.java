package com.nirajkulkani.demo1;

public class users_booking {
   private  String dt ;
    private   String t ;
    private  String price;
    private  String turf_name1  ;
    private String bookingid ;
    private String turfid ;
public users_booking(){}
    public users_booking(String dt, String t, String price, String turf_name1,String bookingid,String turfid) {
        this.dt = dt;
        this.t = t;
        this.price = price;
        this.turf_name1 = turf_name1;
        this.bookingid=bookingid ;
        this.turfid=turfid ;
    }

    public String getTurfid() {
        return turfid;
    }

    public void setTurfid(String turfid) {
        this.turfid = turfid;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTurf_name1() {
        return turf_name1;
    }

    public void setTurf_name1(String turf_name1) {
        this.turf_name1 = turf_name1;
    }
}
