package com.hengshitong.shualianzhifs;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.util.Map;

public class CustomApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        WxPayFace.getInstance().initWxpayface(this, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    new RuntimeException("调用返回为空").printStackTrace();
                    return ;
                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                Log.e("car", "response info :: " + code + " | " + msg);
                if (code == null || !code.equals("SUCCESS")) {
                    new RuntimeException("调用返回非成功信息: " + msg).printStackTrace();
                    return ;
                }
                Log.e("返回成功", "初始化成功");
            }
        });



    }


}
