package com.example.gsthub.Forum;

public class Post {
    String pId, pDescrp, pImage, pTime, pName, pEmail, uid;

    public Post() {}

    public Post(String pId,String pDescrp, String pImage, String pTime, String pName, String pEmail, String uid) {
        this.pId = pId;
        this.pDescrp = pDescrp;
        this.pImage = pImage;
        this.pTime = pTime;
        this.pName = pName;
        this.pEmail = pEmail;
        this.uid = uid;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpDescrp() {
        return pDescrp;
    }

    public void setpDescrp(String pDescrp) {
        this.pDescrp = pDescrp;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
