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
}
