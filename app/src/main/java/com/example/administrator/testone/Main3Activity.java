package com.example.administrator.testone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

/**
 * CREATED BY ZW 2019/1/20
 * 摄像头调用
 */
public class Main3Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main3);
        mAppkey="d10d5af273b1418cacfa5e7c3a475509";
        mAccesstoken=MainActivity.mAccesstoken;

        EZOpenSDK.getInstance().setAccessToken(mAccesstoken);
        this.preparePlay();
        fangdaout=findViewById(R.id.fangdaout);
        fangdain=findViewById(R.id.fangdain);
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
        canel=findViewById(R.id.fanhui1);
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
                Intent it=new Intent(Main3Activity.this,MainActivity.class);
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

}
