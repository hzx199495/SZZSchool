package com.shizhanzhe.szzschool.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hasee on 2017/1/9.
 */
@Table(name="Update")
public class UpdateBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "Version")
    private  double Version;

    public UpdateBean() {
    }

    public UpdateBean(double version) {
        Version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVersion() {
        return Version;
    }

    public void setVersion(double version) {
        Version = version;
    }
}
