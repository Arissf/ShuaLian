package com.hengshitong.shualianzhifs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;


/**
 * Created by lvxingxing on 2018/6/8.
 *
 * @author 辉哥
 */
   public class Request implements HttpResponse {
      private Activity activity;
      private static Request mHttpUtils;
      public boolean isShow = true;
      private Gson gson;
     private GsonBuilder builder;
     private  HttpResponse responses;
     private String code;
     private String code1;
     private Context context;
    private static SharedPreferencesHelper sharedpa;
      public static Request getIncetanc() {
        if (mHttpUtils == null) {
            mHttpUtils = new Request();
        }
        return mHttpUtils;
       }



    private HttpError httpError;
    public void setHttpError(HttpError httpError) {
        this.httpError = httpError;
    }

    @Override
    public void response(String txnCode, Object dataEntity) {

    }

    private Handler handleratwo2 =  new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            msg.what= Integer.parseInt(code);
            responses.response(code+"",message);
             Log.e("12",code+"");
            dialog1.dismiss();
        }
    };

     LoadingDialog dialog1;
    public void requests(final Activity activity, final String urls, final String post, final String code, final FormBody.Builder formBody1, final HttpResponse responses){
        this.activity = (Activity) activity;
        this.responses=responses;
        this.code=code;
         dialog1 = new LoadingDialog(activity);
        if (isShow) {
            dialog1.show("正加载数据...",true,true);
        } else {
            isShow = true;
        }

       Log.e("保护区=前",urls);
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                //创建Request 对象。
                .url(urls)
                .post(formBody1.build())
                .method(post, null).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                httpError.getHttpError(code,e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String zhenjue= response.body().string();
                    //把之存进去
                    android.os.Message msg = new android.os.Message();
                    msg.obj= zhenjue.toString();
                    handleratwo2.sendMessage(msg);
                           Log.e("返回成功",code+"="+zhenjue.toString());


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
     LoadingDialog dialog;

    public void requestsTwo(final Activity activity, final String urls, final String post, final String code1, final FormBody.Builder formBody1, final HttpResponse responses){
        this.activity = (Activity) activity;
        this.responses=responses;
        this.code1=code1;
        String Authorization ="";
        if(urls.contains("common/getImageCaptche")){
            Authorization ="112";
        }else {
            Authorization="Authorization";
        }
        sharedpa = new SharedPreferencesHelper(activity, "anhua");
        String token =  sharedpa.getSharedPreference("token", "").toString().trim();
         Log.e("保护区=前",urls);
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                //创建Request 对象。
                .addHeader(Authorization,"Bearer "+token)
                .addHeader("devtype","app_pos")
                .addHeader("appVersion",getVersionCode(activity))
                .url(urls)
                .post(formBody1.build())
                .method(post, null).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                httpError.getHttpError(code,e.toString());
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String zhenjue= response.body().string();
                    //把之存进去
                    android.os.Message msg = new android.os.Message();
                    msg.obj= zhenjue.toString();
                    msg.what = Integer.parseInt(code1);
                    handleratwo.sendMessage(msg);
                    Log.e("返回成功", msg.what+"="+zhenjue.toString());
                    Toast.makeText(activity,zhenjue.toString(),Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    private Handler handleratwo =  new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
        //   msg.what= Integer.parseInt(code1);
            responses.response( msg.what+"",message);


        }
    };



    private Handler handlera =  new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
           // dialog.dismiss();
            String message = msg.obj.toString();
          //  msg.what= Integer.parseInt(code);
            responses.response(msg.what+"",message);


        }
    };



    public void requestsPost(final Activity activity, final String urls, final String code, final FormBody.Builder formBody1, final HttpResponse responses){
        this.activity = (Activity) activity;
        this.responses=responses;
        this.code=code;
        sharedpa = new SharedPreferencesHelper(activity, "anhua");
        String token =  sharedpa.getSharedPreference("token", "").toString().trim();
        String Authorization ="";
        if(urls.contains("enterprise/login")){
            Authorization ="112";
        }else {
            Authorization="Authorization";
        }
//        dialog = new LoadingDialog(activity);
//        dialog.show("正加载数据...",true,true);
         Log.e("=前",urls+"/"+code);
        //创建OkHttpClient对象。
        OkHttpClient client = new OkHttpClient();
        //创建表单请求体
//       FormBody.Builder formBody = new FormBody.Builder();
//       //传递键值对参数
//       formBody.add("username","zhangsan");
        okhttp3.Request request = new okhttp3.Request.Builder()
                //创建Request 对象。
                .addHeader(Authorization,"Bearer "+token)
                .addHeader("devtype","app_pos")
                .addHeader("appVersion",getVersionCode(activity))
                .url(urls)
                .post(formBody1.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              //  dialog.dismiss();
                httpError.getHttpError(code,e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String zhenjue= response.body().string();
                    android.os.Message msg = new android.os.Message();
                    msg.obj= zhenjue.toString();
                    msg.what= Integer.parseInt(code);
                    handlera.sendMessage(msg);
                             Log.e("返回成功",code+"="+zhenjue.toString());
                    Toast.makeText(activity,zhenjue.toString(),Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    public void requestsPosthand(final Activity activity, final String urls, final String code, final FormBody.Builder formBody1, final HttpResponse responses){
        this.activity = (Activity) activity;
        this.responses=responses;
        this.code1=code;
        sharedpa = new SharedPreferencesHelper(activity, "anhua");
        String token =  sharedpa.getSharedPreference("token", "").toString().trim();
          Log.e("保护区=前",urls);
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(urls)
                .addHeader("Authorization","Bearer "+token)
                .addHeader("devtype","app_pos")
                .addHeader("appVersion",getVersionCode(activity))
                .post(formBody1.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpError.getHttpError(code1,e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                try {
                    String zhenjue= response.body().string();
                    android.os.Message msg = new android.os.Message();
                    msg.obj= zhenjue.toString();
                    msg.what= Integer.parseInt(code);
                    handlera1.sendMessage(msg);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    private Handler handlera1 =  new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            int codes = msg.what;
            responses.response(codes+"",message);
         Log.e("返回成功11121",codes+"="+message);
        }
    };

    public static  FormBody.Builder getHashMap(Context contxet) {
        sharedpa = new SharedPreferencesHelper(contxet, "anhua");
        String token =  sharedpa.getSharedPreference("token", "").toString().trim();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        return formBody;
    }


    public String getVersionCode(Context ctx) {
        // 获取packagemanager的实例
        String version =null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            //getPackageName()是你当前程序的包名
            PackageInfo packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }




}
