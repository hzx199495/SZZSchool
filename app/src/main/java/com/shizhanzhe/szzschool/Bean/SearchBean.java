package com.shizhanzhe.szzschool.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hasee on 2016/12/12.
 */
@Table(name="Search")
public class SearchBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "proid")
    private String proid;
    @Column(name = "img")
    private String img;
    @Column(name = "title")
    private String title;
    @Column(name = "intro")
    private String intro;
    @Column(name = "catid")
    private String catid;


    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public SearchBean() {
    }

    public SearchBean(String proid, String img, String title, String intro, String catid) {
        this.proid = proid;
        this.img = img;
        this.title = title;
        this.intro = intro;
        this.catid = catid;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "id=" + id +
                ", proid='" + proid + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", Catid='" + catid + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
