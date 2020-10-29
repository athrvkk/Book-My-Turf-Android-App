package com.nirajkulkani.demo1;

public class BookedList {
    String date ;
    String time;
    String price ;
    String contact1 ;
    String name1;
    String status ;
    public BookedList(){}

    public BookedList(String date1, String timeslot, String price, String contact1, String name1, String status) {
        this.date=date1;
        this.time = timeslot;
        this.price = price;
        this.contact1 = contact1;
        this.name1 = name1;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date1) {
        this.date = date1;
    }

    public String time() {
        return time;
    }

    public void setTime(String timeslot) {
        time = timeslot;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
