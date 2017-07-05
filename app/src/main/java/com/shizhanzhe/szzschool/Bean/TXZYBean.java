package com.shizhanzhe.szzschool.Bean;

/**
 * Created by zz9527 on 2017/7/3.
 */

public class TXZYBean {

    /**
     * status : 2
     * info : 提现金额大于获利总额或小于100！
     */

    private int status;
    private String info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
