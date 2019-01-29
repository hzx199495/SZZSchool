package com.shizhanzhe.szzschool.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zz9527 on 2018/7/4.
 */
public class WXBean {


    /**
     * timeStamp : 1530758192
     * nonceStr : LEcXXzc5oXkrW1kX
     * prepayid : wx0510363261308585f45efe570565788619
     * package : Sign=WXPay
     * sign : A1B2C6511BB3BE3F7C3194E7A76E035F
     */

    private String timeStamp;
    private String nonceStr;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
