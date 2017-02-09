package com.slavian.study.piramida.adapter;

import android.app.ListActivity;

/**
 * Created by Slavian on 1/6/2017.
 */

public class ListItemGood {
    public String description;
    public String price;
    public String about;
    public long good_id;
    public int icon;
    public ListItemGood(){
        super();
    }
    public ListItemGood(String about, String description, String price, int icon, long good_id){
        super();
        this.about=about;
        this.description=description;
        this.price=price;
        this.icon=icon;
        this.good_id=good_id;
    }

}
