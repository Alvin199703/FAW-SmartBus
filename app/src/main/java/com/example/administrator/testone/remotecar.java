package com.example.administrator.testone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.util.ConvertUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;

/**
 * CREATED BY ZW 2019/3/2
 * 实现 遥控界面布局
 * udp发送数据
 * 遥控杆、摄像头调用、百度地图调用
 */
public class remotecar extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    public LocationClient mLocationClient;                                                         //百度地图相关的定位客户端实例
    private MapView mapView;                                                                       //百度地图视图显示
    private boolean isFirstlocate=true;
    private BaiduMap baiduMap;
    Button iv_forward;//遥控驾驶前进按钮
    Button iv_left;//遥控驾驶左传按钮
    Button iv;//遥控驾驶OK按钮
    Button iv_right;//遥控驾驶右转按钮
    Button ll_go_back;//遥控驾驶后退按钮
    Button iv_accelerator;//遥控驾驶加速按钮
    Button ll_brake;//遥控驾驶刹车按钮
    Spinner number;  //人数
    Thread t;
    boolean panduan;
    private WheelView mWvGear;//用于选择档位的滑动控件
    Button goback;//从遥控驾驶返回
    MiusThread miusThread;
     boolean longclick;
    private CheckBox mCbUrgent, mCbLocking;
    int x=0;
    Button fangdain;
    private EZPlayer mEZPlayerOutside;                                                               //设置播放摄像头
    private SurfaceView mRealPlaySvOutside = null;
    private SurfaceHolder mRealPlayShOutside;
    public OkHttpClient client = new OkHttpClient();
    private byte mEmergencyBrakingStatus = 0, mLockingStatus = 0;
    String mAppkey;
    private String mAccesstoken;
    private Button inofcar,outofcar;
    private LinearLayout ditu,shexiangtou;
    private TextView mLogLeft;
    private TextView mLogRight;
    private static double ang;
    private static double velocity;
    private static double preang;
    private static InetAddress mAddress;
    private static DatagramSocket socket = null;
    private static String ip = "39.106.26.98"; //发送给整个局域网
    //private static String ip = "192.168.1.151"; //发送给整个局域网
    private static final int SendPort = 6666;  //发送方和接收方需要端口一致
    boolean kaishi,Panduan;
    private final String[] mStrings = {"P", "R", "N", "D"};//wheel选择的内容
    private int mWheelIndex = 3;//wheel选择的项
    private static String carid = null;
    private static double PI = Math.PI;
    private static double AXIS = 6378245.0; //
    private static double OFFSET = 0.00669342162296594323; // (a^2 - b^2) / a^2
    private static double X_PI = PI * 3000.0 / 180.0;
    private static double[] latlon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remotecar);
        kaishi=false;
        ang=0;
        Panduan=true;
        preang=0;
        velocity=0;
        mLogLeft=findViewById(R.id.velocity);
        t=new Thread(new Runnable() {
            private byte[] sendBuf;

            @Override
            public void run() {

                while(Panduan) {
                    if (kaishi) {
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
                        try {
                            String angle1="A"+String.valueOf(ang);
                            sendBuf = angle1.getBytes("utf-8");
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            socket.send(recvPacket1);
                            socket.close();
                            Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                            //sleep(1000);//使程序休眠五秒
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    try{
                        sleep(200);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
          t.start();
        mAccesstoken=MainActivity.mAccesstoken;
        inofcar=findViewById(R.id.inofcar);
        outofcar=findViewById(R.id.outofcar);
        ditu=findViewById(R.id.ditu);
        shexiangtou=findViewById(R.id.xiangji);
        EZOpenSDK.getInstance().setAccessToken(mAccesstoken);
        this.preparePlay();
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());

        mapView=findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        mLogRight = (TextView) findViewById(R.id.log_right);
        baiduMap.setMyLocationEnabled(true);
        mCbUrgent = findViewById(R.id.cb_urgent);
        mCbLocking = findViewById(R.id.cb_locking);
        mCbUrgent.setOnCheckedChangeListener(this);
        mCbLocking.setOnCheckedChangeListener(this);
        outofcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ditu.setVisibility(View.GONE);
                shexiangtou.setVisibility(View.VISIBLE);
                preparePlay();
            }
        });
        inofcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ditu.setVisibility(View.VISIBLE);
                shexiangtou.setVisibility(View.GONE);
                LinearLayout.LayoutParams lp;
               //lp=shexiangtou.generateLayoutParams(0);

                mEZPlayerOutside.stopRealPlay();
                if(mEZPlayerOutside!=null){
                    mEZPlayerOutside.release();}
            }
        });
        //设置定位的属性，包括定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色，精度圈边框颜色
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, BitmapDescriptorFactory.fromResource(R.drawable.car2));
        baiduMap.setMyLocationConfiguration(config);
        /*
         * 申请百度地图使用相关权限
         * */
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(remotecar.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(remotecar.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(remotecar.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(remotecar.this, permissions, 1);
        } else {
            requestLocation();
        }
        iv_forward=findViewById(R.id.iv_forward);
        iv_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendControl(5);
                Toast.makeText(remotecar.this, "请求保持前进", Toast.LENGTH_SHORT).show();
            }
        });
        iv_left=findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ang=ang+15;
                try {
                   // Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
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
                            try {
                                String angle1="A"+String.valueOf(ang);
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
                mLogRight.setText("转动角度 : " + ang);
            }
        });
//        iv_left.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                longclick=true;
//
//                  Thread t=  new Thread() {
//                        @Override
//                       public void run() {
//                            super.run();
//                            while (true) {
//                                try {
//                                    Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
//                                    sendControl(3);
//                                    sleep(1800);//使程序休眠五秒
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    };
//                  t.start();
//               // sleep(1800);//使程序休眠五秒
//                return true;
//            }
//        });

        iv_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    longclick=true;
                    x=1;
                    sendControl(3);
                    Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    longclick=false;
                    x=0;

                }

                    return false;
            }
        });
        iv_right=findViewById(R.id.iv_right);
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(remotecar.this, "请求右转", Toast.LENGTH_SHORT).show();
               // sendControl(4);
                ang=ang-15;
                try {
                    // Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
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
                            try {
                                String angle1="A"+String.valueOf(ang);
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
                mLogRight.setText("转动角度 : " + ang);

            }
        });
        iv_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    longclick=true;
                    x=1;
                    sendControl(4);
                    Toast.makeText(remotecar.this, "请求向右打方向盘", Toast.LENGTH_SHORT).show();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    longclick=false;
                    x=0;

                }

                return false;
            }
        });
        ll_go_back=findViewById(R.id.iv_back);
        ll_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(remotecar.this, "请求减速", Toast.LENGTH_SHORT).show();
                sendControl(6);
            }
        });
        iv=findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(remotecar.this, "方向盘回正", Toast.LENGTH_SHORT).show();
               // sendControl(7);
                ang=0;
                try {
                    // Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
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
                            try {
                                String angle1="A"+String.valueOf(ang);
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
                mLogRight.setText("转动角度 : " + ang);
            }
        });
        iv_accelerator=findViewById(R.id.iv_accelerator);
        iv_accelerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                velocity=velocity+5;
                //Toast.makeText(remotecar.this, "请求加油", Toast.LENGTH_SHORT).show();
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
                        try {
                            String angle1="V+5";
                            sendBuf = angle1.getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                        try {
                            socket.send(recvPacket1);
                            socket.close();
                            Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                mLogLeft.setText("速度:"+velocity);
                sendControl(8);
            }
        });
        iv_accelerator.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    longclick=true;
                    x=1;
                    sendControl(8);
                   // Toast.makeText(remotecar.this, "请求加速", Toast.LENGTH_SHORT).show();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    longclick=false;
                    x=0;

                }

                return false;
            }
        });
        ll_brake=findViewById(R.id.iv_brake);
        ll_brake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(remotecar.this, "请求刹车", Toast.LENGTH_SHORT).show();
                if(velocity>=5) {
                    velocity = velocity - 5;
                }
                else{
                    velocity=0;
                }
                //Toast.makeText(remotecar.this, "请求加油", Toast.LENGTH_SHORT).show();
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
                        try {
                            String angle1="V-5";
                            sendBuf = angle1.getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                        try {
                            socket.send(recvPacket1);
                            socket.close();
                            Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + velocity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                mLogLeft.setText("速度:"+velocity);
                sendControl(9);
            }
        });
        ll_brake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    longclick=true;
                    x=1;
                    sendControl(9);
                   // Toast.makeText(remotecar.this, "请求刹车", Toast.LENGTH_SHORT).show();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    longclick=false;
                    x=0;

                }

                return false;
            }
        });
        RockerView rockerViewRight = (RockerView) findViewById(R.id.rockerView_right);
        if (rockerViewRight != null) {
            rockerViewRight.setOnAngleChangeListener(new RockerView.OnAngleChangeListener() {
                @Override
                public void onStart() {
                    mLogRight.setText(null);
                    kaishi=true;
                }

                @Override
                public void angle(double angle) {
                 if(angle>=90&&angle<=270){
                     if(angle==270){
                         ang=0;
                     }
                     else {
                         ang = (angle - 270) * (-3);
                     }
                 }
                    if(90>angle&&angle>=0){
                        ang=(angle+90)*(-3);
                    }
                    if(360>angle&&angle>270){
                        ang=(angle-270)*(-3);
                    }
                    mLogRight.setText("转动角度 : " + ang);

                }

                @Override
                public void onFinish() {

                   kaishi=false;
                   ang=0;
                    mLogRight.setText("转动角度 : " + ang);
                    //创建线程发送信息
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
                            try {
                                String angle1="A"+String.valueOf(ang);
                                sendBuf = angle1.getBytes("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                            try {
                                socket.send(recvPacket1);
                                socket.close();
                                Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });
        }
        mWvGear = findViewById(R.id.wv_gear);
        initWheelView();
    }
    private void initWheelView() {

        mWvGear.setItems(mStrings, 3);
        mWvGear.setTextColor(0xFF888fbb);
        mWvGear.setTextSize(38);
        //mWvGear.setEnabled(false);
        mWvGear.setCycleDisable(true);
        mWvGear.setVisibleItemCount(3);
        WheelView.DividerConfig config = new WheelView.DividerConfig();
        config.setRatio(1.0f / 10.0f);//线比率
        config.setColor(0x000);//线颜色
        config.setAlpha(50);//线透明度
        config.setThick(ConvertUtils.toPx(this, 1));//线粗
        mWvGear.setDividerConfig(config);
        mWvGear.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(final int index) {
                mWheelIndex = index;
                //Toast.makeText(getBaseContext(),String.format(Locale.PRC, "index=%d,item=%s", index, mStrings[index]),Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), String.format(Locale.PRC, "切换为%s档", mStrings[index]), Toast.LENGTH_SHORT).show();
              // sendByokHttp(5);
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
                        try {

                            sendBuf = mStrings[index].getBytes("utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket recvPacket1 = new DatagramPacket(sendBuf, sendBuf.length, mAddress, SendPort);
                        try {
                            socket.send(recvPacket1);
                            socket.close();
                            Log.e("zziafyc", "已将内容发送给了AIUI端内容为：" + ang);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    private void getCarLocation(){
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
                    carid=elements.get(1).select("td").get(5).text();
                    //doorState=elements.get(1).select("td").get(7).text();

                }catch (Exception e){
                    e.printStackTrace();
                }
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
                                    latlon = wgs2BD09(lat,  lon); //地球坐标系转百度坐标
                                    Log.e("111111111111111111111", lon+"wwwwwwwwwwwwwwwwwwwwwwwwwww" );
                                    //LatLng pre=new LatLng(latlon[0],latlon[1]);
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
    private  void navigatetTo(BDLocation location){
        getCarLocation();
        if(isFirstlocate){
            //LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());       //地图上的车标按照手机定位显示
            LatLng ll=new LatLng(latlon[0],latlon[1]);          //地图上的车标按照车身GPS显示

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstlocate=false;
        }

        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
//        locationBuilder.latitude(location.getLatitude());
//        locationBuilder.longitude(location.getLongitude());
        locationBuilder.latitude(latlon[0]);
        locationBuilder.longitude(latlon[1]);
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
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
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
       Panduan=false;
        if (mEZPlayerOutside != null) {
            mEZPlayerOutside.release();
        }
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        //配置定位SDK参数
        option.setCoorType("bd09ll");   //设置返回经纬度坐标类型，默认gcj02
        option.setScanSpan(5000);        //设置发起定位请求的间隔，int类型，单位ms
        option.setOpenGps(true);          //设置是否使用gps，默认false,使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setIsNeedAddress(true);     //是否需要地址信息，默认为不需要，即参数为false
        option.setLocationNotify(true);

        mLocationClient.setLocOption(option);
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
    public class MyLocationListener implements BDLocationListener {
        @Override

        public void onReceiveLocation(final BDLocation location) {
        if(location.getLocType()==BDLocation.TypeGpsLocation||location.getLocType()==BDLocation.TypeNetWorkLocation){
            navigatetTo(location);
        }

        }

    }
    public void sendControl(int flag){
        switch (flag) {
            case 1:
                try {
                    String url = "http://39.106.26.98:8080/show4/?yk_kskz=1";
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
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
                        String url = "http://39.106.26.98:8080/show4/?yk_jdl=1";
                        Request request = new Request.Builder().url(url)
                                .build();
                        while(longclick) {
                            try {
                                sleep(3000);

                                Response response = client.newCall(request).execute();

                               // String responseData = response.body().string();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        }

                }).start();

                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://39.106.26.98:8080/show4/?yk_jdr=1";
                        Request request = new Request.Builder().url(url)
                                .build();
                        while(longclick) {
                            try {
                                sleep(3000);
                                Response response = client.newCall(request).execute();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://39.106.26.98:8080/show4/?yk_yma=1";
                        Request request = new Request.Builder().url(url)
                                .build();
                        while(longclick) {
                            try {
                                sleep(3000);

                                Response response = client.newCall(request).execute();

                               // String responseData = response.body().string();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                break;
            case 9:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(longclick) {
                            try {
                                sleep(3000);
                                String url = "http://39.106.26.98:8080/show4/?yk_ymd=1";
                                Request request = new Request.Builder().url(url)
                                        .build();
                                Response response = client.newCall(request).execute();

                                String responseData = response.body().string();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{

                            String url = "http://39.106.26.98:8080/show4/?yk_cmsd=1";
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

        }
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
                try {
                    String url = "http://39.106.26.98:8080/show4/?yk_cmsd=1";
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                mLockingStatus = 0;
                Toast.makeText(getBaseContext(), "车门锁定", Toast.LENGTH_SHORT).show();
            } else {
                mLockingStatus = 1;
            }
            sendControl(11);
        }
    }
    public void senTrouhcontrol(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://39.106.26.98:8080/show4/?yk_jdl=1";
                Request request = new Request.Builder().url(url)
                        .build();
                while(longclick) {
                    try {
                        sleep(3000);
                        Response response = client.newCall(request).execute();

                        //String responseData = response.body().string();
                        Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    class MiusThread extends Thread {
        @Override
        public void run() {
            while (x==1) {
                try {
                    Toast.makeText(remotecar.this, "请求左转", Toast.LENGTH_SHORT).show();
                    String url = "http://39.106.26.98:8080/show4/?yk_jdl=1";
                    Request request = new Request.Builder().url(url)
                            .build();
                    Response response = client.newCall(request).execute();

                    String responseData=response.body().string();
                    //Thread.sleep(200);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    private void preparePlay() {


        mEZPlayerOutside = EZOpenSDK.getInstance().createPlayer("138437458", 1);

        mRealPlaySvOutside = findViewById(R.id.realplay_sv_outside);

        mRealPlayShOutside = mRealPlaySvOutside.getHolder();

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

        mEZPlayerOutside.setSurfaceHold(mRealPlayShOutside);

        mEZPlayerOutside.setPlayVerifyCode("VMACHO");


        mEZPlayerOutside.startRealPlay();

    }


}
