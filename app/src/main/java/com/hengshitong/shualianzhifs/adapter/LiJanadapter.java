package com.hengshitong.shualianzhifs.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hengshitong.shualianzhifs.R;
import com.hengshitong.shualianzhifs.entmy.Lijina;
import com.hengshitong.shualianzhifs.entmy.MessageEvent2;
import com.hengshitong.shualianzhifs.entmy.RelationOilgun;
import com.hengshitong.shualianzhifs.utils.SharedPreferencesHelper;
import com.hengshitong.shualianzhifs.utils.SpUtils;


import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class LiJanadapter extends RecyclerView.Adapter<LiJanadapter.ItemHolder> {

    private static SharedPreferencesHelper sharedpa;
    List<Lijina> mStringList;
    private Context context;
    RelationOilgun sd;
    private Handler handleratwo;
    public LiJanadapter(Context context, Handler handleratwo ) {
        super();
        this.handleratwo=handleratwo;
        mStringList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new  LiJanadapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {

        if (mStringList.size() != 0) {
            holder.mTextView.setText(mStringList.get(position).getName());
            holder.xian.setText("有效期"+mStringList.get(position).getStartTime()+"-"+mStringList.get(position).getEndTime());
        }

//        sharedpa = new SharedPreferencesHelper(context, "anhua");
        List<Lijina> list2 = SpUtils.getObject(context,
                new TypeToken<List<Lijina>>() { }.getType());
        try {
            if(list2.size()!=0){
                if(list2.get(position).getXuanze().equals("1")){
                    holder.im_shifou.setBackgroundResource(R.drawable.coir_xuanz);
                }else if(list2.get(position).getXuanze().equals("0")){
                    holder.im_shifou.setBackgroundResource(R.drawable.coir_weix);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 点击事件
                holder.im_shifou.setBackgroundResource(R.drawable.coir_xuanz);
                EventBus.getDefault().post(new MessageEvent2(mStringList.get(position).getId(),position,"1"));
                mStringList.get(position).setXuanze("1");
                SpUtils.putObject(context, mStringList,
                        new TypeToken<List<Lijina>>() { }.getType());

                android.os.Message msg = new android.os.Message();
                msg.obj= "1";
                handleratwo.sendMessage(msg);

            }
        });

    }
    public static <T> T getClasse(Class<T> model,Context context){
        sharedpa = new SharedPreferencesHelper(context, "anhua");
        String key=model.getName();
        String value= (String) sharedpa.getSharedPreference(key, "");
        T t= new Gson().fromJson(value,model);
        return t;
    }



    @Override
    public void onBindViewHolder(ItemHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {

            onBindViewHolder(holder, position);
        } else {
            //这里做一些局部刷新的东西
            //    holder.mTextView.setText("我是局部刷新的" + position + "(old=>)" + item);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mStringList.size();
    }

    public void addAll(List<Lijina> list) {
        mStringList.addAll(list);
        //全部刷新
        notifyDataSetChanged();
    }

    public void clearAll() {
        mStringList.clear();
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView mTextView,xian;
        ImageView im_shifou;
        public ItemHolder(View itemView) {
            super(itemView);
            mTextView = getView(itemView, R.id.tv_zhoun);
            xian = getView(itemView, R.id.xian);
            im_shifou= getView(itemView, R.id.im_shifou);
        }
    }

    private <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }

}
