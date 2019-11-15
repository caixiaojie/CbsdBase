package com.yjhs.cbsdbase.bean;

import java.io.Serializable;

public class EditArticleResp implements Serializable {
    private String content;
    private String title;
    private String strUrl;
    private String isUrl;//判断是否为外链地址编辑文本0 外链地址1 ）
    private String isVideo;//
    private String videoSrc;//
    private String videoImg;//
    private String strinfoid;//
    private String isModel;//


    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getIsModel() {
        return isModel;
    }

    public void setIsModel(String isModel) {
        this.isModel = isModel;
    }

    public String getStrinfoid() {
        return strinfoid;
    }

    public void setStrinfoid(String strinfoid) {
        this.strinfoid = strinfoid;
    }

    public String getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(String isVideo) {
        this.isVideo = isVideo;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public String getIsUrl() {
        return isUrl;
    }

    public void setIsUrl(String isUrl) {
        this.isUrl = isUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
