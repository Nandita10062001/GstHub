package com.example.gsthub.Forum;

public class Post {
    String pId, pDescr, pImage, pTime, pName, pEmail, uid, pDp, pTitle;

    public Post() {}

    public Post(String pId,String pDescr, String pImage, String pTime, String pName, String pEmail, String uid, String pDp, String pTitle) {
        this.pId = pId;
        this.pDescr = pDescr;
        this.pImage = pImage;
        this.pTime = pTime;
        this.pName = pName;
        this.pEmail = pEmail;
        this.uid = uid;
        this.pDp = pDp;
        this.pTitle = pTitle;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDp() {
        return pDp;
    }

    public void setpDp(String pDp) {
        this.pDp = pDp;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
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
