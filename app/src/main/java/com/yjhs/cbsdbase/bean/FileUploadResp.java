package com.yjhs.cbsdbase.bean;

public class FileUploadResp {

    /**
     * filePath : 2018/06/25/23848d59f2f848ed9ba10d1dede7c6f5.apk
     * fileName : 1.apk
     * strImgrootpath : http://linux.fushoukeji.com:88/picpath/share_ad/
     */

    private String filePath;
    private String fileName;
    private String strImgrootpath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStrImgrootpath() {
        return strImgrootpath;
    }

    public void setStrImgrootpath(String strImgrootpath) {
        this.strImgrootpath = strImgrootpath;
    }
}
