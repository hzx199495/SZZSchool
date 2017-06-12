package com.shizhanzhe.szzschool.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zz9527 on 2017/3/14.
 */
@Table(name="TG")
public class TGsqlBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "tuanid")
    private String tuanid;
    @Column(name = "proid")
    private String proid;
    @Column(name = "img")
    private String img;
    @Column(name = "title")
    private String title;
    @Column(name = "intro")
    private String intro;
    @Column(name = "time")
    private String time;
    @Column(name = "yjprice")
    private String yjprice;
    @Column(name = "tgprice")
    private String tgprice;

    public TGsqlBean() {
    }

    public TGsqlBean(String tuanid, String proid, String img, String title, String intro, String time, String yjprice, String tgprice) {
        this.tuanid = tuanid;
        this.proid = proid;
        this.img = img;
        this.title = title;
        this.intro = intro;
        this.time = time;
        this.yjprice = yjprice;
        this.tgprice = tgprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTuanid() {
        return tuanid;
    }

    public void setTuanid(String tuanid) {
        this.tuanid = tuanid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYjprice() {
        return yjprice;
    }

    public void setYjprice(String yjprice) {
        this.yjprice = yjprice;
    }

    public String getTgprice() {
        return tgprice;
    }

    public void setTgprice(String tgprice) {
        this.tgprice = tgprice;
    }
}
