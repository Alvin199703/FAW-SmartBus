package com.example.administrator.testone;

import com.google.gson.annotations.SerializedName;

/**
 * created by zw 2019/1/15
 * ...
 */
public class EZReturningData {
    public Data data;

    public class Data {
        @SerializedName("accessToken")
        public String accessToken;

        @SerializedName("expireTime")
        public long expireTime;
    }

    @SerializedName("code")
    public String code;

    @SerializedName("msg")
    public String msg;
}
