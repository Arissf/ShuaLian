package com.hengshitong.shualianzhifs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.Request;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;

public class QiDongActivity extends AppCompatActivity implements HttpResponse,HttpError {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);








        Dbanding(getDeviceSN());





    }

    public static String getDeviceSN(){

        String serialNumber = android.os.Build.SERIAL;

        return serialNumber;
    }

    private void Dbanding(String shebid){


        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.SHEBEIBANGDING+"?equipmentSn=" +shebid ;
        Toast.makeText(this,urls,Toast.LENGTH_LONG).show();
        Request.getIncetanc().requestsTwo(this, urls,"GET","1",formBody,this);

    }


    @Override
    public void getHttpError(String txnCode, String error) {

    }

    private final int NEED_CAMERA = 200;
    int code=1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void response(String txnCode, Object dataEntity) {

        if(txnCode.equals("1")){
            try {

                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");
                String data = json.getString("data");



                if(data.equals("true")){
                   //XuanOilActivity
                   Intent intent = new Intent(QiDongActivity.this,XuanOilActivity.class);
                   startActivity(intent);
                   finish();

                }else  if(data.equals("false")){
                    //BangDingActivity
                    Intent intent = new Intent(QiDongActivity.this,BangDingActivity.class);
                    startActivity(intent);
                    finish();


                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
