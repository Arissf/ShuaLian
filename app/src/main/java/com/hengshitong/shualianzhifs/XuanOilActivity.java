package com.hengshitong.shualianzhifs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chice.scangun.ScanGun;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hengshitong.shualianzhifs.adapter.XuanAdapter;
import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.entmy.Oil;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.QrCodeUtil;
import com.hengshitong.shualianzhifs.utils.Request;
import com.hengshitong.shualianzhifs.utils.ResourceUtils;
import com.hengshitong.shualianzhifs.view.AntGridView;
import com.hengshitong.shualianzhifs.view.ScreenUtils;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;
import com.tencent.wxpayface.WxfacePayCommonCode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import okhttp3.FormBody;

import static com.hengshitong.shualianzhifs.R.id.liners;

public class XuanOilActivity extends AppCompatActivity implements HttpResponse,HttpError {

    private AntGridView girdvie1;
    private AntGridView girdvie2;

    private KehuAdapter keadp;
    private XuanAdapter xuandp;
    private String[] lsi=new String[]{"90#","93#","97#","0#"};
    private String[] lsi2=new String[]{"油枪-1","油枪-2","油枪-3","油枪-4"};
    private EditText ed_jines;

    private LoadingDialog dialog;
    private Button queding;
    private ImageView images;
    private PopupWindow pwindow,popupWindow;
    String rawdata="";
    String authinfo="oe587reXamOUkmNFQPYK1hOI8hROMtfj6iiT/4GmvHhIZJveZvqZf98M5Rt2dKj1TXgJN9UOywky+xUCxZarKxtCReLiRgdSGIjyk4u9MrFxkUE6mpKe4jysYOAo2EHrr+UFZXwmM/Y0F8//2qSjZDNkZ/FXag3GoUGT+hKXcAJ8v+uGlNpjAVYttZIVO72m9Wnl+TmP0Hy9vdladWq8zJLoNyv7skFBsPZP+TpU/WssCry1JOTBp+kSERp5slfZ6WDxXiu5U7K+6nLaS/2jqS46fDq0pPyeGpD8q2cHwySZLSl8sd4gBXgkJOg/9+aTOWgNZfImpkSFhuWI/AsrylZI/20RztgsvfMVggqBbTOCkbBvVG93WLeEpJbr2trxRK+6n7cIFLGLOmvNIbkLHPpZPCxtSRJmZ7ePwtw6SfEdWyH3HUIVOER8QSIM2MKsa7lWtEFAH6G7w1r61wYN9O6/mfphdY8QWGP3s7eSkyUReDnKPiKKHRcgP260QkH0MXFnGBDZYB6vRfjisfaKFXja12lb+BuwGvI96T7tCc+tedh3+n4IA/TspwNvXwbleNELM+aRu+vt4mlzD9i3zoztC72tIfpkWFm4N/n+Hr0kDwDy9bDxBd0v6DWd" +
            "B2Hq7TEW/3P8qY";
    String   mchId="1497343632",appid="wx5995ccd99eea6930",subMchId="1576746841",storeId="20200312000001",subAppid="wxa3e4b5589e80d605";
    private Context context;
    private QRCodeView mQRCodeView=null;

    private List<Oil> lists;
    private LinearLayout liners;
    String sub_openid = "";
    private Oil youp1;
    private List<Oil> pianlidlist;
    private String openid = null;
    private ScanGun scanGun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_xuan_oil);


        girdvie1 =(AntGridView) findViewById(R.id.girdvie1);
        girdvie2 =(AntGridView) findViewById(R.id.girdvie2);

        images = (ImageView) findViewById(R.id.images);
        ed_jines= (EditText) findViewById(R.id.ed_jines);

        liners = (LinearLayout) findViewById(R.id.liners);
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_jines.setText("");
            }
        });
        openid = "owOoV5tkMEwPqKirC-djpfVLQTHY";
        queding = findViewById(R.id.queding);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent sdg = new Intent(XuanOilActivity.this,PaySuccessActivity.class);
//                startActivity(sdg);
             //   GetfaceUserInfo();

            Log.e("qwewe",ed_jines.getText().toString()+"87");

            popwindow();

            }
        });

    //   XuanGet();


      //  showChoiceServiceLine();
        //获取油枪
         Youping();

      //  mQRCodeView.startSpot();
         Log.e("Camera();",  FindFrontCamera()+"=");

         //获取usb扫描枪数据

        scanGun = new ScanGun(new ScanGun.ScanGunCallBack() {
            @Override
            public void onScanFinish(String s) {
                //这里的回调函数的参数s
                //就是扫码枪扫到的那一串字符串
                //TODO 按照你自己的逻辑处理
                Log.e("eess","=="+s);

            }
        });




    }
    String text ="xygas";
    String barcode ="";
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            Log.e("TAG","dispatchKeyEvent: "+event.toString());
            char pressedKey = (char) event.getUnicodeChar();
            barcode += pressedKey;
        }
        if (event.getAction()==KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

            String str1=barcode.substring(0, barcode.indexOf("?code="));
            String str2=barcode.substring(str1.length()+6, barcode.length());

            text=str2;
            Log.e("TAGWW",text+"=="+barcode);
        }
        return super.dispatchKeyEvent(event);
    }






    private int FindFrontCamera(){//判断是否有前置摄像头，得到返回值
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number
        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {
            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo
            if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT ) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
       Log.e("Camera","进来这里里面。。。。。。");
                return camIdx;
            }
        }
        return -1;
    }



    private void XuanGet(){

        View v = View.inflate(this, R.layout.jiesuan_dialog, null);
        TextView content1 = (TextView) v.findViewById(R.id.text);

        TextView confim = (TextView) v.findViewById(R.id.tv_qued);
        TextView qx = (TextView) v.findViewById(R.id.confim);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(v);

        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        confim.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {


                if (ContextCompat.checkSelfPermission(XuanOilActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(XuanOilActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    Log.e("qwee","11");
                } else {
                    Log.e("qwee1","32");
                    Intent intent1 = new Intent(XuanOilActivity.this,ScarZxingActivity.class);
                    startActivityForResult(intent1,0);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    intent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 调用前置摄像头
//
//                    startActivityForResult(intent, 1);
                }






            }
        });

        dialog.getWindow().setLayout(ResourceUtils.getScreenWidth(this)-260, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shap_bai_bai);
    }










    class KehuAdapter extends BaseAdapter {

        private int selectedPosition=8000;
        private Context mContext;
        private List<Oil> lists;
        private  JSONArray oupon;
        public KehuAdapter(List<Oil> lists,  JSONArray oupon, Context mContext) {
            this.lists = lists ;
            this.oupon=oupon;
            this.mContext = mContext;
        }

        /**
         * 返回item的个数
         * @return
         */
        @Override
        public int getCount() {
                return lists.size();
        }

        /**
         * 返回每一个item对象
         * @param i
         * @return
         */
        @Override
        public Object getItem(int i) {
            return lists.get(i);
        }


        public void clearSelection(int position) {

            selectedPosition = position;

        }


        /**
         * 返回每一个item的id
         * @param i
         * @return
         */
        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * 暂时不做优化处理，后面会专门整理BaseAdapter的优化
         * @param i
         * @param view
         * @param viewGroup
         * @return
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.youqiang_items,viewGroup,false);
            TextView tv_phone = (TextView) view.findViewById(R.id.duos);
            Log.e("sdfsd",lists.size()+"=");

//        此处需要返回view 不能是view中某一个

            try {

                    JSONObject value = oupon.getJSONObject(i);
                    //获取到title值
                    String title = value.getString("type");
                    // String title = value.optString("title");
                    JSONObject jjus = new JSONObject(title);
                    int id = jjus.getInt("id");
                    String valuse = jjus.getString("value");
                    tv_phone.setText(valuse);

            }catch (Exception e){
                e.printStackTrace();
            }




            if(selectedPosition==i){
                tv_phone.setTextColor(Color.parseColor("#FFFFFF"));
                tv_phone.setBackgroundResource(R.drawable.shap_lv10_lv);
            }else{
                tv_phone.setTextColor(Color.parseColor("#333333"));
                tv_phone.setBackgroundResource(R.drawable.shap_bai5_yinybai);      }


            return view;
        }
    }



    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (XuanOilActivity.this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (XuanOilActivity.this).getWindow().setAttributes(lp);
    }

    PopupWindow pop,popgz;
    private void popwindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
        pop = new PopupWindow(XuanOilActivity.this);
        View popView = LayoutInflater.from(XuanOilActivity.this).inflate(R.layout.popu_gongz, null, false);
        ImageView iv_cancel = popView.findViewById(R.id.imgse);
        ImageView serviceIND = popView.findViewById(R.id.imgse1);
        ImageView serviceSEA = popView.findViewById(R.id.img_shan);

        //默认
        serviceSEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1f);
                pop.dismiss();

            }
        });
        //中国
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                setBackgroundAlpha(1f);

                if(oilid == null){
                    Toast.makeText(XuanOilActivity.this,"请选择油品",Toast.LENGTH_LONG).show();
                    return;
                }

                if(gunids == null){
                    Toast.makeText(XuanOilActivity.this,"请选择油枪",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ed_jines.getText().toString().trim().equals("")){
                    Toast.makeText(XuanOilActivity.this,"金额不能为空",Toast.LENGTH_LONG).show();
                    return;
                }


                popwindogognz();

            }
        });
        //印度
        serviceIND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                setBackgroundAlpha(1f);





                if(oilid == null){
                    Toast.makeText(XuanOilActivity.this,"请选择油品",Toast.LENGTH_LONG).show();
                    return;
                }

                if(gunids == null){
                    Toast.makeText(XuanOilActivity.this,"请选择油枪",Toast.LENGTH_LONG).show();
                    return;
                }
                if(ed_jines.getText().toString().trim().equals("")){
                    Toast.makeText(XuanOilActivity.this,"金额不能为空",Toast.LENGTH_LONG).show();
                    return;
                }


                getWxpayfaceRawdata();


            }
        });



        pop.setContentView(popView);
        int width = ScreenUtils.getWidth(XuanOilActivity.this);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        setBackgroundAlpha(0.5f);
        pop.update();
        //居中显示，第一个参数的控件只要是这个popwindow里面的随便一个控件就行
        pop.showAtLocation(iv_cancel, Gravity.CENTER, 0, 0);
        //        //根据控件定点显示，后面的两个参数是偏移量
        //        // pop.showAsDropDown(iv_cancel, 0, 0);

    }


    ImageView im_sxing;
    private void popwindogognz() {
        if (popgz != null && popgz.isShowing()) {
            popgz.dismiss();
        }
        popgz = new PopupWindow(XuanOilActivity.this);
        View popView = LayoutInflater.from(XuanOilActivity.this).inflate(R.layout.lay_shaulian, null, false);
        LinearLayout iv_cancel = (LinearLayout) popView.findViewById(R.id.linqih);
        ImageView serviceSEA = (ImageView)popView.findViewById(R.id.img_shan);
         im_sxing =(ImageView) popView.findViewById(R.id.im_sxing);
         TextView  tv_money= popView.findViewById(R.id.tv_money);
        tv_money.setText("¥"+ed_jines.getText().toString().trim());
        //测试
        String url ="http://test.aiyouzhan.cn/";
        String content=url+"member/faceSwiping?code="+text+"&oilTypeId="+oilid+"&gunId="+gunids+"&amount="+ed_jines.getText().toString().trim();
        Bitmap bitmap = QrCodeUtil.createQRCodeWithLogo(content, 500,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        im_sxing.setImageBitmap(bitmap);


        //默认
        serviceSEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(1f);
                popgz.dismiss();

            }
        });
        //中国
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popgz.dismiss();
                setBackgroundAlpha(1f);


            }
        });




        popgz.setContentView(popView);
        int width = ScreenUtils.getWidth(XuanOilActivity.this);
        popgz.setWidth(width * 6 / 7);
        // pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popgz.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popgz.setBackgroundDrawable(new ColorDrawable());
        popgz.setFocusable(true);
        popgz.setOutsideTouchable(true);
        setBackgroundAlpha(0.5f);
        popgz.update();
        //居中显示，第一个参数的控件只要是这个popwindow里面的随便一个控件就行
        popgz.showAtLocation(iv_cancel, Gravity.CENTER, 0, 0);
        //        //根据控件定点显示，后面的两个参数是偏移量
        //        // pop.showAsDropDown(iv_cancel, 0, 0);

    }



    public static String getDeviceSN(){

        String serialNumber = android.os.Build.SERIAL;

        return serialNumber;
    }

    /**
     * 2. 人脸识别第二步 获取raw data
     */
    private void getWxpayfaceRawdata() {
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    Log.e("raw", "rawdata ==调用返回为空");
                    new RuntimeException("调用返回为空").printStackTrace();
                    return;
                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                rawdata = info.get("rawdata").toString();
                Log.e("返回", "1rawdata ==" + rawdata);
                if (code == null || rawdata == null || !code.equals("SUCCESS")) {
                    new RuntimeException("调用返回非成功信息,return_msg:" + msg + "   ").printStackTrace();
                    Log.e("raw", "11rawdata ==信息=");
                    return;
                }
                /**
                 在这里处理您自己的业务逻辑
                 可以紧接着执行第三步 获取调用凭证getAuthInfo，
                 这应该是向 商户server 发起请求。
                 */
                // EMULATOR29X2X11X0
                //getDeviceSN()



                Banjiese(getDeviceSN(),rawdata);
            }
        });
    }




        public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            Log.e("toURLEncoded error:",paramString);
            return "";
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
            Log.e("toURLEncoded error:"+paramString, String.valueOf(localException));
        }

        return "";
    }

    //扫描员工工牌 获取企业id
    private void Pycopye(String Code){
//        dialog = new LoadingDialog(this);
//        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.YUANGOGNGONGPAI+"?code=" +text;
        Log.e("code",urls+"");
        Request.getIncetanc().requestsTwo(this, urls,"GET","3",formBody,this);
        // dialog.dismiss();
    }
    //获取utoken
    private void PyuToken(String enterpriseId){
//        dialog = new LoadingDialog(this);
//        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);

        String urls =  UrlUtils.UTOKENDS+"?openId=" +sub_openid+"&operatorCode=" +text+"&deviceId=" +getDeviceSN()
                +"&enterpriseId=" +enterpriseId +"&authInfo=" +authinfo+"&rawdata=" +rawdata;
        Log.e("code",urls+"");
        Request.getIncetanc().requestsTwo(this, urls,"GET","405",formBody,this);
        // dialog.dismiss();
    }



    //获取人脸凭证接口:
    private void Banjiese(String Banjiese,String rawdata){

        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);


       //传递键值对参数
       formBody.add("deviceId",Banjiese);
        formBody.add("rawdata",rawdata);
        formBody.add("operatorCode",text);
        String urls =  UrlUtils.HUOQUSHUANLIANZF;
      //  String urls =  UrlUtils.HUOQUSHUANLIANZF+"?deviceId=" +Banjiese +"&rawdata=" + rawdata+"&operatorCode="+text;
        Toast.makeText(this,urls,Toast.LENGTH_LONG).show();
        Request.getIncetanc().requestsPost(this, urls,"1",formBody,this);

        dialog.dismiss();
    }


    //获取人脸支付接口:
    private void PayCode(String Code){

//        dialog = new LoadingDialog(this);
//        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.ZHIFUJIEK+"?facecode=" +Code +"&uToken=" + utoken +"&chargeId=" + "chargeId" +"&openId=" + "openId";
        Log.e("qwq33",urls+"");
        Request.getIncetanc().requestsPost(this, urls,"2",formBody,this);
       // dialog.dismiss();
    }


    //获取支付凭证
    private  void GetWxpayfaceCode(){


        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("mch_id", mchId);
        //这里区分服务商模式和商户模式，服务商模式子商户信息必填
        map.put("sub_appid", subAppid);
        map.put("sub_mch_id", subMchId);
        map.put("store_id", storeId);

//        map.put("telephone", "");
//
//        map.put("out_trade_no", "");  //这里有坑
        map.put("total_fee", "1");
        map.put("face_code_type", "1");
        map.put("ignore_update_pay_result", "1");
        map.put("face_authtype", "FACEPAY");
        map.put("authinfo", authinfo);
        map.put("ask_face_permit", "1");
        map.put("ask_ret_page", "1");


        WxPayFace.getInstance().getWxpayfaceCode(map, new IWxPayfaceCallback() {
            @Override
            public void response(final Map info) throws RemoteException {

                if (info == null) {
                    new RuntimeException("调用返回为空").printStackTrace();
                    return;
                }
                String code = (String) info.get("return_code"); // 错误码
                Log.e("qweqwe",info.toString()+"=="+info.get("openid").toString()+"=="+code);
                String msg = (String) info.get("return_msg"); // 错误码描述
                String faceCode = info.get("face_code").toString(); // 人脸凭证，用于刷脸支付
                String openid = info.get("openid").toString(); // openid
                String sub_openid = ""; // 子商户号下的openid(服务商模式)
                int telephone_used = 0; // 获取的`face_code`，是否使用了请求参数中的`telephone`
                int underage_state = 0; // 用户年龄信息（需联系微信支付开通权限）
                if (info.get("sub_openid") != null)
                    sub_openid = info.get("sub_openid").toString();
                if (info.get("telephone_used") != null)
                    telephone_used = Integer.parseInt(info.get("telephone_used").toString());
                if (info.get("underage_state") != null)
                    underage_state = Integer.parseInt(info.get("underage_state").toString());
                if (code == null || faceCode == null || openid == null || !code.equals("SUCCESS")) {
                    new RuntimeException("调用返回非成功信息,return_msg:" + msg + "   ").printStackTrace();
                    return ;
                }
                /*
                在这里处理您自己的业务逻辑
                解释：您在上述中已经获得了支付凭证或者用户的信息，您可以使用这些信息通过调用支付接口来完成支付的业务逻辑
                需要注意的是：
                    1、上述注释中的内容并非是一定会返回的，它们是否返回取决于相应的条件
                    2、当您确保要解开上述注释的时候，请您做好空指针的判断，不建议直接调用
                 */
               //  PayCode(faceCode);
            }
        });
    }

   String openids = "owOoV5tkMEwPqKirC-djpfVLQTHY";
    //获取用户openid
    private  void GetfaceUserInfo(){

        HashMap<String, String> m1 = new HashMap<>();
        m1.put("appid", appid);
        m1.put("mch_id", mchId);
        //这里区分服务商模式和商户模式，服务商模式子商户信息必填
        m1.put("sub_appid", subAppid);
        m1.put("sub_mch_id", subMchId);
        m1.put("store_id", storeId);

        m1.put("face_authtype", "FACEID-ONCE");
        m1.put("authinfo", authinfo);
//        m1.put("ask_unionid", "1");
//        m1.put("screen_index", "1");

        Log.e("返回info",m1.toString()+"==");
//        m1.put("ask_unionid", "1"); // 是否获取union_id    0：获取    1：不获取

        WxPayFace wxPayFace = WxPayFace.getInstance();
        wxPayFace.getWxpayfaceUserInfo(m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                Log.e("返回",info.toString()+"=="+info.get("openid").toString()+"==");
                if (info == null) {
                    new RuntimeException("调用返回为空").printStackTrace();
                    return;
                }

                String code = (String) info.get("return_code"); // 错误码
                String msg = (String) info.get("return_msg"); // 错误码描述
                openids = info.get("openid").toString(); // openid
                String sub_openid = "";
                if (info.get("sub_openid") != null) sub_openid = info.get("sub_openid").toString(); // 子商户号下的openid(服务商模式)
                String nickName = info.get("nickname").toString(); // 微信昵称
                String token = "";
                if (info.get("token") != null) token = info.get("token").toString(); // facesid,用户获取unionid
                if (code == null || openid == null || nickName == null || !code.equals("SUCCESS")) {
                    new RuntimeException("调用返回非成功信息,return_msg:" + msg + "   ").printStackTrace();
                    return ;
                }
                /*
                获取union_id逻辑，传入参数ask_unionid为"1"时使用
                String unionid_code = "";
                if (info.get("unionid_code") != null) unionid_code = info.get("unionid_code").toString();
                if (TextUtils.equals(unionid_code,"SUCCESS")) {
                    //获取union_id逻辑
                } else {
                    String unionid_msg = "";
                    if (info.get("unionid_msg") != null) unionid_msg = info.get("unionid_msg").toString();
                    //处理返回信息
                }
                */
                /*
                在这里处理您自己的业务逻辑
                需要注意的是：
                    1、上述注释中的内容并非是一定会返回的，它们是否返回取决于相应的条件
                    2、当您确保要解开上述注释的时候，请您做好空指针的判断，不建议直接调用
                 */

                Log.e("utokenx",utoken+"==");
                Intent ind = new Intent(XuanOilActivity.this,ScarRenActivity.class);
                ind.putExtra("materialId",materialId);
                ind.putExtra("oilid",oilid);
                ind.putExtra("gunids",gunids);
                ind.putExtra("stationId",stationId);
                ind.putExtra("amount",ed_jines.getText().toString().trim());
                ind.putExtra("stationName",stationName);
                ind.putExtra("utoken",utoken);
                ind.putExtra("operatorCode",text);
                ind.putExtra("sub_openid",sub_openid);
                ind.putExtra("openId",openids);
                startActivity(ind);



            }
        });

        Log.e("返回","=====");



    }




    //获取油品
    private void Youping() {
        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new Request().getHashMap(this);
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.YOUQIANGJIEBIAO+"?code=" + "xygas";
        Request.getIncetanc().requestsTwo(this, urls,"GET","4",formBody,this);
    }


    //获取油抢
    private void Youqiang(String typeid) {
        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new Request().getHashMap(this);
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.YOUQIANGHOUQU+"?token=" + ""+"&type=" + typeid;
        Request.getIncetanc().requestsPosthand(this, urls,"6",formBody,this);
    }



    // 获取用户信息
    private void GetWxpayfaceUserInfo(){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePayFace();

        if(mQRCodeView !=null){
            mQRCodeView.stopSpot();
        }
    }



    @Override
    public void getHttpError(String txnCode, String error) {

    }
    JSONArray oupon;
    String materialId=null;
    String oilid=null;
    String gunids=null;
    String stationId;
    String stationName;
    String appId;
    String enterpriseId ;
    String operatorUserId ;
    String operatorUsername ;
    String staSIdS ;
    String utoken="dc07aa7f782a20026e31d3ab5225c0dd";
    @Override
    public void response(String txnCode, Object dataEntity) {


        if(txnCode.equals("1")){
            try {

                dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");

                if (code == 1) {

                    String data = json.getString("data");
                    JSONObject json1 = new JSONObject(data);
                    mchId = json1.getString("mchId");
                    appid = json1.getString("appid");
                    subMchId = json1.getString("subMchId");
                    storeId = json1.getString("storeId");
                    authinfo = json1.getString("authinfo");
                    subAppid = json1.getString("subAppid");


                    //   GetWxpayfaceCode();
                    //获取用户
                     GetfaceUserInfo();
                }else {

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("2")){
            try {
                releasePayFace();
              //  dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("3")){
            try {
                releasePayFace();
                //  dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");

                if (code == 1) {

                    String data = json.getString("data");
                    JSONObject json1 = new JSONObject(data);
                    appId = json1.getString("appId");
                    enterpriseId = json1.getString("enterpriseId");
                    operatorUserId = json1.getString("operatorUserId");
                    staSIdS = json1.getString("stationId");
                    operatorUsername = json1.getString("operatorUsername");



                }else {

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("405")){
            try {
                releasePayFace();
                //  dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");

                if (code == 1) {

                utoken = json.getString("data");

                }else {

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        } else if(txnCode.equals("4")){
            try {
                dialog.dismiss();

                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");
                String body = json.getString("data");

                JSONObject json2 = new JSONObject(body);

                 oupon = json2.getJSONArray("guns");
                 stationId= json2.getString("stationId");
                 stationName= json2.getString("stationName");
                 String enterpriseId= json2.getString("enterpriseId");

              //  PyuToken(enterpriseId);
                Gson gson = new Gson();
                Type type = new TypeToken<List<Oil>>() {
                }.getType();
                lists = gson.fromJson(oupon.toString(), type);

                Log.e("233ww33",stationId+"=="+lists.get(0).guns.get(0).getValue());

                for (int i = 0; i < oupon.length(); i++) {
                    JSONObject value = oupon.getJSONObject(i);
                    //获取到title值
                    String title = value.getString("type");
                    // String title = value.optString("title");

                    JSONObject jjus = new JSONObject(title);

                    String valuse = jjus.getString("value");
                    Log.e("233ww32",valuse+"=="+title);
                }




                keadp = new KehuAdapter(lists,oupon,this);
                girdvie1.setAdapter(keadp);
                girdvie1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,final int positions, long id) {




                        try {

                            JSONObject value = oupon.getJSONObject(positions);
                            materialId = value.getString("materialId");
                            String title = value.getString("type");
                            JSONObject jjus = new JSONObject(title);
                            oilid = jjus.getString("id");
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        xuandp = new XuanAdapter(lists.get(positions).guns,XuanOilActivity.this);
                        girdvie2.setAdapter(xuandp);

                        keadp.clearSelection(positions);
                        keadp.notifyDataSetChanged();


                        girdvie2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                xuandp.clearSelection(position);
                                xuandp.notifyDataSetChanged();

                                gunids=lists.get(positions).guns.get(position).getId();
                            }
                        });

                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void releasePayFace() {
        WxPayFace.getInstance().releaseWxpayface(XuanOilActivity.this);
    }



    //获取usb扫描枪的usb
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //我们需要的内容肯定是大于6的
        //比如用户点击返回键，keyCode就是小于6的，这类的我们不需要
        if (keyCode <= 6) {
            return super.onKeyDown(keyCode, event);
        }
        return scanGun.isMaybeScanning(keyCode, event) || super.onKeyDown(keyCode, event);
    }







}
