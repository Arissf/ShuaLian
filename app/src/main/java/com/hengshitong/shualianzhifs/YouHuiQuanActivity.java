package com.hengshitong.shualianzhifs;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hengshitong.shualianzhifs.adapter.YouHuidapter;
import com.hengshitong.shualianzhifs.commont.UrlUtils;
import com.hengshitong.shualianzhifs.entmy.MessageEvent2;
import com.hengshitong.shualianzhifs.entmy.RelationOilgun;
import com.hengshitong.shualianzhifs.entmy.YouHui;
import com.hengshitong.shualianzhifs.utils.HttpError;
import com.hengshitong.shualianzhifs.utils.HttpResponse;
import com.hengshitong.shualianzhifs.utils.LoadingDialog;
import com.hengshitong.shualianzhifs.utils.Request;
import com.hengshitong.shualianzhifs.utils.SpUtils;
import com.mrgao.luckrecyclerview.LucklyRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.FormBody;

public class YouHuiQuanActivity extends AppCompatActivity implements LucklyRecyclerView.OnLoadMoreListener,
        LucklyRecyclerView.OnRefreshListener, View.OnClickListener,LucklyRecyclerView.OnItemHeaderClickListener,HttpResponse,HttpError {

    private LucklyRecyclerView mLRecyclerView;
    private TextView tv_bushi;
    private ImageView left_iv;
    private LoadingDialog dialog;

    private List<YouHui> kuhlist;
    private YouHuidapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_hui_quan);

        intdate();

    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
             case R.id.left_iv :
                 EventBus.getDefault().post(new MessageEvent2("0",100,"3"));
                 SpUtils.removeObject(this,new TypeToken<List<RelationOilgun>>() { }.getType());
                 finish();

              break;


          }
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
                String body = json.getString("data");
                JSONObject json2 = new JSONObject(body);
                JSONArray oupon = json2.getJSONArray("rows");
                Gson gson = new Gson();
                Type type = new TypeToken<List<YouHui>>() {
                }.getType();
                kuhlist = gson.fromJson(oupon.toString(), type);

                for(int i=0;i<kuhlist.size();i++){
                    kuhlist.get(i).setXuanze("0");
                }


                if(kuhlist.size()<0){
                    tv_bushi.setVisibility(View.GONE);
                }


                adapter.addAll(kuhlist);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onHeaderClick(View rootView, int position) {

    }


    private void intdate() {

        left_iv = (ImageView) findViewById(R.id.left_iv);
        left_iv.setOnClickListener(this);

        mLRecyclerView = (LucklyRecyclerView)findViewById(R.id.yRecyclerView);
        //添加加载更多监听
        mLRecyclerView.setLoadMoreListener(this);
        //添加下拉刷新监听
        mLRecyclerView.setOnRefreshListener(this);
        //设置空视图/错误视图点击后是否刷新数据
        mLRecyclerView.setOnClickEmptyOrErrorToRefresh(true);
        mLRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //封装好了的线性分割线,也可以使用setGridDivider()；使用以及封装好的网格式布局，在使用这句话之前，请先设置好LayoutManager
        //   mLRecyclerView.addLinearDivider(LRecyclerView.VERTICAL_LIST);
        // mLRecyclerView.addGridDivider();
        mLRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置下拉刷新的背景图片（可放广告图片哦）


        mLRecyclerView.setLoadingProgressColor(getResources().getColor(R.color.clorlvs));
        mLRecyclerView.setLoadingTextColor(getResources().getColor(R.color.clorlvs));
        mLRecyclerView.setRefreshColor(getResources().getColor(R.color.clorlvs));


        adapter =new YouHuidapter(this,handleratwo);
        mLRecyclerView.setAdapter(adapter);

        //添加错误的View
        mLRecyclerView.setErrorView(R.layout.emtey_shuju);
        //添加空View
        mLRecyclerView.setEmptyView(R.layout.emtey_shuju);
        //设置下拉刷新的时长
        mLRecyclerView.setDuration(2000);
        //添加错误的View

        tv_bushi = (TextView) findViewById(R.id.tv_bushi);
        tv_bushi.setOnClickListener(this);
        tv_bushi.setText("不使用优惠券");

        Bnajielie(getIntent().getStringExtra("utoken").trim());
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



    private int page=1;
    private int size =10;
    private int status = 1;
    private void Bnajielie(String utoken) {
        dialog = new LoadingDialog(this);
        dialog.show("正加载数据...",true,true);

        FormBody.Builder formBody = new  FormBody.Builder();
        Request.getIncetanc().setHttpError(this);

        String urls = UrlUtils.YOUHUIJUAN+"?page=" + page + "&size=" + size + "&status=" + status
                + "&oilTypeIds=" + getIntent().getStringExtra("oilTypeIds")
                + "&stationId=" + getIntent().getStringExtra("stationId")
                + "&cost=" + getIntent().getStringExtra("cost")
                + "&uToken=" +  utoken
                + "&payMethod=" +  getIntent().getStringExtra("payMethod");
        Request.getIncetanc().requestsTwo(this, urls, "GET", "1", formBody, this);

    }

}
