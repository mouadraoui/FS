package com.example.mouadr.fs;

import android.widget.ImageView;

/**
 * Created by mouadr on 19/03/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int img;

    public NavDrawerItem() {

    }





    public String getTitle() {
        return title;
    }
public int getImage(){
    return  img;
}

    public void setImg(int img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}