package com.shizhanzhe.szzschool.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hasee on 2016/11/16.
 */
public  class Path {
    private String userzh;
    private String uid;
    private String token;
    private String username;

    public Path(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("userjson", Context.MODE_PRIVATE);
        username = preferences.getString("username", "");
        userzh = preferences.getString("uname", "");
        uid = preferences.getString("uid", "");
        ;
        token = preferences.getString("token", "");
    }

    //登陆
    public static String UZER(String username, String pswd) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.user_data&pc=1&username=" + username + "&password=" + pswd;
        return path;
    }

    //注册
    public static String REGISTER(String username, String pswd) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.zc&pc=1&username=" + username + "&password=" + pswd;
        return path;
    }

    //注册验证码
    public static String REGISTERCODE(String code, String yuliu, String phone) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.alisms&pc=1&code=" + code + "&yuliu=" + yuliu + "&phone=" + phone;
        return path;
    }

    //修改密码
    public String CHANGE(String pswd) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xmm1&pc=1&password=" + pswd + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //忘记密码验证码
    public static String FORGETCODE(String code, String yuliu, String phone) {
        String path = " https://shizhanzhe.com/index.php?m=pcdata.zhmmsms&pc=1&code=" + code + "&yuliu=" + yuliu + "&phone=" + phone;
        return path;
    }

    //修改密码
    public String FORGET(String zh, String pswd) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xmm&pc=1&username=" + zh + "&password=" + pswd;
        return path;
    }

    //首页
    public String CENTER() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.vip_course_data&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //我的课程
    public String MYCLASS() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.app_get_course&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //图片
    public static String IMG(String img) {
        String path = "https://shizhanzhe.com" + img;
        return path;
    }

    //课程详情
    public String SECOND(String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.tx_two_pin1&pc=1&sid=" + sid + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //评论
    public String COMMENT(String spid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.question&pc=1&spid=" + spid + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //消费记录
    public String XF() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xiaofee&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //收藏
    public String COLLECT(String id) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.collect_sys&pc=1&uid=" + uid + "&systemid=" + id + "&token=" + token;
        return path;
    }

    //取消收藏
    public String DELCOLLECT(String id) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.del_collect&pc=1&uid=" + uid + "&id=" + id + "&token=" + token;
        return path;
    }

    //收藏列表
    public String COLLECTLIST() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_collect1&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //论坛首页
    public static String FORUMHOME() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltmodel&pc=1";
        return path;
    }

    //论坛版块
    public static String FORUMBK(String fid, int page, int type) {
        if (type == 1) {
            String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltpost&pc=1&fid=" + fid + "&page=" + page;
            return path;
        } else if (type == 2) {
            String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltpost&pc=1&fid=" + fid + "&page=" + page + "&typeclass=3";
            return path;
        }
        return "";
    }

    //帖子内容
    public static String FORUMCONTENT(String pid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_postda&pc=1&pid=" + pid;
        return path;
    }

    //帖子评论
    public static String FORUMCOMMENT(String pid, int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_tzdata&pc=1&pid=" + pid + "&page=" + page;
        return path;
    }

    //帖子回复
    public String FORUMCOMMENTREPLY(String pid, String fid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid=" + uid + "&rpid=0&pid=" + pid + "&fid=" + fid + "&token=" + token;
        return path;
    }

    //帖子内部评论回复
    public String FORUMCOMMENTUSERREPLY(String rpid, String pid, String fid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.huifu_pl&pc=1&uid=" + uid + "&rpid=" + rpid + "&pid=" + pid + "&fid=" + fid + "&token=" + token;
        return path;
    }

    //团购列表
    public static String TG(int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.showtuangou&pc=1&page=" + page;
        return path;
    }

    //团购详情
    public static String TGDETAIL(String tuanid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_tgdetail&pc=1&id=" + tuanid;
        return path;
    }

    //开团
    public String TGKT(String tuanid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.kaituan&pc=1&tid=" + tuanid + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //参团
    public String TGCT(String ktid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.cantuan&pc=1&ktid=" + ktid + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //我的开团
    public String MYKT() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.mykaituan&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //我的参团
    public String MYCT() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.mycantuan&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //提问
    public String SENDQUESTION(String classid, String sid, String pid, String videoid, String content) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.t_question&pc=1&classid=" + classid + "&sid=" + sid + "&pid=" + pid + "&id=" + videoid + "&content=" + content + "&username=" + userzh + "&realname=" + username + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //回答
    public String ANSWERQUESTION(String videoid, String requid, String content, String qid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.r_question&pc=1&spid=" + videoid + "&requid=" + requid + "&content=" + content + "&qid=" + qid + "&quid=" + uid + "&token=" + token;
        return path;
    }

    //问答中心
    public String QUESTION_CONTENT_PATH() {
        String path = "http://shizhanzhe.com/index.php?" +
                "m=pcdata.reply_tx&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //问答页
    public String QUESTION_PAGE_PATH() {
        String path = "https://www.shizhanzhe.com/?m=pcdata.reply_index&pc=1" +
                "&order=1&coid=315&uid=" + uid + "&token=" + token + "&page=1";
        return path;
    }

    //问题总数
    public static String QUESTION_NUMBER_PATH() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.reply_num&pc=1";
        return path;
    }

    //搜索
    public static String SEARCH(String str) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.soutie&pc=1&page=1&search=" + str;
        return path;
    }

    //个人资料
    public String PERSONALDATA() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_user_data&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //个人资料修改
    public String PERSONALUPDATE() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.xmember&pc=1&uid=" + uid + "&token=" + token;
        return path;
    }

    //考核题目
    public static String EXAM(String videoID) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.do_exam&pc=1&coid=" + videoID;
        return path;
    }

    //考核满分
    public String EXAMSUCCESS(String videoID, String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.manfen&pc=1&coid=" + videoID + "&uid=" + uid + "&sid=" + sid + "&token=" + token;
        return path;
    }

    //打赏提现
    public String DSTIXIAN(String money) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.app_zan_txsubmit&txfee=" + money + "&aliaccout=" + userzh + "&uid=" + uid;
        return path;
    }

    //打赏转移
    public String DSZHUANYI(String money) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zan_zzsubmit&zzfee=" + money + "&uid=" + uid;
        return path;
    }

    //团购获利提现
    public String TGTIXIAN(String money) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.app_txsubmit&txfee=" + money + "&aliaccout=" + userzh + "&uid=" + uid;
        return path;
    }

    //团购获利转移
    public String TGZHUANYI(String money) {
        String path = "http://shizhanzhe.com/index.php?m=pcdata.app_zzsubmit&zzfee=" + money + "&uid=" + uid;
        return path;
    }

    //VIP接口
    public String VIPBUY(String money, String time) {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&uid=" + uid + "&last_time=" + time + "&money=" + money;
        return path;
    }

    //VIP单价查询接口 获取VIP的年单价
    public static String GETVIP() {
        String path = "http://shizhanzhe.com/index.php?m=vipay.app_vip&vip=1";
        return path;
    }

    //补齐团费
    public String TGMONEY(String tid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.buqi&pc=1&uid=" + uid + "&ctid=" + tid + "&token=" + token;
        return path;
    }

    //笔记列表
    public String NOTELIST(String videoid, int page) {
        String path = "https://www.shizhanzhe.com/?m=pcdata.cha_mannote&pc=1&coid=" + videoid + "&uid=" + uid + "&token=" + token + "&page=" + page;
        return path;
    }

    //笔记列表item删除
    public String NOTELISTDEL(String id) {
        String path = "https://www.shizhanzhe.com/?m=pcdata.del_mannote&pc=1&nid=" + id + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //笔记列表item修改
    public String NOTELISTEDIT(String id, String content) {
        String path = "https://www.shizhanzhe.com/?m=pcdata.edit_mannote&pc=1&content=" + content + "&nid=" + id + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //笔记列表增加
    public String NOTELISTEADD(String sid, String pid, String nid, String content) {
        String path = "https://www.shizhanzhe.com/?m=pcdata.ask_mannote&pc=1&content=" + content + "&sid=" + sid + "&pid=" + pid + "&coid=" + nid + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //问题详情
    public static String QUESTIONDETAIL(String qid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.reply_detail&qid=" + qid;
        return path;
    }

    //问题列表
    public String QUESTIONLIST(String type, String videoId, int page) {
        if ("".equals(type)) {
            String path = "https://www.shizhanzhe.com/?m=pcdata.reply_index&pc=1&coid=" + videoId + "&uid=" + uid + "&token=" + token + "&page=" + page;
            return path;
        } else {
            String path = "https://www.shizhanzhe.com/?m=pcdata.reply_index&pc=1&order=" + type + "&coid=" + videoId + "&uid=" + uid + "&token=" + token + "&page=" + page;
            return path;
        }
    }

    //视频进度
    public String VIDEOSCHEDULE(String coid, String pid, String sid, int guantime, int totaltime) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.save_vtime&pc=1&coid=" + coid + "&pid=" + pid + "&sid=" + sid + "&guantime=" + guantime + "&vtime=" + totaltime + "&uid=" + uid + "&token=" + token;
        return path;
    }

    //体系进度
    public String STUDYDETAIL(String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.uudochas&uid=" + uid + "&sid=" + sid;
        return path;
    }

    //我的帖子
    public String MYFORUM(String fid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.get_ltpost_me&pc=1&fid=" + fid + "&page=1&uid=" + uid;
        return path;
    }

    //我的帖子
    public String MYExam(int page) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata.chaexam&uid=" + uid + "&page=" + page;
        return path;
    }

    //课程简介
    public String PROINTRO(String sid) {
        String path = "https://shizhanzhe.com/index.php?m=pcdata1.getcontent&sid=" + sid;
        return path;
    }

    //微信支付
    public static String WXPay(long order, double price) {
        String path = "https://shizhanzhe.com/wxxueapppay/example/jsapi.php?trade_no=" + order + "&fee=" + (int) (price * 100) + "&body=充值&good_name=充值";
        return path;
    }

    //限时特惠
    public static String TH() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata1.tehui";
        return path;
    }

    //网络推广
    public static String WLTG() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata1.nettui";
        return path;
    }

    //小课堂
    public static String XKT() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata1.ketang";
        return path;
    }

    //每日一课
    public static String EVERY() {
        String path = "https://shizhanzhe.com/index.php?m=pcdata1.perday";
        return path;
    }
}

