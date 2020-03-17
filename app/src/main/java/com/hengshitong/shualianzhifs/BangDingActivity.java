package com.hengshitong.shualianzhifs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.Request;

import org.json.JSONObject;

import okhttp3.FormBody;

public class BangDingActivity extends AppCompatActivity implements HttpResponse,HttpError {


    private EditText actions;
    private EditText nameet;
    private EditText passwet;
    private Button bt_logins;
    private LoadingDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bang_ding);

        actions = (EditText) findViewById(R.id.et_yzphone);
        nameet = (EditText) findViewById(R.id.et_yzaccount);
        passwet = (EditText) findViewById(R.id.et_yzpassion);
        bt_logins= (Button) findViewById(R.id.bt_logins);




        bt_logins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actions.getText().toString().equals("")){

                    Toast.makeText(BangDingActivity.this,"请输入油站编码",Toast.LENGTH_LONG).show();

                    return;
                }
                if(nameet.getText().toString().equals("")){

                    Toast.makeText(BangDingActivity.this,"请输入账号",Toast.LENGTH_LONG).show();

                    return;
                }
                if(actions.getText().toString().equals("")){

                    Toast.makeText(BangDingActivity.this,"请输入密码",Toast.LENGTH_LONG).show();

                    return;
                }


                Youqiang();
            }
        });


    }

    public static String getDeviceSN(){
        String serialNumber = android.os.Build.SERIAL;
        return serialNumber;
    }


    //绑定油站
    private void Youqiang() {
        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new Request().getHashMap(this);
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.BANGIDNGYOUZHAN+"?equipmentSn=" + getDeviceSN()+"&username=" + nameet.getText().toString().trim()
                +"&password="+passwet.getText().toString().trim()  +"&stationCode="+actions.getText().toString().trim();
        Request.getIncetanc().requestsPost(this, urls,"1",formBody,this);
    }

    @Override
    public void getHttpError(String txnCode, String error) {

    }

    @Override
    public void response(String txnCode, Object dataEntity) {
        dialog.dismiss();
        if(txnCode.equals("1")){
            try {

                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");
                if(code == 1){
                    Intent intent = new Intent(BangDingActivity.this,XuanOilActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(BangDingActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
