package com.hengshitong.shualianzhifs;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengshitong.shualianzhifs.utils.QrCodeUtil;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.hengshitong.shualianzhifs.R.id.tv_sn;
import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    String rawdata;
    private ImageView tv_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_sn = (ImageView) findViewById(R.id.tv_sn);
        String content="http://test.aiyouzhan.cn/member/operatorCode?code=18703691242";
        Bitmap bitmap = QrCodeUtil.createQRCodeWithLogo(content, 500,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        tv_sn.setImageBitmap(bitmap);
    }

}






