package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2017/1/3.
 */

public class ForumCommentBean {

    /**
     * id : 46
     * tid : 10
     * pid : 58
     * author : 晓亮
     * authorid : 897
     * dateline : 1482393928
     * comment : 前排沙发点位！
     * score : 0
     * useip :
     * rpid : 0
     * uid : 0
     * classid : 0
     * mood : 0
     * poststatus : 0
     * man_reply : [{"id":"50","tid":"10","pid":"58","author":"灯火","authorid":"852","dateline":"1482395356","comment":"沙发很正确呀！！！！！！","score":"0","useip":"","rpid":"46","uid":"0","classid":"0","mood":"0","poststatus":"0"}]
     * logo : http://wx.qlogo.cn/mmopen/6tOpeHFIAJY8XT40C5VZyuaiabYAmKwhRtzkIg9Fh7psXmtQBS3mRWXPtakcEIHxicH0Drxw2wovEhkKphQzspfsibFNyjexibGt/0
     * num : 12
     */

    private String id;
    private String tid;
    private String pid;
    private String author;
    private String authorid;
    private long dateline;
    private String comment;
    private String score;
    private String useip;
    private String rpid;
    private String uid;
    private String classid;
    private String mood;
    private String poststatus;
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

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
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
         * id : 50
         * tid : 10
         * pid : 58
         * author : 灯火
         * authorid : 852
         * dateline : 1482395356
         * comment : 沙发很正确呀！！！！！！
         * score : 0
         * useip :
         * rpid : 46
         * uid : 0
         * classid : 0
         * mood : 0
         * poststatus : 0
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
    }
}
