package com.yjhs.cbsdbase.bean;

import java.util.List;

public class ImgsUploadResp {

    /**
     * filePath : ["2019/02/28/8f58193fbffd4991946b2a19da7519e2.png","2019/02/28/b5f0134278764d0db0f01b7d373f2e3f.png"]
     * fileName : ["Android屏幕.png","retrofit错误回调.png"]
     * strImgrootpath : http://linux.fushoukeji.com:88/picpath/share_ad/
     */

    private String strImgrootpath;
    private List<String> filePath;
    private List<String> fileName;

    public String getStrImgrootpath() {
        return strImgrootpath;
    }

    public void setStrImgrootpath(String strImgrootpath) {
        this.strImgrootpath = strImgrootpath;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(List<String> fileName) {
        this.fileName = fileName;
    }
}
