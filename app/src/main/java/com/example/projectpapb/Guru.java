package com.example.projectpapb;

public class Guru {
    private String nama;
    private String mapel;
    String mFullname, mEmail, mPassword, mUlangipassword;

    public Guru(String mFullname, String mEmail, String mPassword, String mUlangipassword) {
        this.mFullname = mFullname;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mUlangipassword = mUlangipassword;
    }

    public Guru(){

    }
    public Guru(String nama, String mapel){
        this.nama = nama;
        this.mapel = mapel;
    }

    public String getmFullname() {
        return mFullname;
    }

    public void setmFullname(String mFullname) {
        this.mFullname = mFullname;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUlangipassword() {
        return mUlangipassword;
    }

    public void setmUlangipassword(String mUlangipassword) {
        this.mUlangipassword = mUlangipassword;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }
}
