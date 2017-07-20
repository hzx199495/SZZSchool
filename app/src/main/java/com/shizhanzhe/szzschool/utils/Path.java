package com.shizhanzhe.szzschool.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hasee on 2016/11/16.
 */
public  class Path {
   private  String userzh;
   private  String uid;
   private  String token;
   private  String username;

    public Path(Context context){
        SharedPreferences preferences = context.getSharedPreferences("userjson", Context.MODE_PRIVATE);
        SharedPreferences preferences2 = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        username = preferences.getString("username","");
        userzh = preferences2.getString("uname","");
        uid = preferences.getString("uid","");;
        token = preferences.getString("token","");
    }
    //登陆
    public static String UZER(String username,String pswd){
        String path="https://shizhanzhe.com/index.php?m=pcdata.user_data&pc=1&username="+username+"&password="+pswd;
        return path;
    }
    //注册
    public static String REGISTER(String username,String pswd){
        String path="https://shizhanzhe.com/index.php?m=pcdata.zc&pc=1&username="+username+"&password="+pswd;
        return path;
    }
    //修改密码
    public   String CHANGE(String pswd){
        String path="https://shizhanzhe.com/index.php?m=pcdata.xmm1&pc=1&password="+pswd+"&uid="+uid+"&token="+token;
        return path;
    }

    //修改密码
    public   String FORGET(String zh,String pswd){
        String path="https://shizhanzhe.com/index.php?m=pcdata.xmm&pc=1&username="+zh+"&password="+pswd;
        return path;
    }
    //首页
    public  String CENTER(){
        String path="https://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }

    //我的课程
    public  String MYCLASS(){
        String path="https://shizhanzhe.com/index.php?m=pcdata.app_get_course&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //图片
    public static String IMG(String img){
        String path="https://shizhanzhe.com"+img;
        return path;
    }
    //课程详情
    public  String PROLIST() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.course_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //课程详情
    public  String SECOND(String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.tx_two_pin&pc=1&sid="+sid+"&uid="+uid+"&token="+token;
        return path;
    }
    //评论
    public  String COMMENT(String spid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.question&pc=1&spid=" + spid + "&uid=" + uid + "&token=" + token;
        return path;
    }
    //消费记录
    public  String XF() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xiaofee&pc=1&uid="+uid +"&token="+token;
        return path;
    }
    //消费记录
    public  String DelXF(String id) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.del_xiaofee&pc=1&uid="+id+"&id="+uid+"&token="+token;
        return path;
    }
    //收藏
    public  String COLLECT(String id) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.collect_sys&pc=1&uid="+uid+"&systemid="+id+"&token="+token;
        return path;
    }
    //取消收藏
    public  String DELCOLLECT(String id) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.del_collect&pc=1&uid="+uid+"&id="+id+"&token="+token;
        return path;
    }
    //收藏列表
    public  String COLLECTLIST() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_collect1&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //论坛首页
    public static String FORUMHOME() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltmodel&pc=1";
        return path;
    }
    //论坛版块
    public static String FORUMBK(String fid,int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltpost&pc=1&fid="+fid+"&page="+page;
        return path;
    }
    //帖子内容
    public static String FORUMCONTENT(String pid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_postda&pc=1&pid="+pid;
        return path;
    }
    //帖子评论
    public static String FORUMCOMMENT(String pid,int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_tzdata&pc=1&pid="+pid+"&page="+page;
        return path;
    }

    //帖子回复
    public  String FORUMCOMMENTREPLY(String pid,String fid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+uid+"&rpid=0&pid="+pid+"&fid="+fid+"&token="+token;
        return path;
    }
    //帖子内部评论回复
    public  String FORUMCOMMENTUSERREPLY(String rpid,String pid,String fid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid="+uid+"&rpid="+rpid+"&pid="+pid+"&fid="+fid+"&token="+token;
        return path;
    }
    //团购列表
    public static String TG(int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.showtuangou&pc=1&page="+page;
        return path;
    }
    //团购详情
    public static String TGDETAIL(String tuanid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_tgdetail&pc=1&id="+tuanid;
        return path;
    }
    //我的开团
    public  String MYKT() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.mykaituan&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //我的参团
    public  String MYCT() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.mycantuan&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //提问
    public  String SENDQUESTION(String classid,String sid ,String pid,String videoid,String content) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.t_question&pc=1&classid="+classid+"&sid="+sid+"&pid="+pid+"&id="+videoid+"&content="+content+"&username="+userzh+"&realname="+username+"&uid="+uid+"&token="+token;
        return path;
    }
    //回答
    public  String ANSWERQUESTION(String videoid,String requid,String content,String qid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.r_question&pc=1&spid="+videoid+"&requid="+requid+"&content="+content+"&qid="+qid+"&quid="+uid+"&token="+token;
        return path;
    }

    //搜索
    public static String SEARCH(String str) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.soutie&pc=1&page=1&search="+str;
        return path;
    }

    //个人资料
    public  String PERSONALDATA() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_user_data&pc=1&uid="+uid+"&token="+token;
        return path;
    }

    //个人资料修改
    public  String PERSONALUPDATE() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xmember&pc=1&uid="+uid+"&token="+token;
        return path;
    }
    //考核题目
    public static String EXAM(String videoID) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.do_exam&pc=1&coid="+videoID;
        return path;
    }
    //考核满分
    public  String EXAMSUCCESS(String videoID,String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.manfen&pc=1&coid="+videoID+"&uid="+uid+"&sid="+sid+"&token="+token;
        return path;
    }

    //打赏提现
    public  String DSTIXIAN(String money) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.app_zan_txsubmit&txfee="+money+"&aliaccout="+userzh+"&uid="+token;
        return path;
    }
    //打赏转移
    public  String DSZHUANYI(String money) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zan_zzsubmit&zzfee="+money+"&uid="+uid;
        return path;
    }
    //团购获利提现
    public  String TGTIXIAN(String money) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.app_txsubmit&txfee="+money+"&aliaccout="+userzh+"&uid="+token;
        return path;
    }
    //团购获利转移
    public  String TGZHUANYI(String money) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zzsubmit&zzfee="+money+"&uid="+uid;
        return path;
    }
    //VIP接口
    public  String VIPBUY(String money,String time) {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&uid="+uid+"&last_time="+time+"&money="+money;
        return path;
    }
    //VIP单价查询接口 获取VIP的年单价
    public static String GETVIP() {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&vip=1";
        return path;
    }
    //补齐团费
    public  String TGMONEY(String tid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.buqi&pc=1&uid="+uid+"&ctid="+tid+"&token="+token;
        return path;
    }

}
