package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2016/12/1.
 */

public class CommentBean {

    /**
     * username : 不见了
     * id : 1
     * uid : 840
     * logo : http://wx.qlogo.cn/mmopen/ajNVdqHZLLCMOju7JmzRcpaUOGc8Ric1xAPPIibVU6scNk6jIJbHUfsUIlYJOo0Y3YuibewVMD35ocdh3wvkmAdRA/0
     * status : 未解决
     * content : 怎么用呢
     * addtime : 1475907797
     * classid : 8
     * sid : 34
     * pid : 77
     * reply : [{"content":"阿达","username":"逸港","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1477456212"},{"content":"请问","username":"逸港","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1477456232"},{"content":"我问问","username":"逸港","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1477456272"},{"content":"啊实打实的","username":"逸港","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1477456299"},{"content":"98","username":"逸港","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1477459946"},{"content":"1111","username":"潇洒走一回","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1478507137"},{"content":"1条条的 阿杜而对方 多好的额的的打得好快d 的 大大好的 ","username":"潇洒走一回","logo":"/var/upload/image/2016/04/20150424105449_35518.png","addtime":"1478507216"}]
     */

    private String username;
    private String id;
    private String uid;
    private String logo;
    private String status;
    private String content;
    private  Long addtime;
    private String classid;
    private String sid;
    private String pid;
    private List<ReplyBean> reply;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public  Long getAddtime() {
        return addtime;
    }

    public void setAddtime( Long addtime) {
        this.addtime = addtime;
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

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class ReplyBean {
        /**
         * content : 阿达
         * username : 逸港
         * logo : /var/upload/image/2016/04/20150424105449_35518.png
         * addtime : 1477456212
         */

        private String content;
        private String username;
        private String logo;
        private Long addtime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Long getAddtime() {
            return addtime;
        }

        public void setAddtime(Long addtime) {
            this.addtime = addtime;
        }
    }
}
