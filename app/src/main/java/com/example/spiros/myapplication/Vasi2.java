package com.example.spiros.myapplication;

import com.google.firebase.database.PropertyName;

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
    String vasirent_vasihouse;
    String vasidist;
    String vasiurl;

    public Vasi2(){

    }

    public Vasi2(String vasiid, String vasilastname, String vasifirstname, String vasiemail, String vasitel, String vasirent, String vasihouse, String vasiaddress, String vasicountry, String vasitk, String vasicomments,
                 String vasirent_vasihouse, String vasidist,String vasiurl) {
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
        this.vasirent_vasihouse=vasirent_vasihouse;
        this.vasidist=vasidist;
        this.vasiurl=vasiurl;
    }

    public String getVasidist(){
        return vasidist;
    }

    public String getVasiurl(){return  vasiurl;}

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

    public void setVasidist(String vasidist){
        this.vasidist = vasidist;
    }

    public void setVasiurl(String vasiurl) {this.vasiurl = vasiurl;}

    public void setVasiid(String vasiid) {
        this.vasiid = vasiid;
    }

    public void setVasilastname(String vasilastname) {
        this.vasilastname = vasilastname;
    }

    public void setVasifirstname(String vasifirstname) {
        this.vasifirstname = vasifirstname;
    }

    public void setVasiemail(String vasiemail) {
        this.vasiemail = vasiemail;
    }

    public void setVasitel(String vasitel) {
        this.vasitel = vasitel;
    }

    public void setVasirent(String vasirent) {
        this.vasirent = vasirent;
    }

    public void setVasihouse(String vasihouse) {
        this.vasihouse = vasihouse;
    }

    public void setVasiaddress(String vasiaddress) {
        this.vasiaddress = vasiaddress;
    }

    public void setVasicountry(String vasicountry) {
        this.vasicountry = vasicountry;
    }

    public void setVasitk(String vasitk) {
        this.vasitk = vasitk;
    }

    public void setVasicomments(String vasicomments) {
        this.vasicomments = vasicomments;
    }

    public String getVasirent_vasihouse() {
        return vasirent_vasihouse;
    }

    public void setVasirent_vasihouse(String vasirent_vasihouse) {
        this.vasirent_vasihouse = vasirent_vasihouse;
    }

    public static void clear() {
    }

    @Override
    public String toString() {
        return "Vasi2{" +
                "vasiid='" + vasiid + '\'' +
                ", vasilastname='" + vasilastname + '\'' +
                ", vasifirstname='" + vasifirstname + '\'' +
                ", vasiemail='" + vasiemail + '\'' +
                ", vasitel='" + vasitel + '\'' +
                ", vasirent='" + vasirent + '\'' +
                ", vasihouse='" + vasihouse + '\'' +
                ", vasiaddress='" + vasiaddress + '\'' +
                ", vasicountry='" + vasicountry + '\'' +
                ", vasitk='" + vasitk + '\'' +
                ", vasicomments='" + vasicomments + '\'' +
                ", vasirent_vasihouse='" + vasirent_vasihouse + '\'' +
                ", vasidist='" + vasidist + '\'' +
                ", vasiurl='" + vasiurl + '\'' +
                '}';
    }
}
