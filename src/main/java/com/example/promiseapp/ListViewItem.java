package com.example.promiseapp;
import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable poster;
    private String korname;
    private String engname;


    public void setPoster(Drawable icon) {
        poster = icon ;
    }
    public void setKorname(String title) {
        korname = title ;
    }
    public void setEngname(String desc) { engname = desc ; }

    public Drawable getPoster() {
        return this.poster ;
    }
    public String getKorname() {
        return this.korname ;
    }
    public String getEngname() { return this.engname ; }

}
