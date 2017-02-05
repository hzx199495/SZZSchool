package com.shizhanzhe.szzschool.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hasee on 2016/12/14.
 */
@Table(name="Collect")
public class CollectBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "collectId")
    private  String collectId;
    @Column(name = "proId")
    private  String proId;
    @Column(name = "proname")
    private  String proname;
    @Column(name = "img")
    private String img;
    @Column(name = "intro")
    private String intro;


    public CollectBean() {
    }

    public CollectBean(String collectId, String proId, String proname, String img, String intro) {
        this.collectId = collectId;
        this.proId = proId;
        this.proname = proname;
        this.img = img;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
