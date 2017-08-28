package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/17.
 */

public class ScheduleBean {


    /**
     * status : 1
     * info : {"stitle":"网络推广变现·掘金","stotal":"42","sntotal":"5","kc_data":[{"vdata":[{"vid":"379","vtitle":"实战者教育学院指南","vdetail":{"id":"1124","uid":"999","coid":"379","pid":"111","sid":"78","guantime":"138","vtime":"284","addtime":"1502942342","status":"1"}},{"vid":"357","vtitle":"网络推广变现1-总纲规划","vdetail":{"id":"1047","uid":"999","coid":"357","pid":"111","sid":"78","guantime":"16","vtime":"353","addtime":"1502703477","status":"0"}},{"vid":"315","vtitle":"网络推广变现2-掘金思路","vdetail":{"id":"1046","uid":"999","coid":"315","pid":"111","sid":"78","guantime":"4","vtime":"1087","addtime":"1502703426","status":"0"}},{"vid":"313","vtitle":"网络推广变现3-基本原理","vdetail":{"id":"1098","uid":"999","coid":"313","pid":"111","sid":"78","guantime":"8","vtime":"909","addtime":"1502873919","status":"0"}},{"vid":"314","vtitle":"网络推广变现4-账号设置","vdetail":""},{"vid":"323","vtitle":"网络推广变现5-前期准备","vdetail":""},{"vid":"326","vtitle":"网络推广变现6-操作步骤","vdetail":{"id":"1097","uid":"999","coid":"326","pid":"111","sid":"78","guantime":"30","vtime":"353","addtime":"1502873759","status":"0"}},{"vid":"328","vtitle":"网络推广变现7-启动运营","vdetail":""},{"vid":"329","vtitle":"网络推广变现8-选品策略","vdetail":""},{"vid":"330","vtitle":"网络推广变现9-裂变技巧","vdetail":""},{"vid":"331","vtitle":"网络推广变现10-维护技巧","vdetail":""}],"cntotal":"5","ctotal":"11","ctitle":"理论","cid":"111"},{"vdata":[{"vid":"316","vtitle":"微信实战掘金1-人群定位","vdetail":""},{"vid":"317","vtitle":"微信实战掘金2-初始数据","vdetail":""},{"vid":"318","vtitle":"微信实战掘金3-巧妙布局","vdetail":""},{"vid":"358","vtitle":"微信实战掘金4-原理说明","vdetail":""},{"vid":"359","vtitle":"微信实战掘金5-目标定位","vdetail":""},{"vid":"361","vtitle":"微博实战掘金6-微博转发","vdetail":""},{"vid":"363","vtitle":"微博实战掘金7-ROI营销","vdetail":""}],"cntotal":"0","ctotal":"7","ctitle":"实战","cid":"113"},{"vdata":[],"cntotal":"0","ctotal":"0","ctitle":"拓展","cid":"114"},{"vdata":[],"cntotal":"0","ctotal":"0","ctitle":"融合","cid":"115"},{"vdata":[{"vid":"414","vtitle":"8月17日疯狂掘金之自媒体淘宝客从新人到公司化玩法","vdetail":""},{"vid":"391","vtitle":"8月10日疯狂掘金之人气QQ不为人知的赚钱套路","vdetail":""},{"vid":"388","vtitle":"8月3日疯狂掘金之使用虚拟机进行多店铺操作","vdetail":""},{"vid":"380","vtitle":"7月27日疯狂掘金之店群项目实操经验分享","vdetail":""},{"vid":"368","vtitle":"7月18日疯狂掘金之新规下店群项目的操作","vdetail":""},{"vid":"367","vtitle":"7月11日疯狂掘金之淘客裂变模式新玩法","vdetail":""},{"vid":"366","vtitle":"7月6日疯狂掘金之微博精准粉丝获取变现","vdetail":""},{"vid":"365","vtitle":"6月27日疯狂掘金之移动互联网营销","vdetail":""},{"vid":"360","vtitle":"6月20日疯狂掘金之独家裂变原创运营","vdetail":""},{"vid":"356","vtitle":"6月13日疯狂掘金之生活化微电商运营","vdetail":""},{"vid":"353","vtitle":"6月6日疯狂掘金之月拥20万实战秘诀","vdetail":""},{"vid":"352","vtitle":"5月23日疯狂掘金之互联网精准营销","vdetail":""},{"vid":"351","vtitle":"5月16日疯狂掘金之店群差异化营销策略","vdetail":""},{"vid":"346","vtitle":"5月9日疯狂掘金之百度霸屏变现技巧","vdetail":""},{"vid":"338","vtitle":"5月4日疯狂掘金之精准数据的变现营销","vdetail":""},{"vid":"333","vtitle":"4月25日疯狂掘金之QQ群九九归一实战玩法","vdetail":""},{"vid":"332","vtitle":"4月19日疯狂掘金之店群爆款选品思路技巧","vdetail":""},{"vid":"327","vtitle":"4月6日疯狂掘金之淘客平台变化策略营销","vdetail":""},{"vid":"325","vtitle":"3月29日疯狂掘金之微淘维护运营运营技巧 ","vdetail":""},{"vid":"322","vtitle":"3月21日疯狂掘金之店群结合微淘运营技巧","vdetail":""},{"vid":"321","vtitle":"3月15日疯狂掘金之店群月入百万运营技巧","vdetail":""},{"vid":"320","vtitle":"3月7日疯狂掘金之微信淘客裂变运营技巧","vdetail":""},{"vid":"319","vtitle":"3月1日疯狂掘金之店群精细化运营技巧","vdetail":""},{"vid":"312","vtitle":"2月21日疯狂掘金之微信淘客运营思路讲解","vdetail":""}],"cntotal":"0","ctotal":"24","ctitle":"精彩直播","cid":"116"}]}
     */

    private int status;
    private InfoBean info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * stitle : 网络推广变现·掘金
         * stotal : 42
         * sntotal : 5
         * kc_data : [{"vdata":[{"vid":"379","vtitle":"实战者教育学院指南","vdetail":{"id":"1124","uid":"999","coid":"379","pid":"111","sid":"78","guantime":"138","vtime":"284","addtime":"1502942342","status":"1"}},{"vid":"357","vtitle":"网络推广变现1-总纲规划","vdetail":{"id":"1047","uid":"999","coid":"357","pid":"111","sid":"78","guantime":"16","vtime":"353","addtime":"1502703477","status":"0"}},{"vid":"315","vtitle":"网络推广变现2-掘金思路","vdetail":{"id":"1046","uid":"999","coid":"315","pid":"111","sid":"78","guantime":"4","vtime":"1087","addtime":"1502703426","status":"0"}},{"vid":"313","vtitle":"网络推广变现3-基本原理","vdetail":{"id":"1098","uid":"999","coid":"313","pid":"111","sid":"78","guantime":"8","vtime":"909","addtime":"1502873919","status":"0"}},{"vid":"314","vtitle":"网络推广变现4-账号设置","vdetail":""},{"vid":"323","vtitle":"网络推广变现5-前期准备","vdetail":""},{"vid":"326","vtitle":"网络推广变现6-操作步骤","vdetail":{"id":"1097","uid":"999","coid":"326","pid":"111","sid":"78","guantime":"30","vtime":"353","addtime":"1502873759","status":"0"}},{"vid":"328","vtitle":"网络推广变现7-启动运营","vdetail":""},{"vid":"329","vtitle":"网络推广变现8-选品策略","vdetail":""},{"vid":"330","vtitle":"网络推广变现9-裂变技巧","vdetail":""},{"vid":"331","vtitle":"网络推广变现10-维护技巧","vdetail":""}],"cntotal":"5","ctotal":"11","ctitle":"理论","cid":"111"},{"vdata":[{"vid":"316","vtitle":"微信实战掘金1-人群定位","vdetail":""},{"vid":"317","vtitle":"微信实战掘金2-初始数据","vdetail":""},{"vid":"318","vtitle":"微信实战掘金3-巧妙布局","vdetail":""},{"vid":"358","vtitle":"微信实战掘金4-原理说明","vdetail":""},{"vid":"359","vtitle":"微信实战掘金5-目标定位","vdetail":""},{"vid":"361","vtitle":"微博实战掘金6-微博转发","vdetail":""},{"vid":"363","vtitle":"微博实战掘金7-ROI营销","vdetail":""}],"cntotal":"0","ctotal":"7","ctitle":"实战","cid":"113"},{"vdata":[],"cntotal":"0","ctotal":"0","ctitle":"拓展","cid":"114"},{"vdata":[],"cntotal":"0","ctotal":"0","ctitle":"融合","cid":"115"},{"vdata":[{"vid":"414","vtitle":"8月17日疯狂掘金之自媒体淘宝客从新人到公司化玩法","vdetail":""},{"vid":"391","vtitle":"8月10日疯狂掘金之人气QQ不为人知的赚钱套路","vdetail":""},{"vid":"388","vtitle":"8月3日疯狂掘金之使用虚拟机进行多店铺操作","vdetail":""},{"vid":"380","vtitle":"7月27日疯狂掘金之店群项目实操经验分享","vdetail":""},{"vid":"368","vtitle":"7月18日疯狂掘金之新规下店群项目的操作","vdetail":""},{"vid":"367","vtitle":"7月11日疯狂掘金之淘客裂变模式新玩法","vdetail":""},{"vid":"366","vtitle":"7月6日疯狂掘金之微博精准粉丝获取变现","vdetail":""},{"vid":"365","vtitle":"6月27日疯狂掘金之移动互联网营销","vdetail":""},{"vid":"360","vtitle":"6月20日疯狂掘金之独家裂变原创运营","vdetail":""},{"vid":"356","vtitle":"6月13日疯狂掘金之生活化微电商运营","vdetail":""},{"vid":"353","vtitle":"6月6日疯狂掘金之月拥20万实战秘诀","vdetail":""},{"vid":"352","vtitle":"5月23日疯狂掘金之互联网精准营销","vdetail":""},{"vid":"351","vtitle":"5月16日疯狂掘金之店群差异化营销策略","vdetail":""},{"vid":"346","vtitle":"5月9日疯狂掘金之百度霸屏变现技巧","vdetail":""},{"vid":"338","vtitle":"5月4日疯狂掘金之精准数据的变现营销","vdetail":""},{"vid":"333","vtitle":"4月25日疯狂掘金之QQ群九九归一实战玩法","vdetail":""},{"vid":"332","vtitle":"4月19日疯狂掘金之店群爆款选品思路技巧","vdetail":""},{"vid":"327","vtitle":"4月6日疯狂掘金之淘客平台变化策略营销","vdetail":""},{"vid":"325","vtitle":"3月29日疯狂掘金之微淘维护运营运营技巧 ","vdetail":""},{"vid":"322","vtitle":"3月21日疯狂掘金之店群结合微淘运营技巧","vdetail":""},{"vid":"321","vtitle":"3月15日疯狂掘金之店群月入百万运营技巧","vdetail":""},{"vid":"320","vtitle":"3月7日疯狂掘金之微信淘客裂变运营技巧","vdetail":""},{"vid":"319","vtitle":"3月1日疯狂掘金之店群精细化运营技巧","vdetail":""},{"vid":"312","vtitle":"2月21日疯狂掘金之微信淘客运营思路讲解","vdetail":""}],"cntotal":"0","ctotal":"24","ctitle":"精彩直播","cid":"116"}]
         */

        private String stitle;
        private String stotal;
        private String sntotal;
        private List<KcDataBean> kc_data;

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getStotal() {
            return stotal;
        }

        public void setStotal(String stotal) {
            this.stotal = stotal;
        }

        public String getSntotal() {
            return sntotal;
        }

        public void setSntotal(String sntotal) {
            this.sntotal = sntotal;
        }

        public List<KcDataBean> getKc_data() {
            return kc_data;
        }

        public void setKc_data(List<KcDataBean> kc_data) {
            this.kc_data = kc_data;
        }

        public static class KcDataBean {
            /**
             * vdata : [{"vid":"379","vtitle":"实战者教育学院指南","vdetail":{"id":"1124","uid":"999","coid":"379","pid":"111","sid":"78","guantime":"138","vtime":"284","addtime":"1502942342","status":"1"}},{"vid":"357","vtitle":"网络推广变现1-总纲规划","vdetail":{"id":"1047","uid":"999","coid":"357","pid":"111","sid":"78","guantime":"16","vtime":"353","addtime":"1502703477","status":"0"}},{"vid":"315","vtitle":"网络推广变现2-掘金思路","vdetail":{"id":"1046","uid":"999","coid":"315","pid":"111","sid":"78","guantime":"4","vtime":"1087","addtime":"1502703426","status":"0"}},{"vid":"313","vtitle":"网络推广变现3-基本原理","vdetail":{"id":"1098","uid":"999","coid":"313","pid":"111","sid":"78","guantime":"8","vtime":"909","addtime":"1502873919","status":"0"}},{"vid":"314","vtitle":"网络推广变现4-账号设置","vdetail":""},{"vid":"323","vtitle":"网络推广变现5-前期准备","vdetail":""},{"vid":"326","vtitle":"网络推广变现6-操作步骤","vdetail":{"id":"1097","uid":"999","coid":"326","pid":"111","sid":"78","guantime":"30","vtime":"353","addtime":"1502873759","status":"0"}},{"vid":"328","vtitle":"网络推广变现7-启动运营","vdetail":""},{"vid":"329","vtitle":"网络推广变现8-选品策略","vdetail":""},{"vid":"330","vtitle":"网络推广变现9-裂变技巧","vdetail":""},{"vid":"331","vtitle":"网络推广变现10-维护技巧","vdetail":""}]
             * cntotal : 5
             * ctotal : 11
             * ctitle : 理论
             * cid : 111
             */

            private String cntotal;
            private String ctotal;
            private String ctitle;
            private String cid;
            private List<VdataBean> vdata;

            public String getCntotal() {
                return cntotal;
            }

            public void setCntotal(String cntotal) {
                this.cntotal = cntotal;
            }

            public String getCtotal() {
                return ctotal;
            }

            public void setCtotal(String ctotal) {
                this.ctotal = ctotal;
            }

            public String getCtitle() {
                return ctitle;
            }

            public void setCtitle(String ctitle) {
                this.ctitle = ctitle;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public List<VdataBean> getVdata() {
                return vdata;
            }

            public void setVdata(List<VdataBean> vdata) {
                this.vdata = vdata;
            }

            public static class VdataBean {
                /**
                 * vid : 379
                 * vtitle : 实战者教育学院指南
                 * vdetail : {"id":"1124","uid":"999","coid":"379","pid":"111","sid":"78","guantime":"138","vtime":"284","addtime":"1502942342","status":"1"}
                 */

                private String vid;
                private String vtitle;
                private VdetailBean vdetail;

                public String getVid() {
                    return vid;
                }

                public void setVid(String vid) {
                    this.vid = vid;
                }

                public String getVtitle() {
                    return vtitle;
                }

                public void setVtitle(String vtitle) {
                    this.vtitle = vtitle;
                }

                public VdetailBean getVdetail() {
                    return vdetail;
                }

                public void setVdetail(VdetailBean vdetail) {
                    this.vdetail = vdetail;
                }

                public static class VdetailBean {
                    /**
                     * id : 1124
                     * uid : 999
                     * coid : 379
                     * pid : 111
                     * sid : 78
                     * guantime : 138
                     * vtime : 284
                     * addtime : 1502942342
                     * status : 1
                     */

                    private String id;
                    private String uid;
                    private String coid;
                    private String pid;
                    private String sid;
                    private String guantime;
                    private String vtime;
                    private String addtime;
                    private String status;

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

                    public String getCoid() {
                        return coid;
                    }

                    public void setCoid(String coid) {
                        this.coid = coid;
                    }

                    public String getPid() {
                        return pid;
                    }

                    public void setPid(String pid) {
                        this.pid = pid;
                    }

                    public String getSid() {
                        return sid;
                    }

                    public void setSid(String sid) {
                        this.sid = sid;
                    }

                    public String getGuantime() {
                        return guantime;
                    }

                    public void setGuantime(String guantime) {
                        this.guantime = guantime;
                    }

                    public String getVtime() {
                        return vtime;
                    }

                    public void setVtime(String vtime) {
                        this.vtime = vtime;
                    }

                    public String getAddtime() {
                        return addtime;
                    }

                    public void setAddtime(String addtime) {
                        this.addtime = addtime;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }
                }
            }
        }
    }
}
