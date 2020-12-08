package com.kawo.museapp.rating;

public class RatingObject {
    float rating1;
    float rating2;
    float rating3;

    public RatingObject(float rating1, float rating2, float rating3){
        this.rating1 = rating1;
        this.rating2 = rating2;
        this.rating3 = rating3;
    }
    public RatingObject(float rating1, float rating3){
        this.rating1 = rating1;

        this.rating3 = rating3;
    }

    public float getRating1() {return rating1;}

    public void setRating1(float rating1) {
        this.rating1 = rating1;
    }

    public float getRating2() {
        return rating2;
    }

    public void setRating2(float rating2) {
        this.rating2 = rating2;
    }

    public float getRating3() {
        return rating3;
    }

    public void setRating3(float rating3) {
        this.rating3 = rating3;
    }
}
