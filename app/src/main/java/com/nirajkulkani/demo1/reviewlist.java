package com.nirajkulkani.demo1;

public class reviewlist {
   private  String Review_id ;
    String name ;
    String reviewtitle ;
    String reviewdescriptor ;
    int reviewrating ;
    public reviewlist(){}

    public reviewlist(String id,String name, String reviewtitle, String reviewdescriptor, int reviewrating) {
        this.Review_id=id;
        this.name = name;
        this.reviewtitle = reviewtitle;
        this.reviewdescriptor = reviewdescriptor;
        this.reviewrating = reviewrating;
    }

    public String getReview_id() {
        return Review_id;
    }

    public void setReview_id(String review_id) {
        Review_id = review_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewtitle() {
        return reviewtitle;
    }

    public void setReviewtitle(String reviewtitle) {
        this.reviewtitle = reviewtitle;
    }

    public String getReviewdescriptor() {
        return reviewdescriptor;
    }

    public void setReviewdescriptor(String reviewdescriptor) {
        this.reviewdescriptor = reviewdescriptor;
    }

    public int getReviewrating() {
        return reviewrating;
    }

    public void setReviewrating(int reviewrating) {
        this.reviewrating = reviewrating;
    }
}
