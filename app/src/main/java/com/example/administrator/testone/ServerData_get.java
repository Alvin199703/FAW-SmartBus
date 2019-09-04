package com.example.administrator.testone;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ServerData_get {
    static String a;
    static StringBuffer b=new StringBuffer();
    public String SerServerData_get1(){
        sendRequestWithOkHttp();
        a=b.toString();
        return a;
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody =new FormBody.Builder().add("a","b").build();
                    Request request = new Request.Builder().url("http://192.168.1.105/get_data.xml").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData=response.body().string();

                    parseXMLWithPull(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void parseXMLWithPull(String xmlData){
        try{

            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            String id=" ";
            String name=" ";
            String version=" ";
            while (eventType!=XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个节点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                            b.append(id);
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                            b.append(name);
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                            b.append(version);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("MainActivity","id is"+id);
                            Log.d("MainActivity","name is"+name);
                            Log.d("MainActivity","version is"+version);
                        }
                        break;
                    }
                    default:
                        break;

                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
