package com.example.himanshu.rentbasera;

import java.io.Serializable;

/**
 * Created by himanshu on 5/9/2017.
 */

public class PG  implements Serializable
{

    private String pgname;
    private String location;
    private String imageurl;

    public String getAvailable_for() {
        return available_for;
    }

    public void setAvailable_for(String available_for) {
        this.available_for = available_for;
    }

    public int getNo_of_beds() {
        return no_of_beds;
    }

    public void setNo_of_beds(int no_of_beds) {
        this.no_of_beds = no_of_beds;
    }

     private String available_for;

    public String getPgArea() {
        return pgArea;
    }

    public void setPgArea(String pgArea) {
        this.pgArea = pgArea;
    }

    private String pgArea;
    private int pgid;
    private int no_of_rooms;
    private int no_of_beds;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private int amount;

    public PG(){}

    public PG(int pgid,String pgname,int no_of_rooms,String location,String imageurl,int no_of_beds,String available_for,int amount,String pgArea)
    {
        this.pgid=pgid;
        this.pgname=pgname;
        this.no_of_rooms=no_of_rooms;
        this.location=location;
        this.imageurl=imageurl;
        this.no_of_beds=no_of_beds;
        this.available_for=available_for;
        this.amount=amount;
        this.pgArea=pgArea;
    }

    public int getNo_of_rooms() {
        return no_of_rooms;
    }

    public void setNo_of_rooms(int no_of_rooms) {
        this.no_of_rooms = no_of_rooms;
    }

    public int getPgid() {
        return pgid;
    }

    public void setPgid(int pgid) {
        this.pgid = pgid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPgname() {
        return pgname;
    }

    public void setPgname(String pgname) {
        this.pgname = pgname;
    }




}
