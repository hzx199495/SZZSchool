package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/4.
 */

public class QuestionBean {

    /**
     * qinfo : [{"id":"20","askquiz":"很好","uid":"967","uname":"15818441698","classid":"241","sid":"78","pid":"111","coid":"315","inputtime":"2017-03-07 15-15","isdeal":"1","realname":"索颜一笑","istui":"0","help":"4","logo":"https://wx.qlogo.cn/mmopen/Q3auHgzwzM7BNK5nI5QlZxznEK4dia9K2Ubfep25b897rELmHgPf0NtlCJVicfwia8I0n3DRGgPCeAneGO1H7icbrA/0"}]
     * title : 网络推广变现2-掘金思路
     * stitle : 网络推广变现·掘金
     * reply : [{"id":"28","qid":"20","quid":"967","requid":"877","classid":"","sid":"","pid":"","coid":"","inputtime":"2017-07-21 20-13","istime":"","istrue":"0","content":"赞","userType":"2","realname":"般若","username":"18629493394"},{"id":"7","qid":"20","quid":"967","requid":"852","classid":"","sid":"","pid":"","coid":"","inputtime":"2017-03-21 14-22","istime":"","istrue":"0","content":"只是课程的大纲，记住一句话，淘宝客只是一种结果！","userType":"2","realname":"灯火","username":"15655555202"}]
     */

    private String title;
    private String stitle;
    private List<QinfoBean> qinfo;
    private List<ReplyBean> reply;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public List<QinfoBean> getQinfo() {
        return qinfo;
    }

    public void setQinfo(List<QinfoBean> qinfo) {
        this.qinfo = qinfo;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class QinfoBean {
        /**
         * id : 20
         * askquiz : 很好
         * uid : 967
         * uname : 15818441698
         * classid : 241
         * sid : 78
         * pid : 111
         * coid : 315
         * inputtime : 2017-03-07 15-15
         * isdeal : 1
         * realname : 索颜一笑
         * istui : 0
         * help : 4
         * logo : https://wx.qlogo.cn/mmopen/Q3auHgzwzM7BNK5nI5QlZxznEK4dia9K2Ubfep25b897rELmHgPf0NtlCJVicfwia8I0n3DRGgPCeAneGO1H7icbrA/0
         */

        private String id;
        private String askquiz;
        private String uid;
        private String uname;
        private String classid;
        private String sid;
        private String pid;
        private String coid;
        private String inputtime;
        private String isdeal;
        private String realname;
        private String istui;
        private String help;
        private String logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAskquiz() {
            return askquiz;
        }

        public void setAskquiz(String askquiz) {
            this.askquiz = askquiz;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCoid() {
            return coid;
        }

        public void setCoid(String coid) {
            this.coid = coid;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getIsdeal() {
            return isdeal;
        }

        public void setIsdeal(String isdeal) {
            this.isdeal = isdeal;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getIstui() {
            return istui;
        }

        public void setIstui(String istui) {
            this.istui = istui;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class ReplyBean {
        /**
         * id : 28
         * qid : 20
         * quid : 967
         * requid : 877
         * classid :
         * sid :
         * pid :
         * coid :
         * inputtime : 2017-07-21 20-13
         * istime :
         * istrue : 0
         * content : 赞
         * userType : 2
         * realname : 般若
         * username : 18629493394
         */

        private String id;
        private String qid;
        private String quid;
        private String requid;
        private String classid;
        private String sid;
        private String pid;
        private String coid;
        private String inputtime;
        private String istime;
        private String istrue;
        private String content;
        private String userType;
        private String realname;
        private String username;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQid() {
            return qid;
        }

        public void setQid(String qid) {
            this.qid = qid;
        }

        public String getQuid() {
            return quid;
        }

        public void setQuid(String quid) {
            this.quid = quid;
        }

        public String getRequid() {
            return requid;
        }

        public void setRequid(String requid) {
            this.requid = requid;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCoid() {
            return coid;
        }

        public void setCoid(String coid) {
            this.coid = coid;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }

        public String getIstime() {
            return istime;
        }

        public void setIstime(String istime) {
            this.istime = istime;
        }

        public String getIstrue() {
            return istrue;
        }

        public void setIstrue(String istrue) {
            this.istrue = istrue;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
