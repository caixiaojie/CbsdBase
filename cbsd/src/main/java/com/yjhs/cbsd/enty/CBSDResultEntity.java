package com.yjhs.cbsd.enty;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 *
 *created by Damon on 2017/7/4 9:19
 *
 *description: 
 *
 */
public class CBSDResultEntity {

    @SerializedName(value = "code", alternate = {"error_code","status"})
    private int code;
    @SerializedName(value = "success", alternate = {"reason","msg","error_msg"})
    private String msg;
    @SerializedName(value = "data", alternate = {"result","body"})
    private JsonElement data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
