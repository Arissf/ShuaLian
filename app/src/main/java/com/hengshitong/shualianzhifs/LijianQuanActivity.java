package com.hengshitong.shualianzhifs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hengshitong.shualianzhifs.adapter.LiJanadapter;
import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.entmy.Lijina;
import com.hengshitong.shualianzhifs.entmy.MessageEvent2;
import com.hengshitong.shualianzhifs.entmy.RelationOilgun;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.Request;
import com.hengshitong.shualianzhifs.utils.SharedPreferencesHelper;
import com.hengshitong.shualianzhifs.utils.SpUtils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.FormBody;

public class LijianQuanActivity extends AppCompatActivity implements HttpResponse,HttpError,View.OnClickListener{

    private RecyclerView recycler_view;
   // private OilgunAdapter adapter;
    private LiJanadapter adapter;
    private LinearLayout shuju;
    private List<Lijina> lists;
    private TextView tv_bushi;

    SharedPreferencesHelper sharedpa;
    RelationOilgun sd;
    LoadingDialog dialog;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lijian);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setHasFixedSize(true);
        recycler_view.setNestedScrollingEnabled(false);
        adapter =new LiJanadapter(this,handleratwo);
        recycler_view.setAdapter(adapter);
        tv_bushi= (TextView) findViewById(R.id.tv_bushi);
        tv_bushi.setOnClickListener(this);
        tv_bushi.setText("不使用立减券");
        shuju = (LinearLayout) findViewById(R.id.shuju);
        Youping();

    }

    private Handler handleratwo =  new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            finish();

        }
    };
    //获取油品
    private void Youping() {

        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);
        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);
        String urls =  UrlUtils.HUOQULIJIANJUAN+
       //         "?uToken=" + token
//                +"&amount="+ getIntent().getStringExtra("cost")
//                +"&payMethod="+ getIntent().getStringExtra("payMethod")
//                +"&oilTypeId=" + getIntent().getStringExtra("oilTypeIds")
//                +"&stationId=" + getIntent().getStringExtra("stationId");
        "?uToken=" + "0718e066f9dad634afc4c42d7921fa43"
        +"&amount="+ getIntent().getStringExtra("cost")
                +"&payMethod="+ "3"
                +"&oilTypeId=" + "1150693270582484994"
                +"&stationId=" + "1215443318524108802";
        Request.getIncetanc().requestsTwo(this, urls,"GET","1",formBody,this);
    }


    @Override
    public void getHttpError(String txnCode, String error) {

    }

    @Override
    public void response(String txnCode, Object dataEntity) {
        try {
            dialog.dismiss();
            JSONObject json = new JSONObject(dataEntity.toString());
            int code = json.getInt("code");
            if (code == 1) {
                JSONArray oupon = json.getJSONArray("data");
                Gson gson = new Gson();
                Type type = new TypeToken<List<Lijina>>() {
                }.getType();
                lists = gson.fromJson(oupon.toString(), type);

                if(lists.size()==0){
                    recycler_view.setVisibility(View.GONE);
                    //     tv_bushi.setVisibility(View.GONE);
                }else {
                    recycler_view.setVisibility(View.VISIBLE);
                }
                for(int i=0;i<lists.size();i++){
                    lists.get(i).setXuanze("0");
                }


                adapter.addAll(lists);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_bushi:

                EventBus.getDefault().post(new MessageEvent2("0",100,"4"));
                SpUtils.removeObject(this,new TypeToken<List<RelationOilgun>>() { }.getType());
                finish();
                break;
        }
    }
}
