package com.example.administrator.testone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.animation.AlphaAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.example.administrator.testone.EZReturningData;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import com.example.administrator.testone.overlayutil.PoiOverlay;
import com.google.gson.Gson;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;



import android.app.AlertDialog;

import android.os.Message;
import android.os.Handler;
import android.app.Dialog;
import android.widget.TextView;

import android.content.DialogInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.wanghaomiao.xpath.model.JXDocument;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
 static long c;

    Button appointment1;                                                                            //预约
    Button fangdayi;                                                                                //摄像头放大1
    Button fangdaer;                                                                                //摄像头放大2
    Button cance;                                                                                   //取消
   // Button pickup;                                                                                  //起点
    //Button end;                                                                                     //终点
    Button close;                                                                                   //关闭车门
    Button fanhui;                                                                                  //从监控画面返回
    Button fenxiang;                                                                                //分享
    Button setcarid,carspeed,carorientation,cargoout,carhavegoout,getinclose;
    Button iv_forward;      //遥控驾驶前进按钮
    Button iv_left;         //遥控驾驶左传按钮
    Button iv;              //遥控驾驶OK按钮
    Button iv_right;        //遥控驾驶右转按钮
    Button ll_go_back;      //遥控驾驶后退按钮
    Button iv_accelerator;  //遥控驾驶加速按钮
    Button ll_brake;        //遥控驾驶刹车按钮
    Spinner number;         //人数
    Button goback;          //从遥控驾驶返回
    Button driving;         //自动驾驶按钮
    Button jiankong;        //车1监控
    Button jiankong2;       //车2监控
    Spinner qidian;         //起点
    Spinner zhongdian;      //终点
    Spinner monitor;        //车辆监控
    Spinner spinner;                                                                                //方向
    TextView talk;
    ServerData_get getdata;                                                                          //获取服务器车辆信息
    StringBuilder getcarmessge;
    String carmessage;
    private CheckBox mCbUrgent, mCbLocking;
    private EZPlayer mEZPlayerOutside;                                                               //设置播放摄像头
    private SurfaceView mRealPlaySvOutside = null;
    private SurfaceHolder mRealPlayShOutside;
    private EZPlayer mEZPlayerInside;
    private SurfaceView mRealPlaySvInside = null;
    private SurfaceHolder mRealPlayShInside;
    String mAppkey;
    static String mAccesstoken;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private LinearLayout frameSwitch;                                                               //功能框
    //private ScrollView frameSwitch1;
    private LinearLayout frameSwitch1;                                                               //车票框
    private LinearLayout frameSwitch2;                                                                    //车票框中放图片的小框
    private FrameLayout frameSwitch4;                                                                    //地图框
    private LinearLayout remotedriving;                                                                    //遥控驾驶框
    private LinearLayout success;                                                                   //预约成功框
    private Button ticket;                                                                          //车票
    private LinearLayout frameSwitch3;                                                                    //监控框
    private LinearLayout biaoti;                                                                    //最上面的标题框
    TextView mOffTextView;
    Handler mOffHandler,mOffHandler2;
    Timer mOffTime,mOffTime2;
    Dialog mDialog;
    static int b=0;
    static int a=0;
    static boolean isUserBegin = false;
    static boolean isUserEnd = false;
    static String Orderstate = null,doorState;
    public TimeCount time;
    private DrawerLayout mDrawerLayout;                                                            //各个控件定义
    private EditText cityNameText;
    private EditText searchText;
    public static boolean isLogin = false;                                                        //表示是否在登录状态
    public LocationClient mLocationClient;                                                         //百度地图相关的定位客户端实例
    static public MapView mapView;                                                                       //百度地图视图显示
    private BaiduMap baiduMap;                                                                     //地图的总控制器
    List<LatLng> points = new ArrayList();                                                          //地图上添加折线所需要的点集合
    private boolean isFirstLocate = true;                                                        //判断是否是第一次定位
    static LatLng beganla=null;                                                                                 //记录起始位置
    static LatLng endla=null;                                                                                   //记录结束位置
    static LatLng present=null;
    static LatLng present1=null;
    static LatLng prepresent=null;
    String number11;
    String fangxiang,carid,busspeed,busor;                                                                         //车辆id；
    static String closedoor;                                                                               //
    static String opendoor;
    final OkHttpClient client = new OkHttpClient();
    Request request5; //获取车辆的位置信息
    static String la1;
    static String lo1;
    private byte mEmergencyBrakingStatus = 0, mLockingStatus = 0;
    CircleOptions circle1 ;
    CircleOptions circle2 ;
    CircleOptions circle3 ;
    CircleOptions circle4 ;
    CircleOptions circle5 ;
    CircleOptions circle6;
    public static LatLng latLng;
    private static double PI = Math.PI;
    private static double AXIS = 6378245.0; //
    private static double OFFSET = 0.00669342162296594323; // (a^2 - b^2) / a^2
    private static double X_PI = PI * 3000.0 / 180.0;
    private static InetAddress mAddress;
    private static DatagramSocket socket = null;
    private static String ip = "39.106.26.98"; //发送给整个局域网
    //private static String ip = "192.168.1.151"; //发送给整个局域网
    private static final int SendPort = 6666;  //发送方和接收方需要端口一致
     Marker marker,marker1,markerEnd;
    // static LatLng prebegan = new LatLng(43.7980457,125.4392735);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prepresent=new LatLng(0,0);
        present1=new LatLng(0,0);
        closedoor="1";
        opendoor="1";
        carid="0";
        getcarmessge=new StringBuilder();
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());                             //百度地图定位客户端实例
        mLocationClient.registerLocationListener(new MyLocationListener());
        latLng=new LatLng(43.7980457,125.4392735);
        Orderstate = "1";
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        fangdayi=findViewById(R.id.fangdaout);
        fangdaer=findViewById(R.id.fangdain);
        fangdayi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // frameSwitch4.setVisibility(View.GONE);
                mRealPlaySvInside.setVisibility(View.GONE);
                mRealPlaySvOutside.setVisibility(View.VISIBLE);


            }
        });
        fangdaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // frameSwitch4.setVisibility(View.GONE);
                mRealPlaySvInside.setVisibility(View.VISIBLE);
                mRealPlaySvOutside.setVisibility(View.GONE);
            }
        });
        frameSwitch=findViewById(R.id.lai);
        frameSwitch1=findViewById(R.id.lue);
        close=findViewById(R.id.close);
        frameSwitch2=findViewById(R.id.tu1);
        frameSwitch3=findViewById(R.id.jiankong);
        frameSwitch4=findViewById(R.id.map);
        remotedriving=findViewById(R.id.remotedriving);
        success=findViewById(R.id.appointmentsuccess);
        biaoti=findViewById(R.id.biaoti);
        ticket=findViewById(R.id.ticket);
        talk=findViewById(R.id.talk);
        mCbUrgent = findViewById(R.id.cb_urgent);
        mCbLocking = findViewById(R.id.cb_locking);
        setcarid=findViewById(R.id.busid);
        carspeed=findViewById(R.id.busspeed);
        carorientation=findViewById(R.id.busorientation);
        getinclose = findViewById(R.id.getin);
        cargoout=findViewById(R.id.goout);
        carhavegoout=findViewById(R.id.havegoout);
        mCbUrgent.setOnCheckedChangeListener(this);
        mCbLocking.setOnCheckedChangeListener(this);
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPref.edit();
        //EZOpenSDK.getInstance().openLoginPage();
        //Log.e("preparePlay", MyApplication.sOutsideCameraSerial);
        // Log.e("preparePlay", MyApplication.sOutsideCameraVerifyCode);
        mAppkey="d10d5af273b1418cacfa5e7c3a475509";
        mAccesstoken = mPref.getString("accessToken", null);
        Long mExpireTime = mPref.getLong("accessToken_expireTime", 0);

        if (mExpireTime == 0 || System.currentTimeMillis() > mExpireTime) {
            new AccessTokenLoader().execute();
        } else {
            // preparePlay();
            EZOpenSDK.getInstance().setAccessToken(mAccesstoken);
        }
        //EZOpenSDK.getInstance().setAccessToken(mAccesstoken);

        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        //设置定位的属性，包括定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色，精度圈边框颜色
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory.fromResource(R.drawable.icon_position));
        baiduMap.setMyLocationConfiguration(config);

        String[] ctype2 = new String[]{"起点", "净月区启明软件园-正门门口", "造型中心","会议中心","NBD正门","NBD5号门","试制所正门","自选起点"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype2);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        qidian = super.findViewById(R.id.qidian);
        qidian.setAdapter(adapter2);
        fenxiang=findViewById(R.id.fenxiang);
        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "成功分享到朋友圈", Toast.LENGTH_SHORT).show();
            }
        });
        getinclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    socket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                try {
                    mAddress = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                new Thread() {
                    private byte[] sendBuf;

                    public void run() {
                        boolean x=true;
                        int i=0;
                        while(x&&i<=5) {
                            i++;
                            try {
                                String angle1 = "D2";
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                // Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }.start();
                //getinclose.setVisibility(View.GONE);
                getinclose.setText("车门已关闭");
                getinclose.setEnabled(false);
            }
        });
        qidian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = qidian.getItemAtPosition(i).toString();
                LatLng pt1 = new LatLng(43.789983, 125.428745);
                LatLng pt2 = new LatLng(43.829554, 125.158683);
                LatLng pt3 = new LatLng(43.833187, 125.162277);
                LatLng pt4 = new LatLng(43.829554, 125.158683);
                LatLng pt5 = new LatLng(43.796139, 125.438132);
                LatLng pt6 = new LatLng(43.788141, 125.437872);
                beganla=new LatLng(0,0);
                if (marker != null)
                    marker.remove();
                double[] latlon = new double[2];
                LatLng temp;
                BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
                OverlayOptions option1 = new MarkerOptions().position(beganla).icon(bitmap1);
                marker=(Marker) baiduMap.addOverlay(option1);
                AlphaAnimation animation = new AlphaAnimation(1.0f,1.0f);//marker点动画（需导入百度地图的Animation包）
                animation.setDuration(3000);
                marker.setAnimation(animation);
                marker.startAnimation();

                switch(text){
                    case "净月区启明软件园-正门门口":
                        beganla=pt1;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        //marker.setPosition(beganla);
                        break;
                    case "造型中心":
                        beganla=pt2;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        //marker.setPosition(beganla);
                        break;
                    case "会议中心":
                        beganla=pt3;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        break;
                    case "NBD正门":
                        beganla=pt4;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        break;
                    case "NBD5号门":
                        beganla=pt5;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        break;
                    case "试制所":
                        beganla=pt6;
                        latlon = wgs2BD09(beganla.latitude, beganla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        marker.setPosition(temp);
                        break;
                    case "自选起点":
                        Toast.makeText(MainActivity.this, "请点击地图来选择起点",Toast.LENGTH_SHORT).show();
                        a=1;
                        break;
                    default: break;
                }
                //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

            }
            //构建MarkerOption，用于在地图上添加Marker
//            OverlayOptions option11 = new MarkerOptions()
//                    .position(testtest)
//                    .icon(bitmaptest);


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        String[] monitorType = new String[]{"车辆监控","车一监控","车二监控"};
        ArrayAdapter<String> adapterMonitor = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,monitorType);
        adapterMonitor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monitor = super.findViewById(R.id.monitor);
        monitor.setAdapter(adapterMonitor);
        monitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = monitor.getItemAtPosition(position).toString();
                switch (text) {
                    case "车一监控":
                        Intent it=new Intent(MainActivity.this,Main2Activity.class);
                        monitor.setSelection(0,true);
                        startActivity(it);
                        //monitor.setSelection(0,true);
//                        preparePlayCar1();
//                        frameSwitch.setVisibility(View.GONE);
//                        frameSwitch3.setVisibility(View.VISIBLE);
                        break;
                    case "车二监控":
                        Intent car2=new Intent(MainActivity.this,Main3Activity.class);
                        startActivity(car2);
                        monitor.setSelection(0,true);
                        //Intent it=new Intent(MainActivity.this,Main3Activity.class);
                        //startActivity(it);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] ctype3 = new String[]{"终点", "净月区启明软件园-正门门口", "造型中心","A座门口","NBD正门","NBD5号门","试制所","自选终点"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype3);  //创建一个数组适配器
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        zhongdian = super.findViewById(R.id.zhongdian);
        zhongdian.setAdapter(adapter1);
        zhongdian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = zhongdian.getItemAtPosition(i).toString();
                LatLng pt1 = new LatLng(43.789983, 125.428745);
                LatLng pt2 = new LatLng(43.831354, 125.162825);
                LatLng pt3 = new LatLng(43.800719, 125.436075);
                LatLng pt4 = new LatLng(43.799321, 125.440171);
                LatLng pt5 = new LatLng(43.796139, 125.438132);
                LatLng pt6 = new LatLng(43.788141, 125.437872);
                endla=new LatLng(0,0);
                if (markerEnd != null)
                    markerEnd.remove();
                double[] latlon = new double[2];
                LatLng temp;
                BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
                OverlayOptions option1 = new MarkerOptions().position(endla).icon(bitmap1);
                markerEnd=(Marker) baiduMap.addOverlay(option1);
                AlphaAnimation animation = new AlphaAnimation(1.0f,1.0f);//marker点动画（需导入百度地图的Animation包）
                animation.setDuration(3000);
                markerEnd.setAnimation(animation);
                markerEnd.startAnimation();
                switch(text){
                    case "净月区启明软件园-正门门口":
                        endla=pt1;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option1 = new MarkerOptions().position(endla).icon(bitmap1);
//
//                        baiduMap.addOverlay(option1);
                        break;
                    case "造型中心":
                        endla=pt2;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option2 = new MarkerOptions().position(endla).icon(bitmap2);
//
//                        baiduMap.addOverlay(option2);
                        break;
                    case "A座门口":
                        endla=pt3;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap3 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option3 = new MarkerOptions().position(endla).icon(bitmap3);
//
//                        baiduMap.addOverlay(option3);
                        break;
                    case "NBD正门":
                        endla=pt4;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap4 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option4 = new MarkerOptions().position(endla).icon(bitmap4);
//
//                        baiduMap.addOverlay(option4);
                        break;
                    case "NBD5号门":
                        endla=pt5;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap5 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option5 = new MarkerOptions().position(endla).icon(bitmap5);
//
//                        baiduMap.addOverlay(option5);
                        break;
                    case "试制所":
                        endla=pt6;
                        latlon = wgs2BD09(endla.latitude,endla.longitude);
                        temp = new LatLng(latlon[0],latlon[1]);
                        markerEnd.setPosition(temp);
//                        BitmapDescriptor bitmap6 = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//
//                        OverlayOptions option6 = new MarkerOptions().position(endla).icon(bitmap6);
//
//                        baiduMap.addOverlay(option6);
                        break;
                    case"自选终点":
                        Toast.makeText(MainActivity.this, "请点击地图来选择终点",Toast.LENGTH_SHORT).show();
                        a=2;
                        break;
                    default: break;
                }
                //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        cargoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargoout.setText("正在请求...");
                try{
                    cargoout.setText("正在请求...");
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                cargoout.setText("车门已打开！");
                cargoout.setEnabled(false);
                carhavegoout.setEnabled(true);
                try {
                    socket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                try {
                    mAddress = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                new Thread() {
                    private byte[] sendBuf;

                    public void run() {
                        boolean x=true;
                        int i=0;
                        while(x&&i<=5) {
                            i++;
                            try {
                                String angle1 = "D1";
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                // Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }.start();
            }
        });
        carhavegoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"订单完成",Toast.LENGTH_SHORT).show();
                Orderstate  =  "1";
                success.setVisibility(View.GONE);
                frameSwitch.setVisibility(View.VISIBLE);
                cargoout.setEnabled(true);
                getinclose.setEnabled(true);
                carhavegoout.setEnabled(false);
                cargoout.setText("下车，打开车门！");
                carid ="0";
                marker.remove();
                markerEnd.remove();
                try {
                    socket = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                try {
                    mAddress = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                new Thread() {
                    private byte[] sendBuf;

                    public void run() {
                        boolean x=true;
                        int i=0;
                        while(x&&i<=5) {
                            i++;
                            try {
                                String angle1 = "D4";
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                // Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }.start();
            }
        });
        driving=findViewById(R.id.driving);
        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendControl(1);
                Toast.makeText(MainActivity.this, "请求遥控驾驶", Toast.LENGTH_SHORT).show();

                Intent it=new Intent(MainActivity.this,remotecar.class);
                startActivity(it);
//                frameSwitch.setVisibility(View.GONE);
//                frameSwitch1.setVisibility(View.GONE);
//                frameSwitch3.setVisibility(View.GONE);
//                biaoti.setVisibility(View.GONE);
//                remotedriving.setVisibility(View.VISIBLE);
            }
        });
        goback=findViewById(R.id.gobackfromD);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "结束自动驾驶", Toast.LENGTH_SHORT).show();
                sendControl(2);
                biaoti.setVisibility(View.VISIBLE);
                remotedriving.setVisibility(View.GONE);
                frameSwitch.setVisibility(View.VISIBLE);
            }
        });
        iv_forward=findViewById(R.id.iv_forward);
        iv_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendControl(5);
                Toast.makeText(MainActivity.this, "请求保持前进", Toast.LENGTH_SHORT).show();
            }
        });
        iv_left=findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求左转", Toast.LENGTH_SHORT).show();
                sendControl(3);
                Toast.makeText(MainActivity.this, "请求左转chenggon", Toast.LENGTH_SHORT).show();
            }
        });
        iv_right=findViewById(R.id.iv_right);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求右转", Toast.LENGTH_SHORT).show();
                sendControl(4);
            }
        });
        ll_go_back=findViewById(R.id.iv_back);
        ll_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求减速", Toast.LENGTH_SHORT).show();
                sendControl(6);
            }
        });
        iv=findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求保持目前状态", Toast.LENGTH_SHORT).show();
                sendControl(7);
            }
        });
        iv_accelerator=findViewById(R.id.iv_accelerator);
        iv_accelerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求加油", Toast.LENGTH_SHORT).show();
                sendControl(8);
            }
        });
        ll_brake=findViewById(R.id.iv_brake);
        ll_brake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "请求刹车", Toast.LENGTH_SHORT).show();
                sendControl(9);
            }
        });
        String[] ctype1 = new String[]{"人数", "1", "2","3","4"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype1);  //创建一个数组适配器
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式
        number = super.findViewById(R.id.number1);
        number.setAdapter(adapter3);

        number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = number.getItemAtPosition(i).toString();
                //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        String[] ctype = new String[]{"方向", "顺时针", "逆时针"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

         spinner = super.findViewById(R.id.spacer1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getItemAtPosition(i).toString();
               // Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//        cance.setOnClickListener(new View.OnClickListener() {
//                                     @Override
//                                     public void onClick(View v) {
//
//                                     }
//                                 }
//        );
        ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(MainActivity.this,"已选择起点",Toast.LENGTH_SHORT).show();
                frameSwitch.setVisibility(View.GONE);
                frameSwitch1.setVisibility(View.VISIBLE);
                success.setVisibility(View.GONE);

            }
        });
        frameSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                success.setVisibility(View.VISIBLE);
                frameSwitch1.setVisibility(View.GONE);

            }
        });
        fanhui=findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitor.setSelection(0,true);
                frameSwitch.setVisibility(View.VISIBLE);
                frameSwitch3.setVisibility(View.GONE);
                frameSwitch4.setVisibility(View.VISIBLE);
                mRealPlaySvOutside.setVisibility(View.VISIBLE);
                mRealPlaySvInside.setVisibility(View.VISIBLE);
                mEZPlayerOutside.stopRealPlay();
                mEZPlayerInside.stopRealPlay();
            }
        });
//        mRealPlaySvOutside.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // frameSwitch4.setVisibility(View.GONE);
//               // mRealPlayShInside.setVisibility(View.GONE);
//            }
//        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                       close.setText("正在请求......");
                       sendRequestWithOkHttp1();
                       while(i<10){
                           if(!closedoor.equals("1")){
                              i=10;
                           }
                           else{
                               try{
                                   sleep(1000);
                                   close.setText("正在请求:1s");
                               }catch (Exception e){

                               }
                           }
                           i++;
                       }

                        if(!closedoor.equals("1")){
                            Toast.makeText(MainActivity.this, "关闭车门成功", Toast.LENGTH_SHORT).show();
                            close.setText("关闭车门");
                            closedoor="1";
                            close.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(MainActivity.this, "关闭车门失败，请重试！", Toast.LENGTH_SHORT).show();
                            close.setText("请点击重试");
                        }
                success.setVisibility(View.VISIBLE);
                frameSwitch.setVisibility(View.GONE);
                frameSwitch1.setVisibility(View.GONE);
                frameSwitch3.setVisibility(View.GONE);
//                    HttpUtil.sendOkHttpRequest("http://192.168.1.101/get_data.xml", "关闭车门",new okhttp3.Callback() {
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//                            String responseData=response.body().string();
//                            if(response!=null){
//                                Toast.makeText(MainActivity.this, "关闭车门成功", Toast.LENGTH_SHORT).show();
//                                close.setVisibility(View.GONE);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//                            Toast.makeText(MainActivity.this, "关闭车门失败，请重试！", Toast.LENGTH_SHORT).show();
//                        }
//                    });
            }
        });

//        pickup = (Button) super.findViewById(R.id.pickup1);                                         //起点按钮监听事件
//        pickup.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this, "请点击地图来选择起点",Toast.LENGTH_SHORT).show();
//                a=1;                                                                                //定义屏幕选择点为起点
//            }
//        });


//        end = (Button) super.findViewById(R.id.end1);                                               //终点按钮监听事件
//        end.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this, "请点击地图来选择终点",Toast.LENGTH_SHORT).show();
//                a=2;                                                                                //定义屏幕选择点为终点
//            }
//        });


        appointment1 = (Button) super.findViewById(R.id.appointment);                                 //预约按钮
        time = new TimeCount(2000, 1000);
        appointment1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(carid.equals("0")) {
                    boolean a = true;
                    if (beganla == null) {
                        Toast.makeText(MainActivity.this, "请选择起点", Toast.LENGTH_SHORT).show();
                        a = false;
                    } else if (endla == null) {
                        Toast.makeText(MainActivity.this, "请选着终点", Toast.LENGTH_SHORT).show();
                        a = false;
                    } else if (number.getSelectedItem().toString().equals("人数")) {
                        Toast.makeText(MainActivity.this, "请选人数", Toast.LENGTH_SHORT).show();
                        a = false;
                    } else {
                        number11 = number.getSelectedItem().toString();
                    }
                    if (spinner.getSelectedItem().toString().equals("请选方向")) {
                        //Toast.makeText(MainActivity.this, "请选着终点",Toast.LENGTH_SHORT).show();
                        a = false;
                    } else {
                        fangxiang = spinner.getSelectedItem().toString();
                    }
                    sendRequestWithOkHttp();

                    if (a) {
                        cance.setVisibility(View.VISIBLE);
//                        setDrivingCarPosition();
                        time.start();

                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "你已有订单",Toast.LENGTH_SHORT).show();
                }
            }
        });



        cance = (Button) super.findViewById(R.id.cancel);
        cance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                time.cancel();
                carid = "0";
                Orderstate = "1";
                Toast.makeText(MainActivity.this, "取消预约",Toast.LENGTH_SHORT).show();
                appointment1.setText("预约");
                //time.onFinish();
            }
        });
        cance.setVisibility(View.GONE);
        /*jiankong2=findViewById(R.id.time1);
        jiankong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(it);
                monitor.setSelection(0,true);
            }
        });
        jiankong=findViewById(R.id.time);
        jiankong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparePlayCar1();
                frameSwitch.setVisibility(View.GONE);
                frameSwitch3.setVisibility(View.VISIBLE);
            }
        });*/
        /*
        * 地图普通页面点击以及POI点击监听事件
        * */
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            //地图单击事件回调方法
            @Override
            public void onMapClick(LatLng latLng) {

               if(a==1) {
                   isUserBegin = true;
                   beganla=latLng;
                   //baiduMap.clear();
                   BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_st);

                   OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap);

                   baiduMap.addOverlay(option);
                   a=0;

               }
               else if(a==2){
                   endla=latLng;
                   isUserEnd = true;
                   BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
                   OverlayOptions option = new MarkerOptions().position(latLng).icon(bitmap);

                   baiduMap.addOverlay(option);
                   setlocation();
                   a=0;
                   /*Button button1 = new Button(getApplicationContext());
                   button1.setBackgroundResource(R.drawable.car1);

                   button1.setTextColor(0xAAFF0000);

                   InfoWindow  mInfoWindow = new InfoWindow(button1, beganla, -47);
                   //显示InfoWindow

                   baiduMap.showInfoWindow(mInfoWindow);*/
               }
               else {
                   Toast.makeText(MainActivity.this, "点击到地图上了！纬度" + latLng.latitude + "经度" + latLng.longitude, Toast.LENGTH_SHORT).show();
               }
            }

            //Poi 单击事件回调方法，比如点击到地图上面的商店，公交车站，地铁站等等公共场所
            @Override
            public boolean onMapPoiClick(final MapPoi mapPoi) {
                Snackbar.make(mapView,"点到了一个兴趣点,是否设为起点?",Snackbar.LENGTH_SHORT)
                        .setAction("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //创建InfoWindow展示的view
                                Button button = new Button(getApplicationContext());
                                button.setBackgroundResource(R.drawable.popup);
                                button.setText("起点");
                                button.setTextColor(0xAAFF0000);
                                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                                InfoWindow mInfoWindow = new InfoWindow(button, mapPoi.getPosition(), -47);
                                //显示InfoWindow
                                baiduMap.showInfoWindow(mInfoWindow);
                            }
                        }).show();
                return true;
            }
        });

        /*
        * 检索结果覆盖物监听事件
        * 设置弹窗说明该POI
        * */
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getBaseContext(), "点击了标记物", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final PoiSearch search = PoiSearch.newInstance();                                                  //poi检索
        /*
        * POI检索的监听对象
        * */
        OnGetPoiSearchResultListener resultListener = new OnGetPoiSearchResultListener() {
            //获得POI的检索结果，一般检索数据都是在这里获取
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //如果搜索到的结果不为空，并且没有错误
                if (poiResult != null && poiResult.error == PoiResult.ERRORNO.NO_ERROR) {
                    MyOverLay overlay = new MyOverLay(baiduMap, search);                            //这传入search对象，因为一般搜索到后，点击时方便发出详细搜索
                    //设置数据
                    overlay.setData(poiResult);
                    //添加到地图
                    overlay.addToMap();
                    //将显示视图拉倒正好可以看到所有POI兴趣点的缩放等级
                    overlay.zoomToSpan();//计算工具
                    //设置标记物的点击监听事件
                    baiduMap.setOnMarkerClickListener(overlay);
                } else {
                    Toast.makeText(getApplication(), "搜索不到你需要的信息！", Toast.LENGTH_SHORT).show();
                }
            }
            //获得POI的详细检索结果，如果发起的是详细检索，这个方法会得到回调(需要uid)
            //详细检索一般用于单个地点的搜索，比如搜索一大堆信息后，选择其中一个地点再使用详细检索
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getApplication(), "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {// 正常返回结果的时候，此处可以获得很多相关信息
                    Toast.makeText(getApplication(), poiDetailResult.getName() + ": "
                                    + poiDetailResult.getAddress(),
                            Toast.LENGTH_LONG).show();
                }
            }

            //获得POI室内检索结果
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };

        //设置POI检索监听者；
        search.setOnGetPoiSearchResultListener(resultListener);


        /*
        * 写的有点乱,大概就是上面是百度地图的搜索交互等
        * 下面是控件的初始化以及点击事件
        * */
        //toolbar相关设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);                                                       //将actionbar改为toolbar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);              //侧滑菜单和界面总Layout

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);      //侧滑菜单布局

        cityNameText = (EditText)findViewById(R.id.city_name_text);
        searchText = (EditText)findViewById(R.id.search_text);
        cityNameText.setFocusable(false);
        cityNameText.setFocusableInTouchMode(false);
        /*
        * 搜索按钮加载及其点击事件
        * */
       Button searchButton = (Button)findViewById(R.id.startSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String keyString = searchText.getText().toString().trim();
                String cityName = cityNameText.getText().toString().trim();
                if (TextUtils.isEmpty(keyString)) {
                    Toast.makeText(MainActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
                //发起检索
                PoiCitySearchOption poiCity = new PoiCitySearchOption();
                poiCity.keyword(keyString).city(cityName);                                            //这里还能设置显示的个数，默认显示10个
                search.searchInCity(poiCity);                                                        //执行搜索，搜索结束后，在搜索监听对象里面的方法会被回调
            }
        });

        /*FloatingActionButton make_sure = (FloatingActionButton) findViewById(R.id.sure);
        make_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                                        //确认下单按钮监听事件
                Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT).show();
            }
        });*/
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {                                                                    //设置toolbar的HomeAsUp按钮可用以及图片
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.personal);
        }

        /*
        * 申请百度地图使用相关权限
        * */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }

        /*
        * 加载侧滑菜单的头部布局并设置监听事件
        * */
        View headLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        View headImage = headLayout.findViewById(R.id.icon_image);
        headImage.setOnClickListener(this);
        /*
        * 设置侧滑菜单nav_menu部分的点击事件
        * */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_order:
                        Intent intent = new Intent(MainActivity.this, OrderManageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_setting:
                        Intent intent1 = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent1);
                        break;
                }

                return true;
            }
        });
    }
    //解析萤石返回数据
    private EZReturningData parseResponse(final String responseData) {

        Gson gson = new Gson();
        EZReturningData returningData = gson.fromJson(responseData, EZReturningData.class);
        Log.e("parseResponse", "accessToken:" + returningData.data.accessToken);
        Log.e("parseResponse", "mExpireTime:" + returningData.data.expireTime);
        Log.e("parseResponse", "code:" + returningData.code);
        Log.e("parseResponse", "msg:" + returningData.msg);
        return returningData;


    }

    //发送请求到萤石官网
    private EZReturningData sendRequestToYs() {
        final String appKey ="d10d5af273b1418cacfa5e7c3a475509";
        final String appSecret ="aba2ea24a9f7de6aaa7028cd319cb5d1";
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("appKey", appKey)
                .add("appSecret", appSecret).build();
        Request request = new Request.Builder().url(" https://open.ys7.com/api/lapp/token/get")
                .post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.e("11111",responseData);
            return parseResponse(responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //七天更换accesstoken
    private class AccessTokenLoader extends AsyncTask<Void, Void, EZReturningData> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("加载中,请稍后......");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected EZReturningData doInBackground(Void... voids) {
            return sendRequestToYs();
        }

        @Override
        protected void onPostExecute(EZReturningData rd) {
            progressDialog.dismiss();
            if (rd.code.equals("200")) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("accessToken", rd.data.accessToken);
                editor.putLong("accessToken_expireTime", rd.data.expireTime);
                editor.apply();
                mAccesstoken = mPref.getString("accessToken", null);
                EZOpenSDK.getInstance().setAccessToken(mAccesstoken);
                //preparePlay();
            } else {
                Toast.makeText(getApplicationContext(), "accessToken获取失败！！！", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
    车辆预约弹窗
     */
    class TimeCount extends CountDownTimer {
        boolean a=true;
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            //   appointment1.setBackgroundColor(Color.parseColor("#B6B6D8"));
            //  appointment1.setClickable(false);
               // sendRequestWithOkHttp();
                //b++;
                getOrderState();

            appointment1.setText("正在预约: " +millisUntilFinished/1000 + "秒");

        }
        @Override
        public void onFinish() {
            cance.setVisibility(View.GONE);

            if (Orderstate.equals("接受成功!")) {
                sendRequestWithOkHttp1();
            appointment1.setText("预约");
            mOffTextView = new TextView(MainActivity.this);
            mOffTextView.setTextSize(20);
            frameSwitch.setVisibility(View.GONE);
            talk.setVisibility(View.GONE);
            //success.setVisibility(View.GONE);
            frameSwitch1.setVisibility(View.VISIBLE);
            ticket.setEnabled(true);
            driving.setEnabled(true);
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(R.drawable.ticket_bus);
            frameSwitch2.addView(imageView);
            baiduMap.addOverlay(circle1);
            baiduMap.addOverlay(circle2);
            baiduMap.addOverlay(circle3);
            baiduMap.addOverlay(circle4);
            baiduMap.addOverlay(circle5);
            baiduMap.addOverlay(circle6);
            present = latLng;
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAAFF0000).points(points);
            Polyline mPolyline = (Polyline) baiduMap.addOverlay(ooPolyline);
            mDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("预约成功！" + getcarmessge)
                    .setCancelable(false)
                    .setView(mOffTextView) //
                    .setPositiveButton("打开车门", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int i = 0;
                            // close.setText("正在请求......");
                            try {
                                socket = new DatagramSocket();
                            } catch (SocketException e) {
                                e.printStackTrace();
                            }
                            try {
                                mAddress = InetAddress.getByName(ip);
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            }
                            new Thread() {
                                private byte[] sendBuf;

                                public void run() {
                                    boolean x=true;
                                    int i=0;
                                    while(x&&i<=5) {
                                        i++;
                                        try {
                                            String angle1 = "D1";
                                            sendBuf = angle1.getBytes("utf-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                                        try {
                                            socket.send(recvPacket1);
                                            socket.close();
                                            // Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (doorState.equals("已开门")) {
                                            success.setVisibility(View.VISIBLE);
                                            frameSwitch.setVisibility(View.GONE);
                                            frameSwitch1.setVisibility(View.GONE);
                                            frameSwitch3.setVisibility(View.GONE);
                                            x=false;
                                        }else {
                                            try{
                                                sleep(1000);
                                            }catch (Exception e){ }
                                        }

                                    }
                                }
                            }.start();
                            if (!doorState.equals("已开门")) {
                                //close.setVisibility(View.VISIBLE);
//                                success.setVisibility(View.VISIBLE);
//                                frameSwitch.setVisibility(View.GONE);
//                                frameSwitch1.setVisibility(View.GONE);
//                                frameSwitch3.setVisibility(View.GONE);
//                                opendoor = "1";
                                //Toast.makeText(MainActivity.this, "打开车门成功", Toast.LENGTH_SHORT).show();
                                // close.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "打开车门失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
//                            else {
//                                Toast.makeText(MainActivity.this, "打开车门失败，请重试！", Toast.LENGTH_SHORT).show();
//
//                            }
                            // close.setVisibility(View.VISIBLE);
//                                    opendoor="1";
                            //                                  Toast.makeText(MainActivity.this, "打开车门成功", Toast.LENGTH_SHORT).show();

//                                if(!opendoor.equals(1)) {
//                                    close.setVisibility(View.VISIBLE);
//                                    opendoor="1";
//                                    Toast.makeText(MainActivity.this, "车门打开成功", Toast.LENGTH_SHORT).show();
//                                    //carmessage=null;
//                                }else{
//                                    Toast.makeText(MainActivity.this, "打开失败，请重试", Toast.LENGTH_SHORT).show();
//                                }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "取消订单", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            mOffTime.cancel();
                        }
                    })
                    .create();
            mDialog.show();
            mDialog.setCanceledOnTouchOutside(false);

            mOffHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what > 0) {
                        ////动态显示倒计时
                        mOffTextView.setText("       车辆即将到达，倒计时：" + msg.what + "秒");
                        //mOffTextView.setText("       车辆已经到达!");
                    } else {
                        ////倒计时结束自动关闭
                        mOffTextView.setText("       车辆已经到达!");
                        mOffTime.cancel();
                    }
                    super.handleMessage(msg);
                }

            };
            mOffTime = new Timer(true);
            TimerTask tt = new TimerTask() {
                int countTime = 30;

                public void run() {
                    if (countTime > 0) {
                        countTime--;
                    }
                    Message msg = new Message();
                    msg.what = countTime;
                    mOffHandler.sendMessage(msg);
                }
            };

            mOffTime.schedule(tt, 1000, 1000);
        }
        else{
                carid = "0";
                appointment1.setText("预约");
                Toast.makeText(MainActivity.this,"预约失败，请检查网络后重试！",Toast.LENGTH_SHORT).show();
            }
            }

    }

    public void setlocation(){

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
               double a=beganla.latitude;
               double b=endla.latitude;
               double c= (a-b)/40;
               double d=(beganla.longitude-endla.longitude)/40;
               int i=0;
               while(i<38) {
                   try {
                       sleep(500);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   LatLng laa=new LatLng(beganla.latitude-c,beganla.longitude-d);
                   present=laa;
                   i++;
               }
            }
        });

    }
    /**
     * 重写和设置最基础的覆盖类
     * 用来处理搜索到的对象的信息数据
     * 这里的PoiOverlay是工具类里面的类，需要自己去复制过来使用
     */
    public class MyOverLay extends PoiOverlay {
        /**
         * 构造函数
         */
        PoiSearch poiSearch;

        public MyOverLay(BaiduMap baiduMap, PoiSearch poiSearch) {
            super(baiduMap);
            this.poiSearch = poiSearch;
        }

        /**
         * 覆盖物被点击时
         */
        @Override
        public boolean onPoiClick(int i) {
            //获取点击的标记物的数据
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            Toast.makeText(MainActivity.this, poiInfo.name + "   " + poiInfo.address + "   " + poiInfo.phoneNum, Toast.LENGTH_SHORT).show();
            Log.e("TAG", poiInfo.name + "   " + poiInfo.address + "   " + poiInfo.phoneNum);
            //  发起一个详细检索,要使用uid
            poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
            return true;
        }
    }

    /*
    * 将位置移动到目前所在位置(默认北京)
    * */
    private void navigateTo(BDLocation location) {
        LatLng ll = null;
        if (isFirstLocate) {
           // LatLng latLng = new LatLng(43.797550, 125.434450);

            //ll = present1;
            if (Orderstate.equals("接受成功!")) {
                ll = present1;
            }
            else {
                ll=new LatLng(location.getLatitude(),location.getLongitude());
            }
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        //ll = present1;
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
//        locationBuilder.latitude(ll.latitude);
//        locationBuilder.longitude(ll.longitude);
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    /*
    * 下面的两个方法对mapView进行管理,及时释放资源
    * */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /*
            * 启动定位客户端
            * */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    /*
    * 设置百度地图刷新事件间隔以及其他界面配置
    * */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        //配置定位SDK参数
        option.setCoorType("bd09ll");   //设置返回经纬度坐标类型，默认gcj02
        option.setScanSpan(5000);        //设置发起定位请求的间隔，int类型，单位ms
        option.setOpenGps(true);          //设置是否使用gps，默认false,使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setIsNeedAddress(true);     //是否需要地址信息，默认为不需要，即参数为false
        option.setLocationNotify(true);
        //绘制地图折线代码实现
        LatLng p1 = new LatLng(43.7981498, 125.4393544);
        LatLng p2 = new LatLng(43.7982799, 125.4373781);
        LatLng p3 = new LatLng(43.7979936, 125.4355007);
        LatLng p4 = new LatLng(43.7972453, 125.4338927);
        LatLng p5 = new LatLng(43.7961196, 125.4328776);
        LatLng p6 = new LatLng(43.7945384, 125.4321949);
        //LatLng p7 = new LatLng(43.789983, 125.428745);
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);

//        OverlayOptions ooPolyline = new PolylineOptions().width(10)
//                .color(0xAAFF0000).points(points);
//        Polyline mPolyline = (Polyline) baiduMap.addOverlay(ooPolyline);


        mLocationClient.setLocOption(option);
    }

    /*
    * 活动销毁时停止定位,防止程序一直在后台进行定位,消耗电量
    * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        if (mEZPlayerOutside != null) {
            mEZPlayerOutside.release();
        }
        if (mEZPlayerInside != null) {
            mEZPlayerInside.release();
        }
    }

    //申请相关权限的函数
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }


    /*
        * 加载menu目录下的toolbar的菜单布局
        * */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    /*
    * toolbar上各个按钮的监听事件
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    /*
    * 按钮监听器点击事件
    * */
    @Override
    public void onClick(View view) {
        if (MainActivity.isLogin) {
            Intent intent1 = new Intent(MainActivity.this, PersonalInforActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent2);
        }
    }

    /*
    * 百度地图的定位功能实现
    * */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            sendRequestWithOkHttp1();
            if(marker1 != null){
                marker1.remove();
            }
            //加上搜索城市名
            cityNameText.setText(bdLocation.getCity());
//
//            //创建InfoWindow展示的view
//            Button button = new Button(getApplicationContext());
//            button.setBackgroundResource(R.drawable.popup);
//        double distance = DistanceUtil.getDistance(p1, p2);
//        String sDistance = String.valueOf(distance);
//        button.setText(sDistance);
//            button.setText("正门");
//            button.setTextColor(0xAAFF0000);
            //定义用于显示该InfoWindow的坐标点
            LatLng pt1 = new LatLng(43.7981498, 125.4393544);
            LatLng pt2 = new LatLng(43.7982799, 125.4373781);
            LatLng pt3 = new LatLng(43.7979936, 125.4355007);
            LatLng pt4 = new LatLng(43.7972453, 125.4338927);
            LatLng pt5 = new LatLng(43.7961196, 125.4328776);
            LatLng pt6 = new LatLng(43.7945384, 125.4321949);

            //LatLng pt7 = new LatLng(43.789983, 125.428745);
           /* LatLng pt1 = new LatLng(43.838889, 125.170671);
            LatLng pt2 = new LatLng(43.839465, 125.173954);
            LatLng pt3 = new LatLng(43.841854, 125.17523);
            LatLng pt4 = new LatLng(43.843577, 125.17134);
            LatLng pt5 = new LatLng(43.841549, 125.165879);*/
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//            InfoWindow mInfoWindow = new InfoWindow(button, pt1, -47);
            //显示InfoWindow
//            baiduMap.showInfoWindow(mInfoWindow);
            LatLng car1=new LatLng(43.79319,125.42917);
            View bubble = new View(MainActivity.this);



            //appointment1.setText("q");
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.alc1);


            OverlayOptions option = new MarkerOptions()
                    .position(present1)
                    .icon(bitmap);
            marker1=(Marker)baiduMap.addOverlay(option);
            double[] qidian=MillierConvertion(present1.latitude,present1.longitude);
            double[] zhongdian=MillierConvertion(prepresent.latitude,prepresent.longitude);
            int res=function(qidian,zhongdian);
            marker1.setRotate(res);
            AlphaAnimation animation = new AlphaAnimation(0.8f,0.8f);//marker点动画（需导入百度地图的Animation包）
            animation.setDuration(3000);
            marker1.setAnimation(animation);
            marker1.startAnimation();
            marker1.setPosition(present1);
            //Toast.makeText(MainActivity.this, "当前车身位置：纬度" + present1.latitude + "经度" + present1.longitude, Toast.LENGTH_SHORT).show();

            bubble.setBackgroundResource(R.drawable.car2);
//
//            if(present==null) {
//                InfoWindow  mInfoWindow = new InfoWindow(bubble, car1, -47);
//                //显示InfoWindow
//                 baiduMap.showInfoWindow(mInfoWindow);
//
//            }else{
//                InfoWindow  mInfoWindow = new InfoWindow(bubble, present, -47);
//                //显示InfoWindow
//                baiduMap.showInfoWindow(mInfoWindow);
//            }
//            CircleOptions circle1 = new CircleOptions().center(pt1).fillColor(0x80ff0000).radius(1);
//            CircleOptions circle2 = new CircleOptions().center(pt2).fillColor(0x80ff0000).radius(1);
//            CircleOptions circle3 = new CircleOptions().center(pt3).fillColor(0x80ff0000).radius(1);
//            CircleOptions circle4 = new CircleOptions().center(pt4).fillColor(0x80ff0000).radius(1);
//            CircleOptions circle5 = new CircleOptions().center(pt5).fillColor(0x80ff0000).radius(1);
//            CircleOptions circle6 = new CircleOptions().center(pt6).fillColor(0x80ff0000).radius(1);
            circle1 = new CircleOptions().center(pt1).fillColor(0x80ff0000).radius(1);
           circle2 = new CircleOptions().center(pt2).fillColor(0x80ff0000).radius(1);
            circle3 = new CircleOptions().center(pt3).fillColor(0x80ff0000).radius(1);
            circle4 = new CircleOptions().center(pt4).fillColor(0x80ff0000).radius(1);
             circle5 = new CircleOptions().center(pt5).fillColor(0x80ff0000).radius(1);
           circle6 = new CircleOptions().center(pt6).fillColor(0x80ff0000).radius(1);
           // CircleOptions circle7 = new CircleOptions().center(pt7).fillColor(0x80ff0000).radius(15);
//            baiduMap.addOverlay(circle1);
//            baiduMap.addOverlay(circle2);
//            baiduMap.addOverlay(circle3);
//            baiduMap.addOverlay(circle4);
//            baiduMap.addOverlay(circle5);
//            baiduMap.addOverlay(circle6);

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }

        }
    }
    private void getOrderState(){

        new Thread( new Runnable(){
            @Override
            public  void run(){
              try{
                 String url="http://39.106.26.98:8080/show3/";
                  Request request = new Request.Builder().url(url)
                          .build();

                  Response response = client.newCall(request).execute();

                  String responseData=response.body().string();
                  Document doc = Jsoup.parse(responseData);
                  Elements elements=doc.getElementsByTag("tr");
                Orderstate=elements.get(1).select("td").get(6).text();
                doorState=elements.get(1).select("td").get(7).text();

              }catch (Exception e){
                  e.printStackTrace();
              }
            }
        }).start();
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder qq=new StringBuilder();

                try{
                    String fang;
                        if(fangxiang.equals("顺时针")){
                            fang="1";
                        }else{
                        fang="2";
                    }
                    String url = "";

                    double[] latlon = new double[2];
                    double[] latlon1 = new double[2];
                    latlon= bd092WGS(beganla.latitude, beganla.longitude);
                    latlon1= bd092WGS(endla.latitude, endla.longitude);
                    if (!isUserBegin && !isUserEnd){
                        url="http://39.106.26.98:8080/show3/?qdj="+beganla.longitude+"&qdw="+ beganla.latitude +"&zdj="+ endla.longitude + "&zdw="+ endla.latitude +"&szfx="+fang+"&ps="+number11;
                    }
                    else if (isUserBegin && !isUserEnd) {
                        url="http://39.106.26.98:8080/show3/?qdj="+latlon[1]+"&qdw="+latlon[0]+"&zdj=" + endla.longitude +" &zdw=" + endla.latitude +"&szfx="+fang+"&ps="+number11;
                        isUserBegin = false;
                    }
                    else if (!isUserBegin && isUserEnd) {
                        url="http://39.106.26.98:8080/show3/?qdj=" + beganla.longitude +"&qdw=" + beganla.latitude +"&zdj="+latlon1[1]+"&zdw="+latlon1[0]+"&szfx="+fang+"&ps="+number11;
                        isUserEnd = false;
                    }
                    else {
                        url="http://39.106.26.98:8080/show3/?qdj="+latlon[1]+"&qdw="+latlon[0]+"&zdj="+latlon1[1]+"&zdw="+latlon1[0]+"&szfx="+fang+"&ps="+number11;
                        isUserEnd = false;
                        isUserBegin = false;
                    }
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData=response.body().string();
                    Document doc = Jsoup.parse(responseData);
                    Elements elements=doc.getElementsByTag("tr");
                    carid=elements.get(1).select("td").get(5).text();
                    setcarid.setText("车辆id:"+carid);
                    Request request1 = new Request.Builder().url("http://39.106.26.98:8080/show4/")
                            .build();
                    Response response1 = client.newCall(request1).execute();
                    String responseData1=response1.body().string();
                    Document doc1 = Jsoup.parse(responseData1);
                    Elements elements1=doc1.getElementsByTag("tr");
                    int a=parseInt(carid);
                    Boolean jiance=true;

                    switch (a){
                        case 201801:
                            busspeed=elements1.get(1).select("td").get(2).text();
                            busor=elements1.get(1).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201802:
                            busspeed=elements1.get(2).select("td").get(2).text();
                            busor=elements1.get(2).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201803:
                            busspeed=elements1.get(3).select("td").get(2).text();
                            busor=elements1.get(3).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201804:
                            busspeed=elements1.get(4).select("td").get(2).text();
                            busor=elements1.get(4).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201805:
                            busspeed=elements1.get(5).select("td").get(2).text();
                            busor=elements1.get(5).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201806:
                            busspeed=elements1.get(6).select("td").get(2).text();
                            busor=elements1.get(6).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201807:
                            busspeed=elements1.get(7).select("td").get(2).text();
                            busor=elements1.get(7).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201808:
                            busspeed=elements1.get(2).select("td").get(2).text();
                            busor=elements1.get(2).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201809:
                            busspeed=elements1.get(9).select("td").get(2).text();
                            busor=elements1.get(9).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                        case 201810:
                            busspeed=elements1.get(10).select("td").get(2).text();
                            busor=elements1.get(10).select("td").get(1).text();
                            carspeed.setText("速度:"+busspeed);
                            carorientation.setText("方向盘转角:"+busor);
                            break;
                    }
//                    for (Element element : elements) {
//                        //String temp =element.child(0).attr("href")+"\t"+element.text();
//                        String temp =element.toString();
//                        if(temp.length()==20){
//                            String la1=temp.substring(4,13);
//                            // System.out.println(la1+i);
//                            getcarmessge.append(la1);
//                        }
//                        if(temp.length()==21){
//                            String lo1=temp.substring(4,13);
//                            getcarmessge.append(lo1);
//                            //cance.setText(lo1);
//                            // System.out.println(la1+i);
//                        }
//
//                    }


                  //  getcarmessge=qq;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp1(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//
                    Request request1 = new Request.Builder().url("http://39.106.26.98:8080/show5/")
                            .build();
                    int a=parseInt(carid);
                    while(true){                                                                     //解析网页的经纬度
                        if(!carid.equals("0")){
                            Response response1 = client.newCall(request1).execute();

                            String responseData1 = response1.body().string();
                            Document doc1 = Jsoup.parse(responseData1);

                            Elements elements1 = doc1.getElementsByTag("tr");

                            for(int index = 1; index < 11; index++){

                                if(elements1.get(index).select("td").get(0).text().equals(carid)){

                                    double lon=Double.parseDouble(elements1.get(index).select("td").get(1).text());
                                    double lat=Double.parseDouble(elements1.get(index).select("td").get(2).text());
                                    double[] latlon = wgs2BD09(lat,  lon); //地球坐标系转百度坐标
                                    Log.e("111111111111111111111", lon+"wwwwwwwwwwwwwwwwwwwwwwwwwww" );
                                    LatLng pre=new LatLng(latlon[0],latlon[1]);
                                    prepresent=present;
                                    present1 =pre;
                                }
                            }
                        }

                        //sleep(500);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void sendControl(int flag){
        switch (flag) {
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_kskz=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_jskz=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            String url;
                            url="http://39.106.26.98:8080/show4/?yk_jdl=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 4:
                try {
                    String url = "http://39.106.26.98:8080/show4/?yk_jdr=1";
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 5:
            case 6:
            case 7:
                break;
            case 8:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_yma=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case 9:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_ymd=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case 10:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_jjzd=1";
                            Request request = new Request.Builder().url(url)
                                    .build();
                            Response response = client.newCall(request).execute();

                            String responseData=response.body().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case 11:
                try {
                    String url = "http://39.106.26.98:8080/show4/?yk_cmsd=1";
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }
    private void sendRequestWithOkHttp2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder qq=new StringBuilder();
                try{
                    RequestBody requestBody =new FormBody.Builder().add("cardoor","openthedoor")
       .build();
                    String url;
                    url="http://39.106.26.98:8080/show3/?qdj="+beganla.longitude+"&qdw="+beganla.latitude+"&zdj="+endla.longitude+"&zdw="+endla.latitude+"&szfx='opendoor'"+"&ps="+number11;
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData=response.body().string();
                   opendoor=responseData;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void opencardoorhttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody =new FormBody.Builder().add("cardoor","打开车门")
                            .build();
                    Request request = new Request.Builder().url("http://39.106.26.98:8080/show3/").post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData=response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /*
     *CheckBox被选中时调用
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        //mCbUrgent被选中
        if (compoundButton.getId() == R.id.cb_urgent) {
            if (b) {
                mEmergencyBrakingStatus = 1;
                Toast.makeText(getBaseContext(), "紧急制动", Toast.LENGTH_SHORT).show();
            } else {
                mEmergencyBrakingStatus = 0;
            }
           sendControl(10);
        }
        //mCbLocking被选中
        else if (compoundButton.getId() == R.id.cb_locking) {
            if (b) {
                mLockingStatus = 0;
                Toast.makeText(getBaseContext(), "车门锁定", Toast.LENGTH_SHORT).show();
            } else {
                mLockingStatus = 1;
            }
            sendControl(11);
        }
    }
    private void showResponse(final String a){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //button.setText("3");
                cance.setText(a);
            }
        });
    }
    private void setDrivingCarPosition(){
        Toast.makeText(MainActivity.this, "当前车身位置：纬度" + present1.latitude + "经度" + present1.longitude, Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true){
                        if (marker1 != null){
                            marker1.remove();
                        }
                        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.alc1);
                        OverlayOptions option = new MarkerOptions()
                                .position(present1)
                                .icon(bitmap);
                        marker1=(Marker)baiduMap.addOverlay(option);
                        double[] qidian=MillierConvertion(present1.latitude,present1.longitude);
                        double[] zhongdian=MillierConvertion(prepresent.latitude,prepresent.longitude);
                        int res=function(qidian,zhongdian);
                        marker1.setRotate(res);
                        AlphaAnimation animation = new AlphaAnimation(0.8f,0.8f);//marker点动画（需导入百度地图的Animation包）
                        animation.setDuration(3000);
                        marker1.setAnimation(animation);
                        marker1.startAnimation();
                        marker1.setPosition(present1);
                        Toast.makeText(MainActivity.this, "当前车身位置：纬度" + present1.latitude + "经度" + present1.longitude, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void preparePlayCar2() {
        mEZPlayerOutside = EZOpenSDK.getInstance().createPlayer("C79439324", 1);
        mEZPlayerInside = EZOpenSDK.getInstance().createPlayer("C79439341", 1);
        mRealPlaySvOutside = findViewById(R.id.realplay_sv_outside);
        mRealPlaySvInside = findViewById(R.id.realplay_sv_inside);
        mRealPlayShOutside = mRealPlaySvOutside.getHolder();
        mRealPlayShInside = mRealPlaySvInside.getHolder();
        mRealPlayShOutside.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mEZPlayerOutside != null) {
                    mEZPlayerOutside.setSurfaceHold(surfaceHolder);
                } else {
                }

                mRealPlayShOutside = surfaceHolder;
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mEZPlayerOutside != null) {
                    mEZPlayerOutside.setSurfaceHold(null);
                }

                mRealPlayShOutside = null;
            }
        });
        mRealPlayShInside.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mEZPlayerInside != null) {
                    mEZPlayerInside.setSurfaceHold(surfaceHolder);
                } else {
                }

                mRealPlayShInside = surfaceHolder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mEZPlayerInside != null) {
                    mEZPlayerInside.setSurfaceHold(null);
                }

                mRealPlayShInside = null;
            }
        });
        mEZPlayerOutside.setSurfaceHold(mRealPlayShOutside);
        mEZPlayerInside.setSurfaceHold(mRealPlayShInside);
        mEZPlayerOutside.setPlayVerifyCode("UNMYSG");
        mEZPlayerInside.setPlayVerifyCode("VQDVST");

        mEZPlayerOutside.startRealPlay();
        mEZPlayerInside.startRealPlay();
    }
    private void preparePlayCar1() {
        mEZPlayerOutside = EZOpenSDK.getInstance().createPlayer("C76338328", 1);
        mEZPlayerInside = EZOpenSDK.getInstance().createPlayer("C76338280", 1);
        mRealPlaySvOutside = findViewById(R.id.realplay_sv_outside);
        mRealPlaySvInside = findViewById(R.id.realplay_sv_inside);
        mRealPlayShOutside = mRealPlaySvOutside.getHolder();
        mRealPlayShInside = mRealPlaySvInside.getHolder();
        mRealPlayShOutside.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mEZPlayerOutside != null) {
                    mEZPlayerOutside.setSurfaceHold(surfaceHolder);
                } else {
                }

                mRealPlayShOutside = surfaceHolder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mEZPlayerOutside != null) {
                    mEZPlayerOutside.setSurfaceHold(null);
                }

                mRealPlayShOutside = null;
            }
        });
        mRealPlayShInside.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mEZPlayerInside != null) {
                    mEZPlayerInside.setSurfaceHold(surfaceHolder);
                } else {
                }

                mRealPlayShInside = surfaceHolder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mEZPlayerInside != null) {
                    mEZPlayerInside.setSurfaceHold(null);
                }

                mRealPlayShInside = null;
            }
        });
        mEZPlayerOutside.setSurfaceHold(mRealPlayShOutside);
        mEZPlayerInside.setSurfaceHold(mRealPlayShInside);
        mEZPlayerOutside.setPlayVerifyCode("LFYFBQ");
        mEZPlayerInside.setPlayVerifyCode("MJWXYN");
        mEZPlayerOutside.startRealPlay();
        mEZPlayerInside.startRealPlay();
    }

    // BD09=>GCJ-02 百度坐标系=>火星坐标系
    public static double[] bd092GCJ(double glat, double glon) {
        double x = glon - 0.0065;
        double y = glat - 0.006;
        double[] latlon = new double[2];
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        latlon[0] = z * Math.sin(theta);
        latlon[1] = z * Math.cos(theta);
        return latlon;
    }

    // GCJ02=>WGS84 火星坐标系=>地球坐标系(粗略)
    public static double[] gcj2WGS(double glat, double glon) {
        double[] latlon = new double[2];
        if (outOfChina(glat, glon)) {
            latlon[0] = glat;
            latlon[1] = glon;
            return latlon;
        }
        double[] deltaD = delta(glat, glon);
        latlon[0] = glat - deltaD[0];
        latlon[1] = glon - deltaD[1];
        return latlon;
    }


    // GCJ02=>WGS84 火星坐标系=>地球坐标系（精确）
    public static double[] gcj2WGSExactly(double gcjLat, double gcjLon) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            double[] tmp = wgs2GCJ(wgsLat, wgsLon);
            dLat = tmp[0] - gcjLat;
            dLon = tmp[1] - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;


            if (dLat > 0)
                pLat = wgsLat;
            else
                mLat = wgsLat;
            if (dLon > 0)
                pLon = wgsLon;
            else
                mLon = wgsLon;


            if (++i > 10000)
                break;
        }
        double[] latlon = new double[2];
        latlon[0] = wgsLat;
        latlon[1] = wgsLon;
        return latlon;
    }
    // 两点距离
    public static double distance(double latA, double logA, double latB,
                                  double logB) {
        int earthR = 6371000;
        double x = Math.cos(latA * Math.PI / 180)
                * Math.cos(latB * Math.PI / 180)
                * Math.cos((logA - logB) * Math.PI / 180);
        double y = Math.sin(latA * Math.PI / 180)
                * Math.sin(latB * Math.PI / 180);
        double s = x + y;
        if (s > 1)
            s = 1;
        if (s < -1)
            s = -1;
        double alpha = Math.acos(s);
        double distance = alpha * earthR;
        return distance;
    }


    public static double[] delta(double wgLat, double wgLon) {
        double[] latlng = new double[2];
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - OFFSET * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0)
                / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
        latlng[0] = dLat;
        latlng[1] = dLon;
        return latlng;
    }


    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }


    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }


    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0
                * PI)) * 2.0 / 3.0;
        return ret;
    }

    // WGS84=》GCJ02 地球坐标系=>火星坐标系
    public static double[] wgs2GCJ(double wgLat, double wgLon) {
        double[] latlon = new double[2];
        if (outOfChina(wgLat, wgLon)) {
            latlon[0] = wgLat;
            latlon[1] = wgLon;
            return latlon;
        }
        double[] deltaD = delta(wgLat, wgLon);
        latlon[0] = wgLat + deltaD[0];
        latlon[1] = wgLon + deltaD[1];
        return latlon;
    }

    // GCJ-02=>BD09 火星坐标系=>百度坐标系
    public static double[] gcj2BD09(double glat, double glon) {
        double x = glon;
        double y = glat;
        double[] latlon = new double[2];
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        latlon[0] = z * Math.sin(theta) + 0.006;
        latlon[1] = z * Math.cos(theta) + 0.0065;
        return latlon;}

    // WGS84=》BD09 地球坐标系=>百度坐标系
    public static double[] wgs2BD09(double wgLat, double wgLon) {
        double[] latlon = wgs2GCJ(wgLat, wgLon);
        return gcj2BD09(latlon[0], latlon[1]);
    }
    // BD09=>WGS84 百度坐标系=>地球坐标系
    public static double[] bd092WGS(double glat, double glon) {
        //Log.d("WWW==纬度:"+glat);
        //Log.d("WWW==经度:"+glon);
        double[] latlon = bd092GCJ(glat, glon);
        return gcj2WGSExactly(latlon[0], latlon[1]);
    }
    public static double[] MillierConvertion(double lat, double lon)
    {
        double L = 6381372 * Math.PI * 2;//地球周长
        double W=L;// 平面展开后，x轴等于周长
        double H=L/2;// y轴约等于周长一半
        double mill=2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间
        double x = lon * Math.PI / 180;// 将经度从度数转换为弧度
        double y = lat * Math.PI / 180;// 将纬度从度数转换为弧度
        y=1.25 * Math.log( Math.tan( 0.25 * Math.PI + 0.4 * y ) );// 米勒投影的转换
        // 弧度转为实际距离
        x = ( W / 2 ) + ( W / (2 * Math.PI) ) * x;
        y = ( H / 2 ) - ( H / ( 2 * mill ) ) * y;
        double[] result=new double[2];
        result[0]=x;
        result[1]=y;
        return result;
    }
    /**
     2     *在每个点的真实步骤中设置小车转动的角度
     3     *@param{BMap.Point} curPos 起点
     4      *@param{BMap.Point} targetPos 终点
     5     */
       private int function (double[] a, double[] e)
        {

                   double deg = 0;

                   if (e[0] != a[0]) {
                           double tan = (e[1] - a[1]) / (e[0] - a[0]);
                           Double atan = Math.atan(tan);
                           deg = atan * 360 / (2 * Math.PI);
                           if (e[0] < a[0]) {
                                   deg = -deg + 90 + 90;
                               } else {
                                   deg = -deg;
                               }
                           return (int) deg;

                       } else {
                           int disy = (int)(e[1] - a[1]);
                           int bias = 0;
                           if (disy > 0)
                                   bias = -1;
                           else
                               bias = 1;
                           return (-bias * 90);
                       }

               }
}


