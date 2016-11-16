package com.shizhanzhe.szzschool.utils;

/**
 * Created by hasee on 2016/11/16.
 */
public  class Path {
    public static String UZER(String username,String pswd){
        String path="http://2.huobox.com/index.php?m=pcdata.user_data&pc=1&username="+username+"&password="+pswd;
        return path;
    }
}
