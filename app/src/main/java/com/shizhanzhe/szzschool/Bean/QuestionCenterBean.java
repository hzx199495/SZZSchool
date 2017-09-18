package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 */

public class QuestionCenterBean {

    /**
     * sid : 78
     * stitle : 网络推广变现·掘金
     * picture : /var/upload/image/2017/05/20170516163246_16264.png
     * chavideo : [{"cid":"111","ctitle":"理论","video":[{"id":"379","title":"实战者教育学院指南"},{"id":"357","title":"网络推广变现1-总纲规划"},{"id":"315","title":"网络推广变现2-掘金思路"},{"id":"313","title":"网络推广变现3-基本原理"},{"id":"314","title":"网络推广变现4-账号设置"},{"id":"323","title":"网络推广变现5-前期准备"},{"id":"326","title":"网络推广变现6-操作步骤"},{"id":"328","title":"网络推广变现7-启动运营"},{"id":"329","title":"网络推广变现8-选品策略"},{"id":"330","title":"网络推广变现9-裂变技巧"},{"id":"331","title":"网络推广变现10-维护技巧"}]},{"cid":"113","ctitle":"实战","video":[{"id":"316","title":"微信实战掘金1-人群定位"},{"id":"317","title":"微信实战掘金2-初始数据"},{"id":"318","title":"微信实战掘金3-巧妙布局"},{"id":"358","title":"微信实战掘金4-原理说明"},{"id":"359","title":"微信实战掘金5-目标定位"},{"id":"361","title":"微博实战掘金6-微博转发"},{"id":"363","title":"微博实战掘金7-ROI营销"}]},{"cid":"114","ctitle":"拓展","video":[]},{"cid":"115","ctitle":"融合","video":[]},{"cid":"116","ctitle":"精彩直播","video":[{"id":"450","title":"8月31日疯狂掘金之自媒体营销实战精细化运营"},{"id":"414","title":"8月17日疯狂掘金之自媒体淘宝客从新人到公司化玩法"},{"id":"391","title":"8月10日疯狂掘金之人气QQ不为人知的赚钱套路"},{"id":"388","title":"8月3日疯狂掘金之使用虚拟机进行多店铺操作"},{"id":"380","title":"7月27日疯狂掘金之店群项目实操经验分享"},{"id":"368","title":"7月18日疯狂掘金之新规下店群项目的操作"},{"id":"367","title":"7月11日疯狂掘金之淘客裂变模式新玩法"},{"id":"366","title":"7月6日疯狂掘金之微博精准粉丝获取变现"},{"id":"365","title":"6月27日疯狂掘金之移动互联网营销"},{"id":"360","title":"6月20日疯狂掘金之独家裂变原创运营"},{"id":"356","title":"6月13日疯狂掘金之生活化微电商运营"},{"id":"353","title":"6月6日疯狂掘金之月拥20万实战秘诀"},{"id":"352","title":"5月23日疯狂掘金之互联网精准营销"},{"id":"351","title":"5月16日疯狂掘金之店群差异化营销策略"},{"id":"346","title":"5月9日疯狂掘金之百度霸屏变现技巧"},{"id":"338","title":"5月4日疯狂掘金之精准数据的变现营销"},{"id":"333","title":"4月25日疯狂掘金之QQ群九九归一实战玩法"},{"id":"332","title":"4月19日疯狂掘金之店群爆款选品思路技巧"},{"id":"327","title":"4月6日疯狂掘金之淘客平台变化策略营销"},{"id":"325","title":"3月29日疯狂掘金之微淘维护运营运营技巧 "},{"id":"322","title":"3月21日疯狂掘金之店群结合微淘运营技巧"},{"id":"321","title":"3月15日疯狂掘金之店群月入百万运营技巧"},{"id":"320","title":"3月7日疯狂掘金之微信淘客裂变运营技巧"},{"id":"319","title":"3月1日疯狂掘金之店群精细化运营技巧"},{"id":"312","title":"2月21日疯狂掘金之微信淘客运营思路讲解"}]}]
     */

    private String sid;
    private String stitle;
    private String picture;
    private List<ChavideoBean> chavideo;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<ChavideoBean> getChavideo() {
        return chavideo;
    }

    public void setChavideo(List<ChavideoBean> chavideo) {
        this.chavideo = chavideo;
    }

    public static class ChavideoBean {
        /**
         * cid : 111
         * ctitle : 理论
         * video : [{"id":"379","title":"实战者教育学院指南"},{"id":"357","title":"网络推广变现1-总纲规划"},{"id":"315","title":"网络推广变现2-掘金思路"},{"id":"313","title":"网络推广变现3-基本原理"},{"id":"314","title":"网络推广变现4-账号设置"},{"id":"323","title":"网络推广变现5-前期准备"},{"id":"326","title":"网络推广变现6-操作步骤"},{"id":"328","title":"网络推广变现7-启动运营"},{"id":"329","title":"网络推广变现8-选品策略"},{"id":"330","title":"网络推广变现9-裂变技巧"},{"id":"331","title":"网络推广变现10-维护技巧"}]
         */

        private String cid;
        private String ctitle;
        private List<VideoBean> video;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCtitle() {
            return ctitle;
        }

        public void setCtitle(String ctitle) {
            this.ctitle = ctitle;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class VideoBean {
            /**
             * id : 379
             * title : 实战者教育学院指南
             */

            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
