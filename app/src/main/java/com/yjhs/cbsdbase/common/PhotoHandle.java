package com.yjhs.cbsdbase.common;



import java.util.List;

/**
 * author: Administrator
 * date: 2018/1/25 0025
 * desc:
 */

public class PhotoHandle {
    private IWebCallback iWebCallback;
    private String callbackname;

    public PhotoHandle(IWebCallback iWebCallback) {
        this.iWebCallback = iWebCallback;
    }

    public void setCallbackname(String callbackname) {
        this.callbackname = callbackname;
    }
    public void setSuccess (List<String> urls, String strImgrootpath) {
        /**
         * "filePath": [
         *             "2019/02/28/8f58193fbffd4991946b2a19da7519e2.png",
         *             "2019/02/28/b5f0134278764d0db0f01b7d373f2e3f.png"
         *         ]
         */
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("\"url\":["));
        for (int i = 0; i < urls.size(); i++) {
            sb.append(String.format("\"%s\",",urls.get(i)));
        }
        String substring = sb.substring(0, sb.length() - 1);
        StringBuilder stringBuilder = new StringBuilder(substring);
        stringBuilder.append("]");
        stringBuilder.append(String.format(",\"strImgrootpath\":\"%s\"",strImgrootpath));

//                .append(",")

        String json= String.format("{\"code\":1,\"data\":{%s}}",stringBuilder);
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
