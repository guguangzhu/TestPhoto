package com.photo.testphoto.bean.base;

import java.io.Serializable;

public class BaseBean<T> implements Serializable {


    private int count;
    private int start;
    private int total;
    private String title;

    private T subjects;


    private T images;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }


    public T getImages() {
        return images;
    }

    public void setImages(T images) {
        this.images = images;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("title=" + title + " count=" + count + " start=" + start);
        if (null != subjects) {
            sb.append(" subjects:" + subjects.toString());
        }
        if (null != images) {
            sb.append(" images:" + subjects.toString());
        }
        return sb.toString();
    }
}
