package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/8/16.
 */

public class QuestionCenterBean {

    /**
     * sid : 88
     * stitle : PhotoShop零基础入门教程
     * chavideo : [{"cid":"162","ctitle":"理论","video":[{"id":"379","title":"实战者教育学院指南"},{"id":"369","title":"PS课程1.1-安装打开"},{"id":"370","title":"PS课程1.2-界面认识"},{"id":"371","title":"PS课程1.3-文件新建"},{"id":"372","title":"PS课程1.4-文件开关"},{"id":"373","title":"PS课程1.5-综合案例"},{"id":"374","title":"PS课程2.1-图片调整"},{"id":"375","title":"PS课程2.2-图片编辑"},{"id":"376","title":"PS课程2.3-编辑其他"},{"id":"377","title":"PS课程2.4-基本工具"},{"id":"378","title":"PS课程2.5-选框工具"},{"id":"381","title":"PS课程3.1-选框案例的制作1"},{"id":"382","title":"PS课程3.2-选框案例的制作2"},{"id":"383","title":"PS课程3.3-套索工具"},{"id":"384","title":"PS课程3.4-套索案例讲解"},{"id":"385","title":"PS课程3.5-微信图标的绘制"},{"id":"392","title":"PS课程4.1-魔棒工具和快速选择工具"},{"id":"393","title":"PS课程4.2-案例制作"},{"id":"394","title":"PS课程4.3-裁剪工具和污点修复画笔工具"},{"id":"395","title":"PS课程4.4-修补工具和红眼工具"},{"id":"401","title":"PS课程4.5-修复画笔工具"},{"id":"402","title":"PS课程4.6-内容感知工具"}]},{"cid":"163","ctitle":"实战","video":[]},{"cid":"164","ctitle":"拓展","video":[]},{"cid":"165","ctitle":"融合","video":[]},{"cid":"166","ctitle":"精彩直播","video":[]}]
     */

    private String sid;
    private String stitle;
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

    public List<ChavideoBean> getChavideo() {
        return chavideo;
    }

    public void setChavideo(List<ChavideoBean> chavideo) {
        this.chavideo = chavideo;
    }

    public static class ChavideoBean {
        /**
         * cid : 162
         * ctitle : 理论
         * video : [{"id":"379","title":"实战者教育学院指南"},{"id":"369","title":"PS课程1.1-安装打开"},{"id":"370","title":"PS课程1.2-界面认识"},{"id":"371","title":"PS课程1.3-文件新建"},{"id":"372","title":"PS课程1.4-文件开关"},{"id":"373","title":"PS课程1.5-综合案例"},{"id":"374","title":"PS课程2.1-图片调整"},{"id":"375","title":"PS课程2.2-图片编辑"},{"id":"376","title":"PS课程2.3-编辑其他"},{"id":"377","title":"PS课程2.4-基本工具"},{"id":"378","title":"PS课程2.5-选框工具"},{"id":"381","title":"PS课程3.1-选框案例的制作1"},{"id":"382","title":"PS课程3.2-选框案例的制作2"},{"id":"383","title":"PS课程3.3-套索工具"},{"id":"384","title":"PS课程3.4-套索案例讲解"},{"id":"385","title":"PS课程3.5-微信图标的绘制"},{"id":"392","title":"PS课程4.1-魔棒工具和快速选择工具"},{"id":"393","title":"PS课程4.2-案例制作"},{"id":"394","title":"PS课程4.3-裁剪工具和污点修复画笔工具"},{"id":"395","title":"PS课程4.4-修补工具和红眼工具"},{"id":"401","title":"PS课程4.5-修复画笔工具"},{"id":"402","title":"PS课程4.6-内容感知工具"}]
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
