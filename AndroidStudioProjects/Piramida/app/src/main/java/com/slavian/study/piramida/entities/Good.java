package com.slavian.study.piramida.entities;

/**
 * Created by Slavian on 1/7/2017.
 */

public class Good {
    private String category;
    private long good_id;
    private long good_count;
    private String good_name;
    private String price;
    private String description;
    private String icon;
    public Good(){}
    public Good(Good g){
        this.icon=g.icon;
        this.description=g.description;
        this.category=g.category;
        this.good_id=g.good_id;
        this.good_count=g.good_count;
        this.good_name=g.good_name;
        this.price=g.price;
    }
    public Good(String category, long good_id, long good_count, String good_name, String price, String description, String icon){
        super();
        this.icon=icon;
        this.description=description;
        this.category=category;
        this.good_id=good_id;
        this.good_count=good_count;
        this.good_name=good_name;
        this.price=price;
    }

    public long getGood_id() {
        return good_id;
    }

    public void setGood_id(long good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getGood_count() {
        return good_count;
    }

    public void setGood_count(long good_count) {
        this.good_count = good_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
