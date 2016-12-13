package com.shizhanzhe.szzschool.Bean;

/**
 * Created by hasee on 2016/12/7.
 */

public class PayBean {

    /**
     * status : 1
     * info : 下单成功！
     * order : 14811034953638
     * price : 0.01
     * img_url : http://paysdk.weixin.qq.com/example/qrcode.php?data=weixin%3A%2F%2Fwxpay%2Fbizpayurl%3Fpr%3DBdpXnbN
     */

    private int status;
    private String info;
    private long order;
    private double price;
    private String img_url;

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

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
