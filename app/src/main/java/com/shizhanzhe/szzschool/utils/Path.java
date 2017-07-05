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
    public static String CHANGE(String uid,String pswd,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.xmm1&pc=1&password="+pswd+"&uid="+uid+"&token="+token;
        return path;
    }
    //首页
    public static String CENTER(String uid,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }

    //我的课程
    public static String MYCLASS(String uid,String token){
        String path="http://shizhanzhe.com/index.php?m=pcdata.app_get_course&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //图片
    public static String IMG(String img){
        String path="http://shizhanzhe.com"+img;
        return path;
    }

    //课程详情
    public static String SECOND(String sid,String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.tx_two_pin&pc=1&sid="+sid+"&uid="+uid+"&token="+token;
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
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_collect1&pc=1&uid="+uid+"&token="+token;
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

    //帖子回复
    public static String FORUMCOMMENTREPLY(String uid,String pid,String fid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+uid+"&rpid=0&pid="+pid+"&fid="+fid+"&token="+token;
        return path;
    }
    //帖子内部评论回复
    public static String FORUMCOMMENTUSERREPLY(String uid,String rpid,String pid,String fid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+uid+"&rpid="+rpid+"&pid="+pid+"&fid="+fid+"&token="+token;
        return path;
    }
    //团购
    public static String TG() {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.showtuangou&pc=1";
        return path;
    }
    //团购
    public static String TGDETAIL(String tuanid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_tgdetail&pc=1&id="+tuanid;
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
    //提问
    public static String SENDQUESTION(String classid,String sid ,String pid,String videoid,String content,String username,String zh,String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.t_question&pc=1&classid="+classid+"&sid="+sid+"&pid="+pid+"&id="+videoid+"&content="+content+"&username="+zh+"&realname="+username+"&uid="+uid+"&token="+token;
        return path;
    }
    //回答
    public static String ANSWERQUESTION(String videoid,String requid,String content,String qid,String quid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.r_question&pc=1&spid="+videoid+"&requid="+requid+"&content="+content+"&qid="+qid+"&quid="+quid+"&token="+token;
        return path;
    }

    //搜索
    public static String SEARCH(String str) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.soutie&pc=1&page=1&search="+str;
        return path;
    }

    //个人资料
    public static String PERSONALDATA(String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.get_user_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }

    //个人资料修改
    public static String PERSONALUPDATE(String uid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.xmember&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //考核题目
    public static String EXAM(String videoID) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.do_exam&pc=1&coid="+videoID;
        return path;
    }
    //考核满分
    public static String EXAMSUCCESS(String videoID,String uid,String sid,String token) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.manfen&pc=1&coid="+videoID+"&uid="+uid+"&sid="+sid+"&token="+token;
        return path;
    }

    //打赏提现
    public static String DSTIXIAN(String money,String zh,String uid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zan_txsubmit&txfee="+money+"&aliaccout="+zh+"&uid="+uid;
        return path;
    }
    //打赏转移
    public static String DSZHUANYI(String money,String uid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zan_zzsubmit&zzfee="+money+"&uid="+uid;
        return path;
    }
    //团购获利提现
    public static String TGTIXIAN(String money,String zh,String uid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_txsubmit&txfee="+money+"&aliaccout="+zh+"&uid="+uid;
        return path;
    }
    //团购获利转移
    public static String TGZHUANYI(String money,String uid) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zzsubmit&zzfee="+money+"&uid="+uid;
        return path;
    }
    //VIP接口
    public static String VIPBUY(String money,String time,String uid) {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&uid="+uid+"&last_time="+time+"&money="+money;
        return path;
    }
    //VIP单价查询接口 获取VIP的年单价
    public static String GETVIP() {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&vip=1";
        return path;
    }
}
