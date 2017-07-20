package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/7/14.
 */

public class ProListBean {

    private List<CourseBean> course;
    private List<SysinfoBean> sysinfo;

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
        /**
         * id : 77
         * listorder : 0
         * updatetime : 1461222523
         * inputtime : 1461222523
         * ctitle : 企鹅媒体
         * style :
         * choice_kc : [{"id":"262","name":"QQ群提取好友","sort":"0","mv_url":"d9a628711e944cc26c0073ee8f2d348c_d"},{"id":"290","name":"天涯论坛顶帖机","sort":"0","mv_url":"d9a628711e6c30477b9877decf2fae1d_d"},{"id":"291","name":"豆瓣顶帖机","sort":"0","mv_url":"d9a628711ed9512ab7f1d6ec23e137e1_d"},{"id":"292","name":"猫扑回帖助手","sort":"0","mv_url":"d9a628711ef83a2d2294e7bbe5d978da_d"},{"id":"293","name":"百度贴吧帖子采集器","sort":"0","mv_url":"d9a628711e80d534834f78d6e2351407_d"}]
         * choice_kc_idstr : 262,290,291,292,293
         * systemid : 34
         * thumb : /var/upload/image/2016/05/20160503160613_55082.jpg
         * introduce :
         */

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
        private List<ChoiceKcBean> choice_kc;

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

        public List<ChoiceKcBean> getChoice_kc() {
            return choice_kc;
        }

        public void setChoice_kc(List<ChoiceKcBean> choice_kc) {
            this.choice_kc = choice_kc;
        }

        public static class ChoiceKcBean {
            /**
             * id : 262
             * name : QQ群提取好友
             * sort : 0
             * mv_url : d9a628711e944cc26c0073ee8f2d348c_d
             */

            private String id;
            private String name;
            private String sort;
            private String mv_url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getMv_url() {
                return mv_url;
            }

            public void setMv_url(String mv_url) {
                this.mv_url = mv_url;
            }
        }
    }

    public static class SysinfoBean {
        /**
         * id : 34
         * stitle : 新媒体运营
         * thumb : /var/upload/image/2016/10/20161020164907_50943.jpg
         * listorder : 0
         * updatetime : 1461219300
         * inputtime : 1461219300
         * picture :
         * introduce : QQ群提取好友一款可以批量提取qq群里面所有群成员QQ号码的软件。主要是帮助客户 获得精准潜在客户, 懂得QQ营销的朋友都明白，QQ群成员是精准的潜在客户.
         * exception : 5
         * style :
         * catid : 2
         * couClass : 233
         * sys_hours : 3秒29
         * keyword :
         * description :
         * l_nanyi : 1
         * l_jiage : 5
         * keshi : 1
         * status : 0
         * tfm : 0.00
         * pfm : 100.00
         * tcid : 0
         * editor :
         * nowprice : 10000.00
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
        private String nowprice;

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

        public String getNowprice() {
            return nowprice;
        }

        public void setNowprice(String nowprice) {
            this.nowprice = nowprice;
        }
    }
}
