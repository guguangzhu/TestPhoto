package com.photo.testphoto.bean;

public class ChartDataBean {

    private int price;

    private long date;

    //x坐标
    private float itemX;
    //柱高
    private float itemHeight;


    public float getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getItemX() {
        return itemX;
    }

    public void setItemX(float itemX) {
        this.itemX = itemX;
    }
}
