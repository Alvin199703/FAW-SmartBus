package com.example.administrator.testone.overlayutil;

import com.google.gson.annotations.SerializedName;

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
