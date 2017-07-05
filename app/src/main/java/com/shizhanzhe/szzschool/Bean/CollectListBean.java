package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2016/12/20.
 */

public class CollectListBean {


    /**
     * id : 358
     * userID : 999
     * oid :
     * integralAll : 0
     * integral : 0
     * sourceType : 1
     * addtime : 1496891937
     * sourceID :
     * sHash :
     * coid :
     * isshow : 1
     * systemid : 3
     * type :
     * pid :
     * catid : 2
     * sysinfo : [{"id":"3","stitle":"Swift语言开发入门","thumb":"/var/upload/image/2015/05/20150511100104_57213.jpg","listorder":"1","updatetime":"1429614887","inputtime":"1429614887","picture":"/var/upload/image/2015/05/20150511100104_57213.jpg","introduce":"Swift语言编程的相关概念和基础知识，教学中将Swift和C/OC相关内容进行比较和总结，充分的刨析Swift的语法知识，而且通过Swift 类库和OC类库进行比较让学员能够充分的了解并掌握Swift语法特点、和OC的联系和区别，为学员后续学习和进行Swift相关开发打下坚实的语法基础。","exception":"2,3,4,5","style":"","catid":"3","couClass":"18","sys_hours":"8时16分47秒","keyword":"11Swift语言开发入门","description":"22Swift语言开发入门","l_nanyi":"1","l_jiage":"5","keshi":"1","status":"0","tfm":"0.00","pfm":"0.00","tcid":"0","editor":"","count":"0"}]
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
    private String catid;
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

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public List<SysinfoBean> getSysinfo() {
        return sysinfo;
    }

    public void setSysinfo(List<SysinfoBean> sysinfo) {
        this.sysinfo = sysinfo;
    }

    public static class SysinfoBean {
        /**
         * id : 3
         * stitle : Swift语言开发入门
         * thumb : /var/upload/image/2015/05/20150511100104_57213.jpg
         * listorder : 1
         * updatetime : 1429614887
         * inputtime : 1429614887
         * picture : /var/upload/image/2015/05/20150511100104_57213.jpg
         * introduce : Swift语言编程的相关概念和基础知识，教学中将Swift和C/OC相关内容进行比较和总结，充分的刨析Swift的语法知识，而且通过Swift 类库和OC类库进行比较让学员能够充分的了解并掌握Swift语法特点、和OC的联系和区别，为学员后续学习和进行Swift相关开发打下坚实的语法基础。
         * exception : 2,3,4,5
         * style :
         * catid : 3
         * couClass : 18
         * sys_hours : 8时16分47秒
         * keyword : 11Swift语言开发入门
         * description : 22Swift语言开发入门
         * l_nanyi : 1
         * l_jiage : 5
         * keshi : 1
         * status : 0
         * tfm : 0.00
         * pfm : 0.00
         * tcid : 0
         * editor :
         * count : 0
         */

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
        private String tfm;
        private String pfm;
        private String tcid;
        private String editor;
        private String count;

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

        public String getTfm() {
            return tfm;
        }

        public void setTfm(String tfm) {
            this.tfm = tfm;
        }

        public String getPfm() {
            return pfm;
        }

        public void setPfm(String pfm) {
            this.pfm = pfm;
        }

        public String getTcid() {
            return tcid;
        }

        public void setTcid(String tcid) {
            this.tcid = tcid;
        }

        public String getEditor() {
            return editor;
        }

        public void setEditor(String editor) {
            this.editor = editor;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
