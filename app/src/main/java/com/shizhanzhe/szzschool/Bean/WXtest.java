package com.shizhanzhe.szzschool.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zz9527 on 2018/7/5.
 */
public class WXtest {

    /**
     * response : {"appid":"wxa8e3fcf40642c20b","noncestr":"pVm4BcIeXUcUFoH9PPLHyCluh65fjsAp","package":"Sign=WXPay","partnerid":"1509113501","prepayid":"wx0510180132084057f2a3ecb04006434039","timestamp":1530757080,"sign":"A65C96AA920272EE65D09C9920618BB3"}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * appid : wxa8e3fcf40642c20b
         * noncestr : pVm4BcIeXUcUFoH9PPLHyCluh65fjsAp
         * package : Sign=WXPay
         * partnerid : 1509113501
         * prepayid : wx0510180132084057f2a3ecb04006434039
         * timestamp : 1530757080
         * sign : A65C96AA920272EE65D09C9920618BB3
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
