package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2016/11/2.
 */
public class MyProBean {

    /**
     * id : 29
     * userID : 849
     * oid : 14779825299453
     * integralAll : 0
     * integral : -1000.00
     * sourceType : 1
     * addtime : 1477982529
     * sourceID :
     * sHash :
     * coid : 0
     * isshow : 1
     * systemid : 77
     * type : 1
     * pid : 0
     * yvip : 0
     * status : 0
     * catid : 2
     * sharetime :
     * shareurl :
     * tuangou :
     * viplast_time : 0
     * wxpay : 0
     * course : [{"id":"88","listorder":"0","updatetime":"1476840375","inputtime":"1476840375","ctitle":"个人QQ","style":"","choice_kc":[],"choice_kc_idstr":"","systemid":"77","thumb":"","introduce":""},{"id":"90","listorder":"0","updatetime":"1476840650","inputtime":"1476840650","ctitle":"企业QQ","style":"","choice_kc":[],"choice_kc_idstr":"","systemid":"77","thumb":"","introduce":""},{"id":"91","listorder":"0","updatetime":"1476840668","inputtime":"1476840668","ctitle":"企点QQ","style":"","choice_kc":[],"choice_kc_idstr":"","systemid":"77","thumb":"","introduce":""},{"id":"89","listorder":"0","updatetime":"1476840467","inputtime":"1476840467","ctitle":"营销QQ","style":"","choice_kc":[],"choice_kc_idstr":"","systemid":"77","thumb":"","introduce":""}]
     * sysinfo : [{"id":"77","stitle":"QQ营销","thumb":"/var/upload/image/2016/10/20161020164712_62245.jpg","listorder":"0","updatetime":"1474532460","inputtime":"1474532460","picture":"","introduce":"uc自媒体","exception":"1","style":"","catid":"2","couClass":"8","sys_hours":"","keyword":"网络推广，个人推广，互联网营销，腾讯营销","description":"摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要","l_nanyi":"1","l_jiage":"5","keshi":"100","status":"0"}]
     */

    private String id;
    private String userID;
    private String oid;
    private String integralAll;
    private String integral;
    private String sourceType;
    private String addtime;
    private String sourceID;
    private String sHash;
    private String coid;
    private String isshow;
    private String systemid;
    private String type;
    private String pid;
    private String yvip;
    private String status;
    private String catid;
    private String sharetime;
    private String shareurl;
    private String tuangou;
    private String viplast_time;
    private String wxpay;
    /**
     * id : 88
     * listorder : 0
     * updatetime : 1476840375
     * inputtime : 1476840375
     * ctitle : 个人QQ
     * style :
     * choice_kc : []
     * choice_kc_idstr :
     * systemid : 77
     * thumb :
     * introduce :
     */

    private List<CourseBean> course;
    /**
     * id : 77
     * stitle : QQ营销
     * thumb : /var/upload/image/2016/10/20161020164712_62245.jpg
     * listorder : 0
     * updatetime : 1474532460
     * inputtime : 1474532460
     * picture :
     * introduce : uc自媒体
     * exception : 1
     * style :
     * catid : 2
     * couClass : 8
     * sys_hours :
     * keyword : 网络推广，个人推广，互联网营销，腾讯营销
     * description : 摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要摘要
     * l_nanyi : 1
     * l_jiage : 5
     * keshi : 100
     * status : 0
     */

    private List<SysinfoBean> sysinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getIntegralAll() {
        return integralAll;
    }

    public void setIntegralAll(String integralAll) {
        this.integralAll = integralAll;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getSHash() {
        return sHash;
    }

    public void setSHash(String sHash) {
        this.sHash = sHash;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getYvip() {
        return yvip;
    }

    public void setYvip(String yvip) {
        this.yvip = yvip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getSharetime() {
        return sharetime;
    }

    public void setSharetime(String sharetime) {
        this.sharetime = sharetime;
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public String getTuangou() {
        return tuangou;
    }

    public void setTuangou(String tuangou) {
        this.tuangou = tuangou;
    }

    public String getViplast_time() {
        return viplast_time;
    }

    public void setViplast_time(String viplast_time) {
        this.viplast_time = viplast_time;
    }

    public String getWxpay() {
        return wxpay;
    }

    public void setWxpay(String wxpay) {
        this.wxpay = wxpay;
    }

    public List<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<CourseBean> course) {
        this.course = course;
    }

    public List<SysinfoBean> getSysinfo() {
        return sysinfo;
    }

    public void setSysinfo(List<SysinfoBean> sysinfo) {
        this.sysinfo = sysinfo;
    }

    public static class CourseBean {
        private String id;
        private String listorder;
        private String updatetime;
        private String inputtime;
        private String ctitle;
        private String style;
        private String choice_kc_idstr;
        private String systemid;
        private String thumb;
        private String introduce;
        private List<?> choice_kc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getCtitle() {
            return ctitle;
        }

        public void setCtitle(String ctitle) {
            this.ctitle = ctitle;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getChoice_kc_idstr() {
            return choice_kc_idstr;
        }

        public void setChoice_kc_idstr(String choice_kc_idstr) {
            this.choice_kc_idstr = choice_kc_idstr;
        }

        public String getSystemid() {
            return systemid;
        }

        public void setSystemid(String systemid) {
            this.systemid = systemid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public List<?> getChoice_kc() {
            return choice_kc;
        }

        public void setChoice_kc(List<?> choice_kc) {
            this.choice_kc = choice_kc;
        }
    }

    public static class SysinfoBean {
        private String id;
        private String stitle;
        private String thumb;
        private String listorder;
        private String updatetime;
        private String inputtime;
        private String picture;
        private String introduce;
        private String exception;
        private String style;
        private String catid;
        private String couClass;
        private String sys_hours;
        private String keyword;
        private String description;
        private String l_nanyi;
        private String l_jiage;
        private String keshi;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getException() {
            return exception;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getCatid() {
            return catid;
        }

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public String getCouClass() {
            return couClass;
        }

        public void setCouClass(String couClass) {
            this.couClass = couClass;
        }

        public String getSys_hours() {
            return sys_hours;
        }

        public void setSys_hours(String sys_hours) {
            this.sys_hours = sys_hours;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getL_nanyi() {
            return l_nanyi;
        }

        public void setL_nanyi(String l_nanyi) {
            this.l_nanyi = l_nanyi;
        }

        public String getL_jiage() {
            return l_jiage;
        }

        public void setL_jiage(String l_jiage) {
            this.l_jiage = l_jiage;
        }

        public String getKeshi() {
            return keshi;
        }

        public void setKeshi(String keshi) {
            this.keshi = keshi;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
