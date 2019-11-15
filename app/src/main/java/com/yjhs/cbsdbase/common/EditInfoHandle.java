package com.yjhs.cbsdbase.common;


/**
 * author: Administrator
 * date: 2018/1/25 0025
 * desc:
 */

public class EditInfoHandle {
    private IWebCallback iWebCallback;
    private String callbackname;

    public EditInfoHandle(IWebCallback iWebCallback) {
        this.iWebCallback = iWebCallback;
    }

    public void setCallbackname(String callbackname) {
        this.callbackname = callbackname;
    }
    public void setSuccess (String url, String strImgrootpath) {
        /**
         * :{
         * url:'',//上传后获得资源服务器请求路径
         * },
         * strImgrootpath:'',//视频服务器根路径
         * }
         */
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("\"url\":\"%s\",\"strImgrootpath\":\"%s\"",url,strImgrootpath));

        String json= String.format("{\"code\":1,\"data\":{%s}}",sb);
        if (iWebCallback != null) {
            iWebCallback.fun(callbackname,json);
        }
    }
    public void setError (String code, String msg) {
        if ("".equals(code)) {
            return;
        }
        String json = String.format("{\"code\":%s,\"msg\":%s}",code,msg);
        if (iWebCallback != null) {
            iWebCallback.fun(callbackname,json);
        }
    }

}
