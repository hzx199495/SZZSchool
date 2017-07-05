package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2017/1/3.
 */

public class ForumCommentBean {


    /**
     * id : 246
     * tid : 45
     * pid : 59
     * author : 我也是醉了
     * authorid : 987
     * dateline : 2017-02-28
     * comment : 求微帮详细广告文案和话术
     * score : 0
     * useip :
     * rpid : 0
     * uid : 0
     * classid : 0
     * mood : 0
     * poststatus : 0
     * image :
     * man_reply : [{"id":"272","tid":"45","pid":"59","author":"灯火","authorid":"852","dateline":"2017-03-02","comment":" 发现淘宝一个内部的购物方法，一般情况下，你看到的宝贝，都能一折拿到，想要在淘宝低价购物的朋友，可以扫下面的二维码加好友，给你提供内部渠道！！！！ 配图就是很高大上的东西，低价购买到的！","score":"0","useip":"","rpid":"246","uid":"0","classid":"0","mood":"0","poststatus":"0","image":""}]
     * logo : http://wx.qlogo.cn/mmopen/XB40pUV1u88mYapno0m1wsC2AXJuazg8Ip387YmCMicFDr45hQ59tZdL5J2mhCVrDSWCWQkXIFwwCbtXhWkmx8OVPiceOO9BGib/0
     * num : 75
     */

    private String id;
    private String tid;
    private String pid;
    private String author;
    private String authorid;
    private String dateline;
    private String comment;
    private String score;
    private String useip;
    private String rpid;
    private String uid;
    private String classid;
    private String mood;
    private String poststatus;
    private String image;
    private String logo;
    private String num;
    private List<ManReplyBean> man_reply;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUseip() {
        return useip;
    }

    public void setUseip(String useip) {
        this.useip = useip;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPoststatus() {
        return poststatus;
    }

    public void setPoststatus(String poststatus) {
        this.poststatus = poststatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<ManReplyBean> getMan_reply() {
        return man_reply;
    }

    public void setMan_reply(List<ManReplyBean> man_reply) {
        this.man_reply = man_reply;
    }

    public static class ManReplyBean {
        /**
         * id : 272
         * tid : 45
         * pid : 59
         * author : 灯火
         * authorid : 852
         * dateline : 2017-03-02
         * comment :  发现淘宝一个内部的购物方法，一般情况下，你看到的宝贝，都能一折拿到，想要在淘宝低价购物的朋友，可以扫下面的二维码加好友，给你提供内部渠道！！！！ 配图就是很高大上的东西，低价购买到的！
         * score : 0
         * useip :
         * rpid : 246
         * uid : 0
         * classid : 0
         * mood : 0
         * poststatus : 0
         * image :
         */

        private String id;
        private String tid;
        private String pid;
        private String author;
        private String authorid;
        private String dateline;
        private String comment;
        private String score;
        private String useip;
        private String rpid;
        private String uid;
        private String classid;
        private String mood;
        private String poststatus;
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
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

        public String getDateline() {
            return dateline;
        }

        public void setDateline(String dateline) {
            this.dateline = dateline;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getUseip() {
            return useip;
        }

        public void setUseip(String useip) {
            this.useip = useip;
        }

        public String getRpid() {
            return rpid;
        }

        public void setRpid(String rpid) {
            this.rpid = rpid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public String getPoststatus() {
            return poststatus;
        }

        public void setPoststatus(String poststatus) {
            this.poststatus = poststatus;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
