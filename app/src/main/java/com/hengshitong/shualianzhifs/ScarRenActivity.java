package com.hengshitong.shualianzhifs;

import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.entmy.MessageEvent2;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.Request;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.FormBody;

public class ScarRenActivity extends AppCompatActivity implements View.OnClickListener , HttpResponse,HttpError {

    private RelativeLayout re_diyong;
    String materialId=null;
    String oilid=null;
    String gunids=null;
    String stationId=null;
    String utoken=null;
    String amount= null;
    private TextView tv_stationName;
    String paytype="";
    private CheckBox checkbox3;
    private TextView tv_dnegji,tv_dikou,tv_yhjmoney,tv_lijmoney,tv_jifen,te_yue;
    private RelativeLayout ler_yue,ler_weix,re_lijian;
    private ImageView im_xunze,im_xunze1;
    private TextView tv_amout;
    private String payMethod="3";
    private String quanbu="";
    private String couponId="";
    private String deductId="";
    private int  balance=0;
    String   mchId,appid,subMchId,storeId,authinfo="",subAppid="";
    String rawdata="";
    private LoadingDialog dialog;
    private Button queding;
    private String sub_openid="";
    private String operatorCode="";
    private String  selectType1="1";
    private String faceCode=null;
    private String openId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scar_ren);
        EventBus.getDefault().register(this);
        tv_stationName=(TextView) findViewById(R.id.tv_stationName);
        checkbox3 = (CheckBox) findViewById(R.id.checkbox3);
        tv_dnegji=(TextView) findViewById(R.id.tv_dnegji);
        tv_dikou=(TextView) findViewById(R.id.tv_dikou);
        tv_yhjmoney=(TextView) findViewById(R.id.tv_yhjmoney);
        tv_lijmoney=(TextView) findViewById(R.id.tv_lijmoney);
        tv_jifen=(TextView) findViewById(R.id.tv_jifen);
        te_yue=(TextView) findViewById(R.id.te_yue);
        ler_yue=(RelativeLayout) findViewById(R.id.ler_yue);
        ler_weix=(RelativeLayout) findViewById(R.id.ler_weix);
        im_xunze= (ImageView) findViewById(R.id.im_xunze);
        im_xunze1= (ImageView) findViewById(R.id.im_xunze1);
        tv_amout=(TextView) findViewById(R.id.tv_amout);
        queding=(Button) findViewById(R.id.queding);

        materialId = getIntent().getStringExtra("materialId").trim();
        oilid = getIntent().getStringExtra("oilid").trim();
        gunids = getIntent().getStringExtra("gunids").trim();
        stationId = getIntent().getStringExtra("stationId").trim();
        amount= getIntent().getStringExtra("amount").trim();
        utoken = getIntent().getStringExtra("utoken").trim();
        sub_openid=getIntent().getStringExtra("sub_openid").trim();
        operatorCode=getIntent().getStringExtra("operatorCode").trim();
        openId=getIntent().getStringExtra("openId").trim();

        tv_amout.setText(amount);
        tv_stationName.setText("商家:"+getIntent().getStringExtra("stationName").trim());
        re_diyong=(RelativeLayout) findViewById(R.id.re_diyong);
        re_lijian=(RelativeLayout) findViewById(R.id.re_lijian);
        re_diyong.setOnClickListener(this);
        re_lijian.setOnClickListener(this);
        ler_yue.setOnClickListener(this);
        ler_weix.setOnClickListener(this);
        queding.setOnClickListener(this);

        checkbox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pointids=  point;
                }else {
                    pointids=  "0";
                }
            }
        });


        //用户余额
        PayBlances();
        //返回优惠方案
        Youhui("","");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MessageEvent2 event) {
        //TODO
        //    btn_event.setText(event.getMesage());
        quanbu =event.getMesage();
        int  posiont =event.getZongmun();
        String  types =event.getType();

       //优惠券
        if(types.equals("0")){
            couponId = quanbu;
            selectType1="2";
            if(quanbu.equals("0")){
                tv_lijmoney.setTextColor(getResources().getColor(R.color.gray_ff2));
            }

            Youhui(couponId,deductId);


        }else if(types.equals("1")){

            deductId = quanbu;

            if(quanbu.equals("0")){
                tv_yhjmoney.setTextColor(getResources().getColor(R.color.gray_ff2));
            }

            selectType1="3";
            Youhui(couponId,deductId);


        }else if(types.equals("3")){

            couponId = "";

            if(quanbu.equals("0")){
                tv_yhjmoney.setTextColor(getResources().getColor(R.color.gray_ff2));
            }

            selectType1="1";
            Youhui(couponId,deductId);


        }else if(types.equals("4")){

            deductId = "";

            if(quanbu.equals("0")){
                tv_yhjmoney.setTextColor(getResources().getColor(R.color.gray_ff2));
            }
            selectType1="1";
            Youhui(couponId,deductId);


        }


    }



    private void Youhui(String comid,String dedid) {

        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls = UrlUtils.YOUHUIFANGAN+"?utoken=" + utoken
                + "&openId=" + openId
                + "&stationId=" + stationId
                + "&gunId=" + gunids
                + "&oilTypeId=" + oilid
                + "&payMethod=" + payMethod
                + "&selectType=" +  selectType1
                + "&couponId=" +  comid
                + "&deductId=" +  dedid
                + "&amount=" +  amount;
        Request.getIncetanc().requestsPost(this, urls, "78", formBody, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_diyong:

                Intent lijian2 = new Intent(ScarRenActivity.this,YouHuiQuanActivity.class);
                lijian2.putExtra("cost",amount);
                lijian2.putExtra("oilTypeIds",oilid);
                lijian2.putExtra("stationId",stationId);
                lijian2.putExtra("openId","owOoV5tkMEwPqKirC-djpfVLQTHY");
                lijian2.putExtra("payMethod",payMethod);
                lijian2.putExtra("utoken",getIntent().getStringExtra("utoken").trim());
                startActivity(lijian2);


                break;

            case R.id.re_lijian:

                Intent lijians = new Intent(ScarRenActivity.this,LijianQuanActivity.class);
                lijians.putExtra("cost",amount);
                lijians.putExtra("oilTypeIds",oilid);
                lijians.putExtra("stationId",stationId);
                lijians.putExtra("payMethod",payMethod);
                lijians.putExtra("utoken",getIntent().getStringExtra("utoken").trim());
                startActivity(lijians);

                break;

            case R.id.ler_yue:
                   //余额支付
                im_xunze.setBackgroundResource(R.drawable.coir_xuanz);
                im_xunze1.setBackgroundResource(R.drawable.coir_weix);
                payMethod = "8";

                rawdata ="";

                break;

            case R.id.ler_weix:
                   //刷脸支付

              //  getWxpayfaceRawdata();

                im_xunze1.setBackgroundResource(R.drawable.coir_xuanz);
                im_xunze.setBackgroundResource(R.drawable.coir_weix);
                payMethod = "9";



                break;

            case R.id.queding:




                PayYuxiaban();


                break;


        }
    }

    @Override
    public void getHttpError(String txnCode, String error) {

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
                Log.e("raw", "1rawdata ==" + rawdata);
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
                Banjiese(getDeviceSN(),toURLEncoded(rawdata));
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

    public static String getDeviceSN(){

        String serialNumber = android.os.Build.SERIAL;

        return serialNumber;
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
               faceCode = info.get("face_code").toString(); // 人脸凭证，用于刷脸支付
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
                 //先下单
                PayYuxiaban();


            }
        });
    }


    //获取人脸支付接口:
    private void PayCode(String Code){

//        dialog = new LoadingDialog(this);
//        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.SHUALIANZHIFUJIEK+"?facecode=" +Code +"&uToken=" + utoken +"&chargeId=" + "chargeId" +"&openId=" + "openId";
        Log.e("qwq33",urls+"");
        Request.getIncetanc().requestsPost(this, urls,"2",formBody,this);
        // dialog.dismiss();
    }

    //获取人脸凭证接口:
    private void Banjiese(String Banjiese,String rawdata){

        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.HUOQUSHUANLIANZF+"?deviceId=" +Banjiese +"&rawdata=" + rawdata+"&operatorCode="+operatorCode;
        Toast.makeText(this,urls,Toast.LENGTH_LONG).show();
        Request.getIncetanc().requestsPost(this, urls,"1",formBody,this);


    }


    //获取人用户余额
    private void PayBlances(){

//        dialog = new LoadingDialog(this);
//        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.HUOQUYONGHUBLANCES+"?stationId=" +stationId +"&utoken=" + utoken ;
        Log.e("qwq33",urls+"");
        Request.getIncetanc().requestsTwo(this, urls,"GET","3",formBody,this);
        // dialog.dismiss();
    }

    //支付下单接口
    private void PayYuxiaban(){

        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.DINGDANSHENGCHENG+"?stationId=" +stationId +"&uToken=" + utoken+"&openId="+sub_openid+"&aliUserId="+""
                +"&operatorCode=" + operatorCode+"&gunId="+gunids+"&oilTypeId="+"" +oilid
                +"&amount=" + amount+"&payMethod="+payMethod+"&couponId="+"" +couponId
                +"&deductId=" + deductId+"&selectType="+""+"&deductionPoint="+discountPoint
                +"&longitude=" + ""+"&latitude="+""+"&deviceId="+getDeviceSN()+"&rawdata="+rawdata;
        Log.e("qwq33",urls+"");
        Request.getIncetanc().requestsPost(this, urls,"4",formBody,this);
        // dialog.dismiss();
    }





    String discount=null;
    String point;
    String pointids;
    String discountPoint=null;
    String orderId=null;
    @Override
    public void response(String txnCode, Object dataEntity) {

        if(txnCode.equals("78")){
            try {
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                if (code == 1) {
                    String oupon = json.getString("data");
                    JSONObject bo2 = new JSONObject(oupon);
                    String level = bo2.getString("level");
                    discount = bo2.getString("discount");
                    String memberDiscountAmount = bo2.getString("memberDiscountAmount");
                    String deductId = bo2.getString("deductId");
                    String deductAmount = bo2.getString("deductAmount");
                    String couponId = bo2.getString("couponId");  //优惠券
                    String couponAmount = bo2.getString("couponAmount");
                    point = bo2.getString("point");
                     discountPoint = bo2.getString("discountPoint");
                    String discountAmount = bo2.getString("discountAmount");

                    tv_dnegji.setText(level);
                    tv_yhjmoney.setText("-"+couponAmount);
                    tv_dikou.setText("享"+discount+"折");
                    tv_lijmoney.setText("-"+deductAmount);
                    tv_jifen.setText("可用"+point+"积分抵用"+discountPoint+"元");

                }else {

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else   if(txnCode.equals("1")){
            try {


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

                    GetWxpayfaceCode();

                }else {

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("2")){
            try {

                 dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");
                releasePayFace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("3")){
            try {


                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");

                if (code == 1) {
                    String data = json.getString("data");
                    JSONObject json1 = new JSONObject(data);
                    String balance  = json1.getString("balance");
                    te_yue.setText("余额支付(¥"+balance+")");
                }else {

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(txnCode.equals("4")){
            try {

                dialog.dismiss();
                JSONObject json = new JSONObject(dataEntity.toString());
                int code = json.getInt("code");
                String message = json.getString("message");

                if (code == 1) {

                    String data = json.getString("data");
                    JSONObject json1 = new JSONObject(data);
                    orderId  = json1.getString("orderId");

                    if(payMethod.equals("9")){
                        PayCode(faceCode);
                    }else {

                        finish();
                    }

                }else {

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void releasePayFace() {
        WxPayFace.getInstance().releaseWxpayface(ScarRenActivity.this);
    }
}
