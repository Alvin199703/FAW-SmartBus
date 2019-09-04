package com.example.administrator.testone;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,String messeng, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder().add("messger",messeng).build();
        Request request=new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);

    }
}
