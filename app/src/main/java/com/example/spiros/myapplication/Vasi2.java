package com.example.spiros.myapplication;

/**
 * Created by Spiros2 on 7/11/2017.
 */

public class Vasi2 {
    String vasiid;
    String vasilastname;
    String vasifirstname;
    String vasiemail;
    String vasitel;
    String vasirent;
    String vasihouse;
    String vasiaddress;
    String vasicountry;
    String vasitk;
    String vasicomments;

    public Vasi2(){

    }

    public Vasi2(String vasiid, String vasilastname, String vasifirstname, String vasiemail, String vasitel, String vasirent, String vasihouse, String vasiaddress, String vasicountry, String vasitk, String vasicomments) {
        this.vasiid = vasiid;
        this.vasilastname = vasilastname;
        this.vasifirstname = vasifirstname;
        this.vasiemail = vasiemail;
        this.vasitel = vasitel;
        this.vasirent = vasirent;
        this.vasihouse = vasihouse;
        this.vasiaddress = vasiaddress;
        this.vasicountry = vasicountry;
        this.vasitk = vasitk;
        this.vasicomments = vasicomments;
    }

    public String getVasiid() {
        return vasiid;
    }

    public String getVasilastname() {
        return vasilastname;
    }

    public String getVasifirstname() {
        return vasifirstname;
    }

    public String getVasiemail() {
        return vasiemail;
    }

    public String getVasitel() {
        return vasitel;
    }

    public String getVasirent() {
        return vasirent;
    }

    public String getVasihouse() {
        return vasihouse;
    }

    public String getVasiaddress() {
        return vasiaddress;
    }

    public String getVasicountry() {
        return vasicountry;
    }

    public String getVasitk() {
        return vasitk;
    }

    public String getVasicomments() {
        return vasicomments;
    }
}
