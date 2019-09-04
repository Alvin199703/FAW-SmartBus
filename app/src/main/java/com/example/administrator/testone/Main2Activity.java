package com.example.administrator.testone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
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
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.administrator.testone.overlayutil.PoiOverlay;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import java.util.ArrayList;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
public class Main2Activity extends AppCompatActivity {

//    public LocationClient mLocationClient;                                                         //百度地图相关的定位客户端实例
//    private MapView mapView;                                                                       //百度地图视图显示
//    private BaiduMap baiduMap;                                                                     //地图的总控制器
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mLocationClient=new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(new MyLocationListener());
//        SDKInitializer.initialize(getApplicationContext());
//        mapView=findViewById(R.id.bmapView);
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//@Override
//protected void onDestroy(){
//        super.onDestroy();
//        mLocationClient.stop();
//        mapView.onDestroy();
//}
//    public class MyLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//
//
//
////
////            //创建InfoWindow展示的view
////            Button button = new Button(getApplicationContext());
////            button.setBackgroundResource(R.drawable.popup);
////        double distance = DistanceUtil.getDistance(p1, p2);
////        String sDistance = String.valueOf(distance);
////        button.setText(sDistance);
////            button.setText("正门");
////            button.setTextColor(0xAAFF0000);
//            //定义用于显示该InfoWindow的坐标点
//            LatLng pt1 = new LatLng(43.789983, 125.428745);
//            LatLng pt2 = new LatLng(43.796913, 125.429392);
//            LatLng pt3 = new LatLng(43.800719, 125.436075);
//            LatLng pt4 = new LatLng(43.799321, 125.440171);
//            LatLng pt5 = new LatLng(43.796139, 125.438132);
//            LatLng pt6 = new LatLng(43.788141, 125.437872);
//
//            //LatLng pt7 = new LatLng(43.789983, 125.428745);
//           /* LatLng pt1 = new LatLng(43.838889, 125.170671);
//            LatLng pt2 = new LatLng(43.839465, 125.173954);
//            LatLng pt3 = new LatLng(43.841854, 125.17523);
//            LatLng pt4 = new LatLng(43.843577, 125.17134);
//            LatLng pt5 = new LatLng(43.841549, 125.165879);*/
//            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
////            InfoWindow mInfoWindow = new InfoWindow(button, pt1, -47);
//            //显示InfoWindow
////            baiduMap.showInfoWindow(mInfoWindow);
//            LatLng car1=new LatLng(43.79319,125.42917);
//            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.car1);
//
//
//
//
//
//
//        }
//    }
    Button canel;//取消按钮
    Button fangdaout;
    Button fangdain;
    private EZPlayer mEZPlayerOutside;                                                               //设置播放摄像头
    private SurfaceView mRealPlaySvOutside = null;
    private SurfaceHolder mRealPlayShOutside;
    private EZPlayer mEZPlayerInside;
    private SurfaceView mRealPlaySvInside = null;
    private SurfaceHolder mRealPlayShInside;
    String mAppkey;
    private String mAccesstoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAppkey="d10d5af273b1418cacfa5e7c3a475509";
        mAccesstoken=MainActivity.mAccesstoken;

        EZOpenSDK.getInstance().setAccessToken(mAccesstoken);
        this.preparePlay();
        fangdaout=findViewById(R.id.fangdaout_car1);
        fangdain=findViewById(R.id.fangdain_car1);
        fangdaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameSwitch4.setVisibility(View.GONE);
                mRealPlaySvInside.setVisibility(View.GONE);
                mRealPlaySvOutside.setVisibility(View.VISIBLE);


            }
        });
        fangdain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // frameSwitch4.setVisibility(View.GONE);
                mRealPlaySvInside.setVisibility(View.VISIBLE);
                mRealPlaySvOutside.setVisibility(View.GONE);
            }
        });
        canel=findViewById(R.id.fanhui_car1);
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEZPlayerOutside.stopRealPlay();
                mEZPlayerInside.stopRealPlay();
                if (mEZPlayerOutside != null) {
                    mEZPlayerOutside.release();
                }
                if (mEZPlayerInside != null) {
                    mEZPlayerInside.release();
                }
                Intent it=new Intent(Main2Activity.this,MainActivity.class);
                finish();
                //startActivity(it);

            }
        });
    }

    /*
     * 活动销毁时停止定位,防止程序一直在后台进行定位,消耗电量
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mEZPlayerOutside != null) {
            mEZPlayerOutside.release();
        }
        if (mEZPlayerInside != null) {
            mEZPlayerInside.release();
        }
    }
    private void preparePlay() {


        mEZPlayerOutside = EZOpenSDK.getInstance().createPlayer("C76338328", 1);
        mEZPlayerInside = EZOpenSDK.getInstance().createPlayer("C76338280", 1);
        mRealPlaySvOutside = findViewById(R.id.realplay_sv_outside_car1);
        mRealPlaySvInside = findViewById(R.id.realplay_sv_inside_car1);
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
}
