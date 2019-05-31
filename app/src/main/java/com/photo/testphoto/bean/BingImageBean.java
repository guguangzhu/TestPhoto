package com.photo.testphoto.bean;

import java.io.Serializable;
import java.util.List;

public class BingImageBean implements Serializable {


    /**
     * startdate : 20190512
     * fullstartdate : 201905121600
     * enddate : 20190513
     * url : /th?id=OHR.PineLogSP_ZH-CN1105763820_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
     * urlbase : /th?id=OHR.PineLogSP_ZH-CN1105763820
     * copyright : 松木国家森林，佛罗里达州 (© plainpicture/Cavan Images)
     * copyrightlink : http://www.bing.com/search?q=%E6%9D%BE%E6%9C%A8%E5%9B%BD%E5%AE%B6%E6%A3%AE%E6%9E%97&form=hpcapt&mkt=zh-cn
     * title :
     * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20190512_PineLogSP%22&FORM=HPQUIZ
     * wp : true
     * hsh : aebedcd4eec1b8f7d3d256fbbc5a3a16
     * drk : 1
     * top : 1
     * bot : 1
     * hs : []
     */

    private String startdate;
    private String fullstartdate;
    private String enddate;
    private String url;
    private String urlbase;
    private String copyright;
    private String copyrightlink;
    private String title;
    private String quiz;
    private boolean wp;
    private String hsh;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getFullstartdate() {
        return fullstartdate;
    }

    public void setFullstartdate(String fullstartdate) {
        this.fullstartdate = fullstartdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlbase() {
        return urlbase;
    }

    public void setUrlbase(String urlbase) {
        this.urlbase = urlbase;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyrightlink() {
        return copyrightlink;
    }

    public void setCopyrightlink(String copyrightlink) {
        this.copyrightlink = copyrightlink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public boolean isWp() {
        return wp;
    }

    public void setWp(boolean wp) {
        this.wp = wp;
    }

    public String getHsh() {
        return hsh;
    }

    public void setHsh(String hsh) {
        this.hsh = hsh;
    }
}
