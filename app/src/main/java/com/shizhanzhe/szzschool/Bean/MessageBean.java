package com.shizhanzhe.szzschool.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by hasee on 2016/12/21.
 */
@Table(name="Message")
public class MessageBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "time")
    private  String time;
    @Column(name = "type")
    private  String type;

}
