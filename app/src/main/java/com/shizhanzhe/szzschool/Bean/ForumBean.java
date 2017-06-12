package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2016/12/28.
 */

public class ForumBean {


    private List<LtmodelBean> ltmodel;
    private List<SzanBean> szan;

    public List<LtmodelBean> getLtmodel() {
        return ltmodel;
    }

    public void setLtmodel(List<LtmodelBean> ltmodel) {
        this.ltmodel = ltmodel;
    }

    public List<SzanBean> getSzan() {
        return szan;
    }

    public void setSzan(List<SzanBean> szan) {
        this.szan = szan;
    }

    public static class LtmodelBean {
        /**
         * fid : 57
         * name : 新媒体运营
         * description : 新媒体运营简介
         * systemid : 34
         * imgurl : /var/upload/image/2016/10/20161025092352_91015.jpg
         */

        private String fid;
        private String name;
        private String description;
        private String systemid;
        private String imgurl;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSystemid() {
            return systemid;
        }

        public void setSystemid(String systemid) {
            this.systemid = systemid;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

    public static class SzanBean {
        /**
         * pid : 10
         * tid : 0
         * fid : 58
         * first : 1
         * author : 15655555202
         * authorid : 852
         * subject : 微信淘客实战思维导图
         * content :
         * dateline : 1482393728
         * message :
         * useip :
         * invisible : 0
         * anonymous : 0
         * usesig : 0
         * htmlon : 0
         * bbcodeoff : 0
         * smileyoff : 0
         * parseurloff : 0
         * attachment : 0
         * rate : 0
         * ratetimes : 0
         * status : 0
         * tags : 0
         * comment : 0
         * replycredit : 0
         * position : 0
         * looknum : 124
         * recoverytime : 1483372096
         * alltip : 12
         * is_show : 0
         * lmorder : 0
         * showindex : 0
         * descr :
         * sketch :
         * imgurl :
         * classid :
         * is_showindex :
         * sizecolor :
         * location_p : 陕西省
         * location_c : 西安市
         * typeclass : 2
         * logo : http://wx.qlogo.cn/mmopen/XB40pUV1u88KPq4pTzpBb3ce59Tc5TqjiazwcwcVOWZP96E3vPIAHz9SKt4IaLywuzmGSZiaoZFnMsUoR5AFWPuJwfUzE5EYYl/0
         * realname : 灯火
         */

        private String pid;
        private String tid;
        private String fid;
        private String first;
        private String author;
        private String authorid;
        private String subject;
        private String content;
        private long dateline;
        private String message;
        private String useip;
        private String invisible;
        private String anonymous;
        private String usesig;
        private String htmlon;
        private String bbcodeoff;
        private String smileyoff;
        private String parseurloff;
        private String attachment;
        private String rate;
        private String ratetimes;
        private String status;
        private String tags;
        private String comment;
        private String replycredit;
        private String position;
        private String looknum;
        private String recoverytime;
        private String alltip;
        private String is_show;
        private String lmorder;
        private String showindex;
        private String descr;
        private String sketch;
        private String imgurl;
        private String classid;
        private String is_showindex;
        private String sizecolor;
        private String location_p;
        private String location_c;
        private String typeclass;
        private String logo;
        private String realname;

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorid() {
            return authorid;
        }

        public void setAuthorid(String authorid) {
            this.authorid = authorid;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDateline() {
            return dateline;
        }

        public void setDateline(long dateline) {
            this.dateline = dateline;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUseip() {
            return useip;
        }

        public void setUseip(String useip) {
            this.useip = useip;
        }

        public String getInvisible() {
            return invisible;
        }

        public void setInvisible(String invisible) {
            this.invisible = invisible;
        }

        public String getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(String anonymous) {
            this.anonymous = anonymous;
        }

        public String getUsesig() {
            return usesig;
        }

        public void setUsesig(String usesig) {
            this.usesig = usesig;
        }

        public String getHtmlon() {
            return htmlon;
        }

        public void setHtmlon(String htmlon) {
            this.htmlon = htmlon;
        }

        public String getBbcodeoff() {
            return bbcodeoff;
        }

        public void setBbcodeoff(String bbcodeoff) {
            this.bbcodeoff = bbcodeoff;
        }

        public String getSmileyoff() {
            return smileyoff;
        }

        public void setSmileyoff(String smileyoff) {
            this.smileyoff = smileyoff;
        }

        public String getParseurloff() {
            return parseurloff;
        }

        public void setParseurloff(String parseurloff) {
            this.parseurloff = parseurloff;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRatetimes() {
            return ratetimes;
        }

        public void setRatetimes(String ratetimes) {
            this.ratetimes = ratetimes;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getReplycredit() {
            return replycredit;
        }

        public void setReplycredit(String replycredit) {
            this.replycredit = replycredit;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getLooknum() {
            return looknum;
        }

        public void setLooknum(String looknum) {
            this.looknum = looknum;
        }

        public String getRecoverytime() {
            return recoverytime;
        }

        public void setRecoverytime(String recoverytime) {
            this.recoverytime = recoverytime;
        }

        public String getAlltip() {
            return alltip;
        }

        public void setAlltip(String alltip) {
            this.alltip = alltip;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getLmorder() {
            return lmorder;
        }

        public void setLmorder(String lmorder) {
            this.lmorder = lmorder;
        }

        public String getShowindex() {
            return showindex;
        }

        public void setShowindex(String showindex) {
            this.showindex = showindex;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }

        public String getSketch() {
            return sketch;
        }

        public void setSketch(String sketch) {
            this.sketch = sketch;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getIs_showindex() {
            return is_showindex;
        }

        public void setIs_showindex(String is_showindex) {
            this.is_showindex = is_showindex;
        }

        public String getSizecolor() {
            return sizecolor;
        }

        public void setSizecolor(String sizecolor) {
            this.sizecolor = sizecolor;
        }

        public String getLocation_p() {
            return location_p;
        }

        public void setLocation_p(String location_p) {
            this.location_p = location_p;
        }

        public String getLocation_c() {
            return location_c;
        }

        public void setLocation_c(String location_c) {
            this.location_c = location_c;
        }

        public String getTypeclass() {
            return typeclass;
        }

        public void setTypeclass(String typeclass) {
            this.typeclass = typeclass;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
