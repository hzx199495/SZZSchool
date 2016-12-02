package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by hasee on 2016/11/24.
 */
public class ProBean2 {

    /**
     * id : 77
     * listorder : 0
     * updatetime : 1461222523
     * inputtime : 1461222523
     * ctitle : 企鹅媒体
     * style :
     * choice_kc : [{"id":"262","name":"QQ群提取好友","sort":"0","mv_url":""},{"id":"290","name":"天涯论坛顶帖机","sort":"0","mv_url":""},{"id":"291","name":"豆瓣顶帖机","sort":"0","mv_url":""},{"id":"292","name":"猫扑回帖助手","sort":"0","mv_url":""},{"id":"293","name":"百度贴吧帖子采集器","sort":"0","mv_url":""}]
     * choice_kc_idstr : 262,290,291,292,293
     * systemid : 34
     * thumb : /var/upload/image/2016/05/20160503160613_55082.jpg
     * introduce :
     */

    private String id;
    private String listorder;
    private String updatetime;
    private String inputtime;
    private String ctitle;
    private String style;
    private String choice_kc_idstr;
    private String systemid;
    private String thumb;
    private String introduce;
    /**
     * id : 262
     * name : QQ群提取好友
     * sort : 0
     * mv_url :
     */

    private List<ChoiceKcBean> choice_kc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getChoice_kc_idstr() {
        return choice_kc_idstr;
    }

    public void setChoice_kc_idstr(String choice_kc_idstr) {
        this.choice_kc_idstr = choice_kc_idstr;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<ChoiceKcBean> getChoice_kc() {
        return choice_kc;
    }

    public void setChoice_kc(List<ChoiceKcBean> choice_kc) {
        this.choice_kc = choice_kc;
    }

    public static class ChoiceKcBean {
        private String id;
        private String name;
        private String sort;
        private String mv_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getMv_url() {
            return mv_url;
        }

        public void setMv_url(String mv_url) {
            this.mv_url = mv_url;
        }
    }
}
