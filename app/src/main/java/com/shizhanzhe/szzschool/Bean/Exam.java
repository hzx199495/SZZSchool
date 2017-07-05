package com.shizhanzhe.szzschool.Bean;

import java.util.List;

/**
 * Created by zz9527 on 2017/6/30.
 */

public class Exam {

    /**
     * id : 805
     * title : 淘客微信群的群成员构成主要是6个类型：群主、机器人、导购员、产品托儿、启动粉丝和裂变粉丝一起构成，对么？
     * style :
     * listorder : 0
     * updatetime : 1490950657
     * inputtime : 1490950657
     * difficulty : 1
     * answer : 2
     * intro :
     * keys :
     * key1 : 否
     * key2 : 是
     * key3 :
     * key4 :
     * key5 :
     * key6 :
     * courseid : 326
     * type : 1
     * exam : [{"a1":"A","a2":"否","a3":"0"},{"a1":"B","a2":"是","a3":"2"}]
     */

    private String id;
    private String title;
    private String style;
    private String listorder;
    private String updatetime;
    private String inputtime;
    private String difficulty;
    private String answer;
    private String intro;
    private String keys;
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String key5;
    private String key6;
    private String courseid;
    private String type;
    private List<ExamBean> exam;

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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public String getKey6() {
        return key6;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExamBean> getExam() {
        return exam;
    }

    public void setExam(List<ExamBean> exam) {
        this.exam = exam;
    }

    public static class ExamBean {
        /**
         * a1 : A
         * a2 : 否
         * a3 : 0
         */

        private String a1;
        private String a2;
        private String a3;

        public String getA1() {
            return a1;
        }

        public void setA1(String a1) {
            this.a1 = a1;
        }

        public String getA2() {
            return a2;
        }

        public void setA2(String a2) {
            this.a2 = a2;
        }

        public String getA3() {
            return a3;
        }

        public void setA3(String a3) {
            this.a3 = a3;
        }
    }
}
