package com.shizhanzhe.szzschool.Bean;

/**
 * Created by zz9527 on 2017/7/11.
 */

public class Image {

    /**
     * error : 0
     * url : /var/upload/image/2017/07/20170711112456_92433.jpg
     * name : 1
     * ext : jpg
     */

    private int error;
    private String url;
    private String name;
    private String ext;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
