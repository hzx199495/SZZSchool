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
    @Column(name = "proname")
    private  String proname;
    @Column(name = "url")
    private String url;
    @Column(name = "pid")
    private String pid;
    @Column(name = "sid")
    private String sid;
    @Column(name = "spid")
    private String spid;

    public CollectBean() {
    }

    public CollectBean(String proname, String url, String pid, String sid, String spid) {
        this.proname = proname;
        this.url = url;
        this.pid = pid;
        this.sid = sid;
        this.spid = spid;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", proname='" + proname + '\'' +
                ", url='" + url + '\'' +
                ", pid='" + pid + '\'' +
                ", sid='" + sid + '\'' +
                ", spid='" + spid + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }
}
