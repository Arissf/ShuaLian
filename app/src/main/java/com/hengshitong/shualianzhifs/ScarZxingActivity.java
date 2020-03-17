package com.hengshitong.shualianzhifs;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScarZxingActivity extends AppCompatActivity {


    private QRCodeView mQRCodeView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scar_zxing);

        Cares();


    }


    private void Cares() {


        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.changeToScanQRCodeStyle(); //扫二维码
        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.startSpot();

        mQRCodeView.setDelegate(new QRCodeView.Delegate() {

            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.e("kkd", "result:" + result);

                //Toast.makeText(activity, result, Toast.LENGTH_LONG).show();


//
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("Time", "3e");
//                bundle.putString("code", result);
//                intent.putExtras(bundle);
//                setResult(2, intent);//返回值调用函数，其中2为resultCode，返回值的标志
//


                Intent intent =getIntent();
                //这里使用bundle绷带来传输数据
                Bundle bundle =new Bundle();
                //传输的内容仍然是键值对的形式
                bundle.putString("second",result);//回发的消息,hello world from secondActivity!
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();//传值结束

                //获取结果后三秒后，重新开始扫描
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mQRCodeView.startSpot();
                    }
                }, 3000);
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Toast.makeText(ScarZxingActivity.this, "打开相机错误！", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mQRCodeView !=null){
            mQRCodeView.stopSpot();
        }

    }


}
