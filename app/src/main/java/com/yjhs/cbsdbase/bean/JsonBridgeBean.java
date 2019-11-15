package com.yjhs.cbsdbase.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */

public class JsonBridgeBean {
    private String cmd;
    private String callback;
    private List<String> param;

    public static JsonBridgeBean getInstance(String str) {
        Gson gson = new Gson();
        JsonBridgeBean jsonVO = null;
        try {
            jsonVO= gson.fromJson(str,new TypeToken<JsonBridgeBean>(){}.getType());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return jsonVO;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }



    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }


    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "JsonBridgeBean{" +
                "cmd='" + cmd + '\'' +
                ", callback='" + callback + '\'' +
                ", param=" + param +
                '}';
    }
}
