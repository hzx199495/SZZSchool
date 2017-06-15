package com.shizhanzhe.szzschool.utils;

/**
 * Created by hasee on 2016/11/16.
 */
public  class Path {
    //登陆
    public static String UZER(String username,String pswd){
        String path="http://shizhanzhe.com/index.php?m=pcdata.user_data&pc=1&username="+username+"&password="+pswd;
        return path;
    }
    //注册
    public static String REGISTER(String username,String pswd){
        String path="http://shizhanzhe.com/index.php?m=pcdata.zc&pc=1&username="+username+"&password="+pswd;
        return path;
    }
    //修改密码
    public static String CHANGE(String username,String pswd){
        String path="http://shizhanzhe.com/index.php?m=pcdata.xmm&pc=1&username="+username+"&password="+pswd;
        return path;
    }
    //首页
    public static String CENTER(String uid,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //分类
    public static String CLASSIFY(String uid,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //我的课程
    public static String MYCLASS(String uid,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //图片
    public static String IMG(String img){
        String path="http://shizhanzhe.com"+img;
        return path;
    }
    //一级标题
    public static String FIRST(String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }
    //二级标题
    public static String SECOND(String sid,String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.vip_course2_data&pc=1&sid="+sid+"&uid=" + uid + "&token=" + token;
        return path;
    }
    //评论
    public static String COMMENT(String spid,String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.question&pc=1&spid=" + spid + "&uid=" + uid + "&token=" + token;
        return path;
    }
    //消费记录
    public static String XF(String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.xiaofee&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //消费记录
    public static String DelXF(String uid,String id,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.del_xiaofee&pc=1&uid="+uid+"&id="+id+"&token="+token;
        return path;
    }
    //收藏
    public static String COLLECT(String uid,String id,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.collect_sys&pc=1&uid="+uid+"&systemid="+id+"&token="+token;
        return path;
    }
    //取消收藏
    public static String DELCOLLECT(String uid,String id,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.del_collect&pc=1&uid="+uid+"&id="+id+"&token="+token;
        return path;
    }
    //收藏列表
    public static String COLLECTLIST(String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_collect&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //论坛首页
    public static String FORUMHOME() {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_ltmodel&pc=1";
        return path;
    }
    //论坛版块
    public static String FORUMBK(String fid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_ltpost&pc=1&fid="+fid+"&page=1";
        return path;
    }
    //帖子内容
    public static String FORUMCONTENT(String pid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_postda&pc=1&pid="+pid;
        return path;
    }
    //帖子评论
    public static String FORUMCOMMENT(String pid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_tzdata&pc=1&pid="+pid+"&page=1";
        return path;
    }
    //团购
    public static String TG() {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.showtuangou&pc=1";
        return path;
    }
    //我的开团
    public static String MYKT(String id,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.mykaituan&pc=1&uid="+id+"&token="+token;
        return path;
    }
    //我的参团
    public static String MYCT(String id,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.mycantuan&pc=1&uid="+id+"&token="+token;
        return path;
    }
    public static String SENDQUESTION(String classid,String sid ,String pid,String videoid,String content,String username,String zh,String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.t_question&pc=1&classid="+classid+"&sid="+sid+"&pid="+pid+"&id="+videoid+"&content="+content+"&username="+zh+"&realname="+username+"&uid="+uid+"&token="+token;
        return path;
    }
    public static String ANSWERQUESTION(String videoid,String requid,String content,String qid,String quid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.r_question&pc=1&spid="+videoid+"&requid="+requid+"&content="+content+"&qid="+qid+"&quid="+quid+"&token="+token;
        return path;
    }
}
